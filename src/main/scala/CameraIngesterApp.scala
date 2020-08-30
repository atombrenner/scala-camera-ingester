import java.nio.file._
import scala.collection.JavaConverters._

object CameraIngesterApp extends App {

  println("Camera Ingester")

  // sudo mkdir ~/Data
  // sudo mount /dev/sda2 ~/Data

  val photoLibrary = new MediaLibrary(Paths.get("/home/christian/Data/Daten/Bilder/Photos/"), MediaFile.jpg)
  val videoLibrary = new MediaLibrary(Paths.get("/home/christian/Data/Daten/Video/Eigene/"), MediaFile.mp4)

  // Parameters
  // MediaFolder
  // Optional Source

  val roots = FileSystems.getDefault.getRootDirectories.asScala
  val potentialSources = if (roots.head.toString == "/") { // Linux
    Files.list(roots.head.resolve("run/media").resolve(System.getProperty("user.name"))).iterator.asScala
  } else {
    roots
  }
  val sources = potentialSources.map(_.resolve("DCIM")).filter(Files.isDirectory(_)).toSeq
  //var sources = Seq(Paths.get("/home/christian/Dropbox/DCIM")) // ++ sources

  sources.foreach{ source =>
    println(s"Importing from $source")
    
    videoLibrary.ingest(source)  // order is important, otherwise the thumbnails of videos would be imported as photos
    photoLibrary.ingest(source)

    // TODO: delete empty folders inside of DCIM folders

    if (Files.list(source).count == 0) {
       Files.delete(source)
    } else {
      println(s"Warning: $source not empty")
    }
  }

}
