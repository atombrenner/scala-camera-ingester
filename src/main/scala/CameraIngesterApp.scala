import java.io.File
import java.nio.file.{Files, Paths}
import java.time._
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.imaging.ImageMetadataReader.readMetadata

import scala.collection.JavaConverters._
import com.drew.metadata.Metadata

import scala.util.matching.Regex

object CameraIngesterApp extends App {

  println("Camera Ingester")

  val dataRoot = Paths.get("/media/christian/DATA/TestDataRoot")
  val camera = new File("/media/christian/DATA/TestImport")
  val path2017 = new File("/media/christian/DATA/Daten/Bilder/Photos/2017")
  val path2016 = new File("/media/christian/DATA/Daten/Bilder/Photos/2016")
  val jpgPattern = """(?i).*\.jpg$""".r.pattern

  private val targetPattern = """^(\d{4})-(\d{2})-(\d{2}) \d{3}\..*""".r

  val files = listMatchingFiles(camera)(jpgPattern).map{new MediaFile(_)}
  val folders = files.map(_.folder).distinct

  // side effect create folder if it does not exist or add existing files

  // 1. camerafiles
  // 2. add existing files from touched folders
  // 3. because the touched folders could contain wrong files, repeat until the list of files is stable
  // 4. move files to final places (sort by month and renumber)
  // 5. remove tmp/ folder from touched folders

  folders.foreach { folder =>
    val target = dataRoot.resolve(folder)
    print(target)
    if (Files.exists(target)) {
      // move existing matching files to target.resolve("tmp")
      // return list of moved files
    } else {
      Files.createDirectories(target)
      // return empty list
    }
  }

  //val files = listMatchingFiles(path2017)(jpgPattern) ++ listMatchingFiles(path2016)(jpgPattern)
  //checkWrongDate(files)

  def checkWrongDate(files: Iterable[File]): Unit = {
    for (f <- files) {
      val date = readExifDate(f)
      f.getName match {
        case targetPattern(year, month, day) =>
          if (date.toLocalDate != LocalDate.of(year.toInt, month.toInt, day.toInt)) {
            println(s"${f.getCanonicalPath} is  ${date}")
          }
        case _ => //println(f.getCanonicalPath)
      }
    }
  }

  def readExifDate(f: File): LocalDateTime = {
    val directory = readMetadata(f).getFirstDirectoryOfType(classOf[ExifSubIFDDirectory])
    LocalDateTime.ofInstant(directory.getDateOriginal().toInstant, ZoneId.systemDefault())
  }

  // This can be done by the new java.nio.file.Files
  def listMatchingFiles(folder: File)(implicit pattern: Pattern): Array[File] = {
    assert(folder.isDirectory, folder.getName)

    def matchesPattern(file: File) = pattern.matcher(file.getName).matches

    // grey underlines are because of implicit conversions of Array objects
    val (folders, files) = folder.listFiles().partition(_.isDirectory)
    folders.flatMap(listMatchingFiles(_)) ++ files.filter(matchesPattern)
  }

}
