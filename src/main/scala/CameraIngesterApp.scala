import java.io.IOException
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file._
import java.util.stream

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer


object CameraIngesterApp extends App {

  println("Camera Ingester")

  val library = new MediaLibrary(Paths.get("/data/Daten/Bilder/Photos/"))

  // Parameters
  // MediaFolder
  // Optional Source

  val roots = FileSystems.getDefault.getRootDirectories.asScala
  val potentialSources = if (roots.head.toString == "/") { // Linux
    Files.list(roots.head.resolve("media").resolve(System.getProperty("user.name"))).iterator.asScala
  } else {
    roots
  }
  // val sources = potentialSources.map(_.resolve("DCIM")).filter(Files.isDirectory(_)).toSeq

  val sources = Seq(Paths.get("/data/Daten/Bilder/Photos/2017/Juli/"))

  sources.foreach{ source =>
    library.ingest(source)
    if (Files.list(source).count == 0) {
      Files.delete(source)
    } else {
      println(s"Warning: $source not empty")
    }
  }

  // delete empty source folders
  // sources.filter(Files.list(_).count == 0).foreach(Files.delete)

}
