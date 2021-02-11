package com.ourfancyteamname.officespace.db.entities;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`namespace`")
public class LocalName implements Serializable {
  @Serial
  private static final long serialVersionUID = 5768537924857838382L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`namespace-id`")
  private Integer namespaceId;

  @Column(name = "`value`")
  private String value;

  @Column(name = "`description`")
  private String description;
}
