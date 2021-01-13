package enums;

import com.ourfancyteamname.officespace.enums.ErrorObject;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class ErrorObjectTest {
  public static final String[] names = {"NAME", "PART_NUMBER", "CLUSTER", "SERIAL"};

  @Test
  public void name() {
    Stream.of(ErrorObject.values())
        .map(Enum::name)
        .forEach(s -> Assert.assertTrue(ArrayUtils.contains(names, s)));
  }
}
