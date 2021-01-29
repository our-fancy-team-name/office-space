package com.ourfancyteamname.officespace.test.enums;


import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RestClientConst {
  public final String USER = "dang";
  public final String SUPER_ADMIN = "SUPER_ADMIN";

  private final String URL_FORMAT_1 = "http://localhost:%s/api%s";
  private final String URL_FORMAT_2 = "http://localhost:%s/api/%s";

  public String urlApi(int port, String endpoint) {
    if (StringUtils.startsWith(endpoint, "/")) {
      return URL_FORMAT_1.formatted(port, endpoint);
    }
    return URL_FORMAT_2.formatted(port, endpoint);
  }

}
