import java.io.File
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

  val camera = new File("/media/christian/DATA/TestImport/11 November")
  val path2017 = new File("/media/christian/DATA/Daten/Bilder/Photos/2017")
  val path2016 = new File("/media/christian/DATA/Daten/Bilder/Photos/2016")
  val jpgPattern = """(?i).*\.jpg$""".r.pattern

  val files = listMatchingFiles(path2017)(jpgPattern) ++ listMatchingFiles(path2016)(jpgPattern)

  private val targetPattern = """^(\d{4})-(\d{2})-(\d{2}) \d{3}\..*""".r

  checkWrongDate(files)

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

  def algorithm(): Unit = {
    // for each input file
    // add to storage location
    // output is list of storage locations

    // foreach storage location, reorganize media
    // sort by date to regenerate unique numbers
    // move all existing files into separate tmp subfolder
    // move all files to new location
    // Optimizations
    // - don't touch unchanged media
    // - move if same root
    // - copy and delete if not
  }

}
