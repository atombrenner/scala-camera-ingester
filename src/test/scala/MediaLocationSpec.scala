import java.io.File

import org.scalatest.FunSpec

class MediaLocationSpec extends FunSpec {

  describe("MediaLocation") {

    val mediaLocation = new MediaLocation()

    val folder = new File("/media/christian/DATA/Daten/Bilder/Photos/2017/04 April/")

    it("should get next number") {
        assert(mediaLocation.getNextNumber(folder) == 52)
    }

  }
}