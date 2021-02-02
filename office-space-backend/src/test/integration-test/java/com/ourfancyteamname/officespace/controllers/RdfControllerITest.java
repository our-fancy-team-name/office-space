package com.ourfancyteamname.officespace.controllers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.IRI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.web.client.RestTemplate;

import com.ourfancyteamname.officespace.test.annotations.IntegrationTest;

@IntegrationTest
class RdfControllerITest {

  @Autowired
  @Qualifier("restTemplateForTest")
  private RestTemplate restTemplateForTest;

  @Test
  void ahihi() {
    String url = "rdf/types";
    restTemplateForTest.getForObject(url, List.class);
  }

  public static void main(String[] args) {
//    Class foaf = Class.forName("org.eclipse.rdf4j.model.vocabulary.AFN");
//    List<String> names = Arrays.stream(foaf.getFields())
//        .filter(f -> f.getType().isAssignableFrom(IRI.class))
//        .map(Field::getName)
//        .collect(Collectors.toList());

    final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
// add include filters which matches all the classes (or use your own)
    provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

// get matching classes defined in the package
    final Set<BeanDefinition> classes = provider.findCandidateComponents("org.eclipse.rdf4j.model.vocabulary");

// this is how you can load the class type from BeanDefinition instance
    List<String> names = classes.stream().map(b -> {
      try {
        return Class.forName(b.getBeanClassName());
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      return null;
    }).map(Class::getFields)
        .flatMap(Arrays::stream)
        .filter(f -> f.getType().isAssignableFrom(IRI.class))
        .map(Field::getName)
        .collect(Collectors.toList());
    System.out.println(names.size());
    System.out.println(names.stream().distinct().collect(Collectors.toList()).size());

  }
}
