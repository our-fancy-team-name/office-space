package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.enums.RestClientConst.SUPER_ADMIN;
import static com.ourfancyteamname.officespace.test.services.AssertionHelper.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.ourfancyteamname.officespace.dtos.PermissionDto;
import com.ourfancyteamname.officespace.enums.PermissionCode;
import com.ourfancyteamname.officespace.test.annotations.IntegrationTest;

@IntegrationTest
class PermissionControllerITest {

  public static final String TEMPLATE_FOR_TEST = "restTemplateForTest";

  @Autowired
  @Qualifier(PermissionControllerITest.TEMPLATE_FOR_TEST)
  private RestTemplate restTemplateForTest;

  @Test
  void getPermissionByRole() {
    var permissionDtos = restTemplateForTest.getForObject("permission/" + SUPER_ADMIN, PermissionDto[].class);
    String[] actual = Arrays.stream(permissionDtos)
        .map(PermissionDto::getCode)
        .toArray(String[]::new);
    String[] expect = Arrays.stream(PermissionCode.values())
        .map(PermissionCode::getName)
        .toArray(String[]::new);
    assertArrayEquals(expect, actual);
  }
}
