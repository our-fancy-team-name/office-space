package com.ourfancyteamname.officespace.db.converters.enums;

import javax.persistence.Converter;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;

@Converter(autoApply = true)
public class ClusterNodePositionConverter extends AbstractHibernateEnumConverter<ClusterNodePosition, String> {
  @Override
  protected Class<ClusterNodePosition> getClazz() {
    return ClusterNodePosition.class;
  }
}
