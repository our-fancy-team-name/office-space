package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.PackageStatus;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PackageStatusTest {
  public static final String[] names = {"FAIL", "WIP", "PASS"};

  @Test
  void name() {
    Stream.of(PackageStatus.values())
        .map(PackageStatus::getName)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
