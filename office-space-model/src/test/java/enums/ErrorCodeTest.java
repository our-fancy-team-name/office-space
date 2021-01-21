package enums;

import com.ourfancyteamname.officespace.enums.ErrorCode;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class ErrorCodeTest {
  public static final String[] names = {"DUPLICATED", "NOT_FOUND", "IN_USE"};

  @Test
  public void name() {
    Stream.of(ErrorCode.values())
        .map(Enum::name)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
