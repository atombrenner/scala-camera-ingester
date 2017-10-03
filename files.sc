import java.nio.file.{Files, Paths}

import scala.collection.JavaConverters._
import org.mp4parser.IsoFile
import org.mp4parser.boxes.apple.AppleNameBox
import org.mp4parser.tools.Path
import java.io.{File, FileInputStream, FileNotFoundException}

val path = Paths.get("/data/Google Drive/Google Fotos/VID-20151226-WA0000.mp4")
//val path = Paths.get("/data/Daten/Video/Eigene/tmp/2016-11-13 001.mp4")
println (path)

val name = path.getFileName.toString
name.substring(0, name.lastIndexOf('.'))


//val channel = new FileInputStream(path.toString).getChannel
//var isoFile = new IsoFile(new FileInputStream(path.toString).getChannel)
//val creation = isoFile.getMovieBox.getMovieHeaderBox.getCreationTime.toInstant
//Files.getLastModifiedTime(path).toInstant