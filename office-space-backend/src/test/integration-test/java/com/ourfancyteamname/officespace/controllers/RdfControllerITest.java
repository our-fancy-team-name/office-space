package com.ourfancyteamname.officespace.controllers;

import java.util.List;

import org.eclipse.rdf4j.model.IRI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;

import com.ourfancyteamname.officespace.test.annotations.IntegrationTest;

@IntegrationTest
class RdfControllerITest {

  @Autowired
  @Qualifier(PermissionControllerITest.TEMPLATE_FOR_TEST)
  private RestTemplate restTemplateForTest;

  @Test
  void getDefinedIRIs() {
    String url = "rdf/iris";
    List<IRI> result = restTemplateForTest.getForObject(url, List.class);
    Assertions.assertEquals(1810, result.size());
  }

  @Test
  void getDefinedIRIsFilter() {
    String url = "rdf/iris/name";
    List<IRI> result = restTemplateForTest.getForObject(url, List.class);
    Assertions.assertEquals(50, result.size());
  }

}
