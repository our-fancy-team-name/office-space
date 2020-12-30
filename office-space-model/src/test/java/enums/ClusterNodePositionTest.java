package enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class ClusterNodePositionTest {
  public static final String[] names = {"TAIL", "HEAD", "BODY"};

  @Test
  public void name() {
    Stream.of(ClusterNodePosition.values())
        .map(ClusterNodePosition::getName)
        .forEach(s -> Assert.assertTrue(ArrayUtils.contains(names, s)));
  }
}
