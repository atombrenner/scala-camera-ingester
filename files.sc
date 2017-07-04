import java.io.File
import java.nio.file.Files

val folder = new File("/media/christian/DATA/Daten/Bilder/Photos/2017/04 April/")

val regex = """^\d{4}-\d{2}-\d{2} (\d{3})\..*""".r

folder.list.map {
  case regex(number) => number.toInt
  case _ => 0
}.max

