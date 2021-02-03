package com.ourfancyteamname.officespace.rdf.consts;

import java.util.Arrays;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class QualifierName {
  public static final String VOCABULARY = "org.eclipse.rdf4j.model.vocabulary";
  public static final List<String> EXCLUDED_CLASS =
      Arrays.asList(QualifierName.VOCABULARY + ".XMLSchema", QualifierName.VOCABULARY + ".Vocabularies",
          QualifierName.VOCABULARY + ".XSD$Datatype");
  public static final String NAMESPACE = "NAMESPACE";
}
