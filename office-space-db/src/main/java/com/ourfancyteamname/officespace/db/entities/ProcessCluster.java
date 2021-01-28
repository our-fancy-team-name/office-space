package com.ourfancyteamname.officespace.db.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "`process_cluster`")
@EqualsAndHashCode(callSuper = true)
public class ProcessCluster extends AbstractProcessObject {
  private static final long serialVersionUID = -1652730901265423406L;
}
