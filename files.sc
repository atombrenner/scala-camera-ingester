import java.io.File
import java.nio.file.{FileStore, FileSystems, Files, Paths}

import scala.collection.JavaConverters._

val folder = Paths.get("/media/christian/DATA/Daten/Bilder/Photos/2017/04 April/")

val regex = """^\d{4}-\d{2}-\d{2} (\d{3})\..*""".r

Files.list(folder).iterator().asScala.toSeq.head.getParent

val parkingFolder = Paths.get("/home/christian/test")
Files.createDirectories(Paths.get("/home/christian/test"))

folder.getRoot == parkingFolder.getRoot

val stores = FileSystems.getDefault().getFileStores().asScala

for (store: FileStore <- stores.take(2)) {
  println(store.toString)
  println(store.name())
  println(store.`type`())
}
