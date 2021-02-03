package com.ourfancyteamname.officespace.services.impl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.rdf4j.model.IRI;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.enums.CacheName;
import com.ourfancyteamname.officespace.rdf.consts.QualifierName;
import com.ourfancyteamname.officespace.services.RdfService;

import lombok.SneakyThrows;

@Service
public class RdfServiceImpl implements RdfService {

  @Resource
  private RdfService self;

  @Override
  public List<IRI> getDefinedIRLs(String iri) {
    var iris = self.getDefinedIRLs();
    return StringUtils.isBlank(iri) ?
        iris :
        iris.stream().filter(i -> StringUtils.containsIgnoreCase(i.toString(), iri)).collect(Collectors.toList());
  }

  @Override
  @Cacheable(value = CacheName.IRIS)
  public List<IRI> getDefinedIRLs() {
    final var provider = new ClassPathScanningCandidateComponentProvider(false);
    provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
    return provider.findCandidateComponents(QualifierName.VOCABULARY)
        .stream()
        .map(BeanDefinition::getBeanClassName)    // get all String represent of class inside @QualifierName.VOCABULARY
        .filter(Predicate.not(QualifierName.EXCLUDED_CLASS::contains))// excluded some of them
        .map(this::forName)                       // map String to actual class
        .map(Class::getFields)                    // get all fields inside class
        .flatMap(Arrays::stream)                  // flatmap
        .filter(f -> f.getType().isAssignableFrom(IRI.class))// filter IRI class
        .map(this::get)                            // get value of the filtered field
        .collect(Collectors.toList());
  }

  @SneakyThrows
  private Class<?> forName(String name) {
    return Class.forName(name);
  }

  @SneakyThrows
  private IRI get(Field field) {
    return (IRI) field.get(null);
  }
}
