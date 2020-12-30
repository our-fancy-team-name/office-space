package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ClusterNodePositionConverter extends AbstractHibernateEnumConverter<ClusterNodePosition, String> {
  public ClusterNodePositionConverter() {
    super(ClusterNodePosition.class);
  }
}
