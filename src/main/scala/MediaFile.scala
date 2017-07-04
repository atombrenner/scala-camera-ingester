import java.io.File
import java.time.{LocalDateTime, ZoneId}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.exif.ExifSubIFDDirectory

class MediaFile(val f: File) {

  val file: File = f
  val dateTime: LocalDateTime = {
    val directory = readMetadata(f).getFirstDirectoryOfType(classOf[ExifSubIFDDirectory])
    LocalDateTime.ofInstant(directory.getDateOriginal().toInstant, ZoneId.systemDefault())
  }

  //val path = dateTime.f

}
