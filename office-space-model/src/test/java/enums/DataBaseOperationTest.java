package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class DataBaseOperationTest {

  public static final String[] names =
      {"LESS_THAN", "LESS_THAN_OR_EQUAL_TO", "GREATER_THAN", "GREATER_THAN_OR_EQUAL_TO", "EQUAL", "LIKE", "NOT_EQUAL"};

  @Test
  void name() {
    Stream.of(DataBaseOperation.values())
        .map(Enum::name)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
