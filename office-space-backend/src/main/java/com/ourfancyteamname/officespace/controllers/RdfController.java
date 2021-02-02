package com.ourfancyteamname.officespace.controllers;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rdf")
public class RdfController {

  @GetMapping("/defined-vocabulary")
  public ResponseEntity<List<String>> getDefinedVocabulary() throws ClassNotFoundException {
    final var provider = new ClassPathScanningCandidateComponentProvider(false);
    provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

    final var classes = provider.findCandidateComponents("org.eclipse.rdf4j.model.vocabulary");
    List<String> types = classes.stream().map(BeanDefinition::getBeanClassName).collect(Collectors.toList());
    return ResponseEntity.ok(types);
  }
}
