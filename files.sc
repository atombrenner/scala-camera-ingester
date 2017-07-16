import java.nio.file.Paths

import com.drew.imaging.ImageMetadataReader.readMetadata
import com.drew.metadata.Directory
import com.drew.metadata.exif.ExifSubIFDDirectory
import com.drew.metadata.file.FileMetadataDirectory

import scala.collection.JavaConverters._
import org.mp4parser.IsoFile
import org.mp4parser.boxes.apple.AppleNameBox
import org.mp4parser.tools.Path
import java.io.{File, FileInputStream, FileNotFoundException}

val path = Paths.get("/data/Google Drive/Google Fotos/VID-20160408-WA0000.mp4")

val videoFile = path.toFile
val isoFile = new IsoFile(new FileInputStream(path.toString).getChannel)

isoFile.getMovieBox.getTrackCount
isoFile.getMovieBox.
isoFile.getMovieBox.getMovieHeaderBox.getCreationTime.toInstant

isoFile.close()