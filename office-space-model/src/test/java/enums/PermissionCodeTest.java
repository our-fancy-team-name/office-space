package enums;

import com.ourfancyteamname.officespace.enums.PermissionCode;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class PermissionCodeTest {
  public static final String[] names =
      {"USER_EDIT", "ROLE_EDIT", "PRD_EDIT", "PKG_EDIT", "CLUS_EDIT", "NODE_EDIT", "PRCS_EDIT"};

  @Test
  void name() {
    Stream.of(PermissionCode.values())
        .map(PermissionCode::getName)
        .forEach(s -> Assertions.assertTrue(ArrayUtils.contains(names, s)));
  }
}
