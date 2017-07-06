import java.io.File
import java.nio.file.{Files, Paths}
import scala.collection.JavaConverters._

val folder = Paths.get("/media/christian/DATA/Daten/Bilder/Photos/2017/04 April/")

val regex = """^\d{4}-\d{2}-\d{2} (\d{3})\..*""".r

Files.list(folder).iterator().asScala.toSeq.head.getParent
