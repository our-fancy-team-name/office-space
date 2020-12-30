package enums;

import com.ourfancyteamname.officespace.enums.DataBaseOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class DataBaseOperationTest {

  public static final String[] names =
      {"LESS_THAN", "LESS_THAN_OR_EQUAL_TO", "GREATER_THAN", "GREATER_THAN_OR_EQUAL_TO", "EQUAL", "LIKE", "NOT_EQUAL"};

  @Test
  public void name() {
    Stream.of(DataBaseOperation.values())
        .map(Enum::name)
        .forEach(s -> Assert.assertTrue(ArrayUtils.contains(names, s)));
  }
}
