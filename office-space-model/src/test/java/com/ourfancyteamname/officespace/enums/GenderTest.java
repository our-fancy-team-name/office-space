package enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.Gender;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class GenderTest {

  @Test
  void name() {
    assertEquals("FEMALE", Gender.FEMALE.getName());
    assertEquals("MALE", Gender.MALE.getName());
  }
}
