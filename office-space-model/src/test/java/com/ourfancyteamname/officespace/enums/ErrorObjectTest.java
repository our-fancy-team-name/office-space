package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.ErrorObject;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ErrorObjectTest {
  public static final String[] names = {"NAME", "PART_NUMBER", "CLUSTER", "SERIAL"};

  @Test
  void name() {
    Stream.of(ErrorObject.values())
        .map(Enum::name)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
