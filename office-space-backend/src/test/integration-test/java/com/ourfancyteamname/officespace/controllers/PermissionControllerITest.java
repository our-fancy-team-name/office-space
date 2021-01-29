package com.ourfancyteamname.officespace.controllers;

import static com.ourfancyteamname.officespace.test.enums.RestClientConst.SUPER_ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Autowired
  @Qualifier("restTemplateForTest")
  private RestTemplate restTemplateForTest;

  @Test
  void getPermissionByRole() {
    var permissionDtos = restTemplateForTest.getForObject("permission/" + SUPER_ADMIN, PermissionDto[].class);
    Arrays.stream(permissionDtos)
        .map(PermissionDto::getCode)
        .forEach(c -> assertTrue(Arrays.stream(PermissionCode.values())
            .map(PermissionCode::getName)
            .anyMatch(c::equals)));
    assertEquals(PermissionCode.values().length, permissionDtos.length);
  }
}
