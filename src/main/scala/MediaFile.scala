import java.lang.Throwable
import java.nio.file.{Files, Path, Paths}
import java.time.{Instant, LocalDateTime, ZoneId}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileMetadataDirectory

import scala.util.Try

private object MediaFile {

  private val monthName = Array("01 Januar", "02 Februar", "03 März", "04 April", "05 Mai", "06 Juni", "07 Juli", "08 August", "09 September", "10 Oktober", "11 November", "12 Dezember")

  def apply(path: Path): MediaFile = {
    new JpgFile(path)
  }
}

abstract class MediaFile (private var path: Path, val extension: String) {

  val instant: Instant = readCreationDate(path)
  val dateTime: LocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
  val targetFolder: Path = Paths.get(dateTime.getYear.toString, MediaFile.monthName(dateTime.getMonthValue - 1))
  val targetName: String = instant.toString.substring(0, 10)

  def targetName(number: Int): String = f"$targetName $number%03d.$extension"

  def readCreationDate(path: Path): Instant

  def moveTo(folder: Path, index: Int): Unit = {
    val to = folder.resolve(targetName(index + 1))
    println(s"$path ===> $to")

    if (path.getRoot != to.getRoot) {
      Files.copy(path, to)
      Files.delete(path)
    } else {
      Files.move(path, to)
    }
    path = to
  }

}

class JpgFile(path: Path) extends MediaFile(path, "jpg") {
  override def readCreationDate(path: Path): Instant = {
    val metadata = readMetadata(path.toFile)
    Try(metadata.getFirstDirectoryOfType(classOf[ExifSubIFDDirectory]).getDateOriginal.toInstant).getOrElse {
      metadata.getFirstDirectoryOfType(classOf[FileMetadataDirectory]).getDate(FileMetadataDirectory.TAG_FILE_MODIFIED_DATE).toInstant
    }
  }
}

class Mp4File(path: Path) extends MediaFile(path, "mp4") {
  override def readCreationDate(path: Path): Instant = {
    throw new NotImplementedError()
  }
}

