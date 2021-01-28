package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.CharConstants;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class CharConstantsTest {
  public static final String[] names = {":"};

  @Test
  void name() {
    Stream.of(CharConstants.values())
        .map(CharConstants::getValue)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
