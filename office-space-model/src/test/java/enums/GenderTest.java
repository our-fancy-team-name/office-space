package enums;

import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.Assert;
import org.junit.Test;

public class GenderTest {

  @Test
  public void name() {
    Assert.assertEquals("FEMALE", Gender.FEMALE.name());
    Assert.assertEquals("MALE", Gender.MALE.name());
  }
}
