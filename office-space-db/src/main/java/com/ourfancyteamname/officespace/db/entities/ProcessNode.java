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
@Table(name = "`process_node`")
@EqualsAndHashCode(callSuper = true)
public class ProcessNode extends AbstractProcessObject {
  private static final long serialVersionUID = -6241796290475282002L;
}
