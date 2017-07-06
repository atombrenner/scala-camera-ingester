import java.nio.file.{Path, Paths}
import java.time.{LocalDateTime, ZoneId}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.exif.ExifSubIFDDirectory

private object MediaFile {

  val monthName = Array("01 Januar", "02 Februar", "03 März", "04 April", "05 Mai", "06 Juni", "07 Juli", "08 August", "09 September", "10 Oktober", "11 November", "12 Dezember")

}

class MediaFile(val path: Path) {

  val dateTime: LocalDateTime = {
    val directory = readMetadata(path.toFile).getFirstDirectoryOfType(classOf[ExifSubIFDDirectory])
    LocalDateTime.ofInstant(directory.getDateOriginal().toInstant, ZoneId.systemDefault())
  }

  val targetFolder: String = s"${dateTime.getYear}/${MediaFile.monthName(dateTime.getMonthValue - 1)}"

  def move(to: Path): Unit = {
    // state, but is really changes the state on the machine
    // use copy & delete if root is not the same
  }

}

