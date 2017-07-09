import java.nio.file.{Files, Path, Paths}
import java.util.regex.Pattern

import scala.collection.JavaConverters._

// A MediaLibrary is a folder that contains MediaFiles
// that are organized in the following folder structure:
// rootFolder
// - <year>
//   - <month>
//     - file
class MediaLibrary(rootFolder: Path) {

  private val parkingFolder = rootFolder.resolve("tmp")
  private val extension = "jpg"
  private val pattern: Pattern = """(?i).*\.jpg$""".r.pattern


  def ingest(sourceFolder: Path): Unit = {
    Files.createDirectories(parkingFolder)
    val existingParkedFiles = listMatchingFiles(parkingFolder)
    lastParkingSlot = existingParkedFiles.length

    val files = parkFilesFromTouchedFolders(listMatchingFiles(sourceFolder) ::: listMatchingFiles(parkingFolder), List())

    for ((folder, files) <- files.groupBy(_.targetFolder)) {
      val targetFolder = rootFolder.resolve(folder)
      for ((file, slot) <- files.sortBy(_.instant).zipWithIndex) {
        file.move(targetFolder.resolve(file.targetName(slot, extension)))
      }
    }
    assert(Files.list(parkingFolder).count() == 0)
    Files.deleteIfExists(parkingFolder)
    lastParkingSlot = 0
  }

  def parkFilesFromTouchedFolders(files: List[MediaFile], ignore: List[Path]): List[MediaFile] = {
    val folders = files.map(_.targetFolder).distinct.diff(ignore)
    val newFiles = folders.flatMap(folder => parkFiles(rootFolder.resolve(folder)))
    if (newFiles.isEmpty) files else files ::: parkFilesFromTouchedFolders(newFiles, ignore ::: folders)
  }

  def parkFiles(folder: Path): List[MediaFile] = {
    listMatchingFiles(folder).map { file =>
      file.move(parkingFolder.resolve(file.targetName(parkingSlot, extension)))
    }
  }

  def listMatchingFiles(folder: Path): List[MediaFile] = {
    assert(Files.isDirectory(folder), folder.toString)

    def matchesPattern(file: Path) = pattern.matcher(file.getFileName.toString).matches

    // grey underlines are because of implicit conversions of Array objects
    val (folders, files) = Files.list(folder).iterator.asScala.toList.partition(Files.isDirectory(_))
    folders.flatMap(listMatchingFiles) ++ files.filter(matchesPattern).map(MediaFile(_))
  }

  private def parkingSlot: Int = {
    lastParkingSlot += 1
    lastParkingSlot
  }
  private var lastParkingSlot = 0

}
