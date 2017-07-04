import java.io.File

class MediaLocation {

  // input date, root => output complete path

  private val targetPattern = """^\d{4}-\d{2}-\d{2} (\d{3})\..*""".r

  val monthName = Array("01 Januar", "02 Februar", "03 März", "04 April", "05 Mai", "06 Juni", "07 Juli", "08 August", "09 September", "10 Oktober", "11 November", "12 Dezember")

  def getNextNumber(folder: File): Int = {
    assert(folder.isDirectory)

    folder.list.map {
      case targetPattern(number) => number.toInt
      case _ => 0
    }.max + 1
  }

}

//class MediaFile()

object MediaLocation {

  // include rename existing

  private def getNextNumber(folder: String) = {

  }

}

//case class MediaFile(val )