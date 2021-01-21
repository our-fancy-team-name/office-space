package enums;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

public class PackageStatusTest {
  public static final String[] names = {"FAIL", "WIP", "PASS"};

  @Test
  public void name() {
    Stream.of(PackageStatus.values())
        .map(PackageStatus::getName)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
