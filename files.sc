import java.io.File
import java.nio.file.{FileStore, FileSystems, Files, Paths}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileMetadataDirectory

import scala.collection.JavaConverters._

val image = Paths.get("/data/Google Drive/Google Fotos/IMG-20160430-WA0000.jpg")
val metadata = readMetadata(image.toFile)

metadata.getFirstDirectoryOfType(classOf[FileMetadataDirectory]).getDate(FileMetadataDirectory.TAG_FILE_MODIFIED_DATE).toInstant


for (dir: Directory <- readMetadata(image.toFile).getDirectories.iterator.asScala) {
  println(dir)
  println(dir.getTags)
}
