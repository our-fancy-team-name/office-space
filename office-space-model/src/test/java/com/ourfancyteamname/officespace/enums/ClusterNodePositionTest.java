package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class ClusterNodePositionTest {
  public static final String[] names = {"TAIL", "HEAD", "BODY"};

  @Test
  void name() {
    Stream.of(ClusterNodePosition.values())
        .map(ClusterNodePosition::getName)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
