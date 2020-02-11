import java.nio.file._
import scala.collection.JavaConverters._

object CameraIngesterApp extends App {

  println("Camera Ingester")

  // sudo mkdir /run/media/data
  // sudo mount /dev/sdb2 /run/media/data

  val photoLibrary = new MediaLibrary(Paths.get("/run/media/data/Daten/Bilder/Photos/"), MediaFile.jpg)
  val videoLibrary = new MediaLibrary(Paths.get("/run/media/data/Daten/Video/Eigene/"), MediaFile.mp4)

  // Parameters
  // MediaFolder
  // Optional Source

  val roots = FileSystems.getDefault.getRootDirectories.asScala
  val potentialSources = if (roots.head.toString == "/") { // Linux
    Files.list(roots.head.resolve("run/media").resolve(System.getProperty("user.name"))).iterator.asScala
  } else {
    roots
  }
  var sources = potentialSources.map(_.resolve("DCIM")).filter(Files.isDirectory(_)).toSeq
  //sources = Seq(Paths.get("/data/Google Drive/Google Fotos/")) ++ sources

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
