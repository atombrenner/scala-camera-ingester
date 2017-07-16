import java.nio.file.{Files, Path}
import scala.collection.JavaConverters._

// A MediaLibrary is a folder that contains MediaFiles
// that are organized in the following folder structure:
// rootFolder
// - <year>
//   - <month>
//     - file
class MediaLibrary(rootFolder: Path) {

  private val parkingFolder = rootFolder.resolve("tmp")
  private val pattern = """(?i).*\.(jpg|jpeg)$""".r.pattern

  def ingest(sourceFolder: Path): Unit = {
    Files.createDirectories(parkingFolder)
    assert(Files.list(parkingFolder).count() == 0, s"$parkingFolder not empty")

    val files = affectedFiles(listMatchingFiles(sourceFolder))

    // move to parking folder
    for((file, index) <- files.zipWithIndex) yield {
      file.moveTo(parkingFolder, index)
    }

    // sort into file structure
    for ((folder, files) <- files.groupBy(_.targetFolder)) {
      val targetFolder = rootFolder.resolve(folder)
      Files.createDirectories(targetFolder)
      for ((file, index) <- files.sortBy(_.instant).zipWithIndex) {
        file.moveTo(targetFolder, index)
      }
    }

    assert(Files.list(parkingFolder).count() == 0)
    Files.deleteIfExists(parkingFolder)
  }

  // get recursively all files that potentially need to be moved
  def affectedFiles(files: List[MediaFile], ignore: List[Path] = List()): List[MediaFile] = {
    val touchedFolders = files.map(_.targetFolder).distinct.diff(ignore)
    val newFiles = touchedFolders.flatMap(folder => listMatchingFiles(rootFolder.resolve(folder)))
    if (newFiles.isEmpty) files else files ::: affectedFiles(newFiles, ignore ::: touchedFolders)
  }

  def listMatchingFiles(folder: Path): List[MediaFile] = {
    if (Files.notExists(folder)) return List()

    def matchesPattern(file: Path) = pattern.matcher(file.getFileName.toString).matches

    // grey underlines are because of implicit conversions of Array objects
    val (folders, files) = Files.list(folder).iterator.asScala.toList.partition(Files.isDirectory(_))
    folders.flatMap(listMatchingFiles) ++ files.filter(matchesPattern).map(MediaFile(_))
  }

}
