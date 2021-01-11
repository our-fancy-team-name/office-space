package enums;

import com.ourfancyteamname.officespace.enums.CharConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class CharConstantsTest {
  public static final String[] names = {":"};

  @Test
  public void name() {
    Stream.of(CharConstants.values())
        .map(CharConstants::getValue)
        .forEach(s -> Assert.assertTrue(ArrayUtils.contains(names, s)));
  }
}
