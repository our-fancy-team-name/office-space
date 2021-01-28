package enums;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class PermissionCodeTest {
  public static final String[] names =
      {"USER_EDIT", "ROLE_EDIT", "PRD_EDIT", "PKG_EDIT", "CLUS_EDIT", "NODE_EDIT", "PRCS_EDIT"};

  @Test
  void name() {
    Stream.of(PermissionCode.values())
        .map(PermissionCode::getName)
        .forEach(s -> assertTrue(ArrayUtils.contains(names, s)));
  }
}
