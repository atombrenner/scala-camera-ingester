import java.nio.file.{Path, Paths}
import java.time.{Instant, LocalDateTime, ZoneId}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.exif.ExifSubIFDDirectory

private object MediaFile {

  private val monthName = Array("01 Januar", "02 Februar", "03 März", "04 April", "05 Mai", "06 Juni", "07 Juli", "08 August", "09 September", "10 Oktober", "11 November", "12 Dezember")

  def apply(path: Path): MediaFile = {
    val instant = readMetadata(path.toFile).getFirstDirectoryOfType(classOf[ExifSubIFDDirectory]).getDateOriginal().toInstant
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    val folder = Paths.get(dateTime.getYear.toString, MediaFile.monthName(dateTime.getMonthValue - 1))
    val name = instant.toString.substring(0, 10)
    new MediaFile(path, instant, folder, name)
  }

}

class MediaFile private (val path: Path, val instant: Instant, val targetFolder: Path, val targetName: String) {

  def move(to: Path): MediaFile = {
    println(s"${path} ===> ${to}")

    new MediaFile(to, instant, targetFolder, targetName)

    // state, but is really changes the state on the machine
    // use copy & delete if root is not the same
  }

  def targetName(slot: Int, extension: String): String = f"$targetName $slot%03d.$extension"

}

