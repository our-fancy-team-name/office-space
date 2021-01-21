package com.ourfancyteamname.officespace.db.converters.enums;

import com.ourfancyteamname.officespace.enums.ClusterNodePosition;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class ClusterNodePositionConverter extends AbstractHibernateEnumConverter<ClusterNodePosition, String> {
  @Override
  protected Class<ClusterNodePosition> getClazz() {
    return ClusterNodePosition.class;
  }
}
