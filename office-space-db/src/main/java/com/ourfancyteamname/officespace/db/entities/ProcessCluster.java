package com.ourfancyteamname.officespace.db.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "`process_cluster`")
@EqualsAndHashCode(callSuper = true)
public class ProcessCluster extends AbstractProcessObject {
  private static final long serialVersionUID = -1652730901265423406L;
}
