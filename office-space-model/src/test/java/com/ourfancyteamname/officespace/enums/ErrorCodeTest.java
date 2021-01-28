package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.ErrorCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ErrorCodeTest {
  public static final String[] names = {"DUPLICATED", "NOT_FOUND", "IN_USE"};

  @Test
  void name() {
    Stream.of(ErrorCode.values())
        .map(Enum::name)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
