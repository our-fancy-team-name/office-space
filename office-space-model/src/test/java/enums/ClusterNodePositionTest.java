package enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class ClusterNodePositionTest {
  public static final String[] names = {"TAIL", "HEAD", "BODY"};

  @Test
  void name() {
    Stream.of(ClusterNodePosition.values())
        .map(ClusterNodePosition::getName)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
