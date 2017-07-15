import java.nio.file.{Files, Path, Paths}
import java.time.{Instant, LocalDateTime, ZoneId}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.exif.ExifSubIFDDirectory

private object MediaFile {

  private val monthName = Array("01 Januar", "02 Februar", "03 März", "04 April", "05 Mai", "06 Juni", "07 Juli", "08 August", "09 September", "10 Oktober", "11 November", "12 Dezember")

  def apply(path: Path): MediaFile = new MediaFile(path)

}

class MediaFile (private var path: Path) {

  val instant: Instant = readMetadata(path.toFile).getFirstDirectoryOfType(classOf[ExifSubIFDDirectory]).getDateOriginal.toInstant
  val dateTime: LocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
  val targetFolder: Path = Paths.get(dateTime.getYear.toString, MediaFile.monthName(dateTime.getMonthValue - 1))
  val targetName: String = instant.toString.substring(0, 10)

  def targetName(number: Int, extension: String): String = f"$targetName $number%03d.$extension"

  def moveTo(folder: Path, index: Int, extension: String): Unit = {
    val to = folder.resolve(targetName(index + 1, extension))
    println(s"$path ===> $to")

    Files.move(path, to)
    path = to
  }

}

