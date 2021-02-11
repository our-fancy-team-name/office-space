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
import org.eclipse.rdf4j.model.util.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ourfancyteamname.officespace.dtos.RdfCreateDto;
import com.ourfancyteamname.officespace.dtos.RdfIriDisplayDto;
import com.ourfancyteamname.officespace.dtos.RdfObject;
import com.ourfancyteamname.officespace.dtos.TableSearchRequest;
import com.ourfancyteamname.officespace.enums.CacheName;
import com.ourfancyteamname.officespace.rdf.consts.QualifierName;
import com.ourfancyteamname.officespace.rdf.converters.RdfConverter;
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

  @Autowired
  private RdfConverter rdfConverter;

  @Override
  public Page<RdfIriDisplayDto> getDefinedIRLs(TableSearchRequest tableSearchRequest) {
    var term = tableSearchRequest.getColumnSearchRequests().get(0).getTerm();
    var maxResult = tableSearchRequest.getPagingRequest().getPageSize();
    var iris = self.getDefinedIRLs();
    PageRequest pageable = PageRequest.of(0, maxResult);
    if (StringUtils.isBlank(term)) {
      return new PageImpl<>(iris.stream().limit(maxResult).collect(Collectors.toList()), pageable, iris.size());
    }
    var filtered = iris.stream()
        .filter(i -> StringUtils.containsIgnoreCase(i.getIri().toString(), term))
        .collect(Collectors.toList());
    return new PageImpl<>(filtered.stream().limit(maxResult).collect(Collectors.toList()), pageable, filtered.size());
  }

  @Override
  @Cacheable(value = CacheName.IRIS)
  public List<RdfIriDisplayDto> getDefinedIRLs() {
    Field[] allFields = getFields();
    return Arrays.stream(allFields)
        .filter(f -> f.getType().isAssignableFrom(IRI.class))
        .map(this::get)
        .map(IRI.class::cast)
        .map(rdfConverter::toDto)
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
  public void create(RdfCreateDto rdfCreateDto) {
    var valueFactory = Values.getValueFactory();
    var subject = valueFactory.createIRI(
        rdfCreateDto.getSubject().getNamespace(),
        rdfCreateDto.getSubject().getLocalName()
    );
    var predicate = valueFactory.createIRI(
        rdfCreateDto.getPredicate().getNamespace(),
        rdfCreateDto.getPredicate().getLocalName()
    );
    var object = valueFactory.createIRI(
        rdfCreateDto.getObject().getNamespace(),
        rdfCreateDto.getObject().getLocalName()
    );
    rdfRepository.save(new ModelBuilder()
        .subject(subject)
        .add(predicate, object)
        .build());
  }

  @Override
  public void createIri(RdfObject rdfObject) {

  }

  @Override
  public List<RdfCreateDto> getAll() {
    return rdfRepository.tupleQuery("SELECT ?x ?p ?y WHERE { ?x ?p ?y } ", r ->
        r.stream().map(bs -> RdfCreateDto.builder()
            .object(new RdfObject(bs.getValue("x").stringValue()))
            .predicate(new RdfObject(bs.getValue("p").stringValue()))
            .subject(new RdfObject(bs.getValue("y").stringValue()))
            .build())
            .collect(Collectors.toList()));
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

  public static void main(String[] args) {

    String a = "asdas#asdasd#qweqe";
    StringUtils.split(a, "#");
    System.out.println();
  }
}
