package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.DataBaseDirection;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class DataBaseDirectionTest {
  public static final String[] names = {"ASC", "DESC"};

  @Test
  void name() {
    Stream.of(DataBaseDirection.values())
        .map(Enum::name)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
