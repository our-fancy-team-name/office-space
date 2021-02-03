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
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.dtos.RdfDto;
import com.ourfancyteamname.officespace.enums.CacheName;
import com.ourfancyteamname.officespace.rdf.consts.QualifierName;
import com.ourfancyteamname.officespace.rdf.repos.RdfRepository;
import com.ourfancyteamname.officespace.services.RdfService;

import lombok.SneakyThrows;

@Service
public class RdfServiceImpl implements RdfService {

  @Autowired
  @Qualifier(RdfRepository.NATIVE_REPO)
  private RdfRepository rdfRepository;

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
    Field[] allFields = getFields();
    return Arrays.stream(allFields)
        .filter(f -> f.getType().isAssignableFrom(IRI.class))
        .map(this::get)
        .map(IRI.class::cast)
        .collect(Collectors.toList());
  }

  @Override
  public List<String> getDefinedNamespace() {
    Field[] allFields = getFields();
    return Arrays.stream(allFields)
        .filter(f -> StringUtils.endsWith(f.getName(), QualifierName.NAMESPACE))
        .map(this::get)
        .map(String.class::cast)
        .collect(Collectors.toList());
  }

  @Override
  public void create(RdfDto<?, ?> rdfDto) {
    rdfRepository.save(new ModelBuilder()
        .subject(rdfDto.getSubject())
        .add(rdfDto.getPredicate(), rdfDto.getSubject())
        .build());
  }

  @SneakyThrows
  private Class<?> forName(String name) {
    return Class.forName(name);
  }

  @SneakyThrows
  private Object get(Field field) {
    return field.get(null);
  }

  private Field[] getFields() {
    final var provider = new ClassPathScanningCandidateComponentProvider(false);
    provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));
    return provider.findCandidateComponents(QualifierName.VOCABULARY)
        .stream()
        .map(BeanDefinition::getBeanClassName)    // get all String represent of class inside @QualifierName.VOCABULARY
        .filter(Predicate.not(QualifierName.EXCLUDED_CLASS::contains))// excluded some of them
        .map(this::forName)                       // map String to actual class
        .map(Class::getFields)                    // get all fields inside class
        .flatMap(Arrays::stream)
        .toArray(Field[]::new);
  }
}
