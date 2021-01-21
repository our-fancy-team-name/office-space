package enums;

import com.ourfancyteamname.officespace.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenderTest {

  @Test
  void name() {
    Assertions.assertEquals("FEMALE", Gender.FEMALE.getName());
    Assertions.assertEquals("MALE", Gender.MALE.getName());
  }
}
