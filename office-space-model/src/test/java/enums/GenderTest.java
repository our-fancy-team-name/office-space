package enums;

import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.Assert;
import org.junit.Test;

public class GenderTest {

  @Test
  public void name() {
    Assert.assertEquals("FEMALE", Gender.FEMALE.getName());
    Assert.assertEquals("MALE", Gender.MALE.getName());
  }
}
