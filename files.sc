import java.nio.file.{Files, Paths}

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileMetadataDirectory

import scala.collection.JavaConverters._
import org.mp4parser.IsoFile
import org.mp4parser.boxes.apple.AppleNameBox
import org.mp4parser.tools.Path
import java.io.{File, FileInputStream, FileNotFoundException}

val path = Paths.get("/home/christian/DCIM/113___07/IMG_1259.JPG")

Files.list(path.getParent).iterator.asScala.partition(_.toString.lastIndexOf(".") > 0).forEach(println(_))

val name = path.getFileName.toString
name.substring(0, name.lastIndexOf('.'))