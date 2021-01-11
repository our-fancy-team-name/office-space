package enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class PackageStatusTest {
  public static final String[] names = {"FAIL", "WIP", "PASS"};

  @Test
  public void name() {
    Stream.of(PackageStatus.values())
        .map(Enum::name)
        .forEach(s -> Assert.assertTrue(ArrayUtils.contains(names, s)));
  }
}
