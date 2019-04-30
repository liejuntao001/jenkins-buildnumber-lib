import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertNotSame

class BuildNumberTest extends BasePipelineTest {
  def bn

  @Before
  void setUp() {
    super.setUp()
    // load
    bn = loadScript("vars/buildnumber.groovy")
  }

  @Test
  void testGet() {
    def server = "https://buildnumber1.herokuapp.com/"
    def uuid = "e9461f1c-ef78-4162-bcb7-e83da7287614"
    def ret = bn.get(server, uuid)

    assertNotSame "result:", 0, ret
  }

  @Test
  void testGetFailed() {
    // a bad host
    def server = "https://buildnumber1.herokuapp.com1/"
    def uuid = "e9461f1c-ef78-4162-bcb7-e83da7287614"
    def ret = bn.get(server, uuid)

    assertEquals "result:", 0, ret
  }
}
