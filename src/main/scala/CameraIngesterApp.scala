import java.nio.file.{Files, Path, Paths}
import java.time._
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.imaging.ImageMetadataReader.readMetadata

import scala.collection.JavaConverters._
import com.drew.metadata.Metadata

import scala.util.matching.Regex

object CameraIngesterApp extends App {

  val linuxPanasonic = "/media/christian/9016-4EF8/DCIM"

  // TODO: find DCIM folders

  println("Camera Ingester")

  val dataRoot = Paths.get("/media/christian/DATA/TestDataRoot")
  val camera = Paths.get("/media/christian/DATA/TestImport")
  val path2017 = Paths.get("/media/christian/DATA/Daten/Bilder/Photos/2017")
  val path2016 = Paths.get("/media/christian/DATA/Daten/Bilder/Photos/2016")
  val jpgPattern = """(?i).*\.jpg$""".r.pattern

  val library = new MediaLibrary(dataRoot)
  library.ingest(camera)

  // TODO: detect Memory Cards for automatic import


}
