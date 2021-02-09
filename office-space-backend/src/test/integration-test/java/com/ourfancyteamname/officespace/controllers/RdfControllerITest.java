package com.ourfancyteamname.officespace.controllers;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.ourfancyteamname.officespace.dtos.ColumnSearchRequest;
import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;
import com.ourfancyteamname.officespace.dtos.TablePagingRequest;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.test.annotations.IntegrationTest;

@IntegrationTest
class RdfControllerITest {

  @Autowired
  @Qualifier(PermissionControllerITest.TEMPLATE_FOR_TEST)
  private RestTemplate restTemplateForTest;

  @Test
  void getDefinedIRIs() {
    String url = "rdf/iris";
    List<RdfIriDisplayDto> result = restTemplateForTest.getForObject(url, List.class);
    Assertions.assertEquals(1810, result.size());
  }

  @Test
  void getDefinedIRIsFilter() {
    String url = "rdf/iris/search";
    var tableSearchRequest = TableSearchRequest.builder()
        .pagingRequest(TablePagingRequest.builder().pageSize(100).build())
        .columnSearchRequests(Collections.singletonList(ColumnSearchRequest.builder().term("name").build()))
        .build();
    LinkedHashMap result = restTemplateForTest.postForObject(url, tableSearchRequest, LinkedHashMap.class);
    Assertions.assertEquals(50, ((List<RdfIriDisplayDto>) result.get("content")).size());
  }

  @Test
  void getDefinedNamespace() {
    String url = "rdf/namespace";
    List<String> result = restTemplateForTest.getForObject(url, List.class);
    Assertions.assertEquals(43, result.size());
  }

}
