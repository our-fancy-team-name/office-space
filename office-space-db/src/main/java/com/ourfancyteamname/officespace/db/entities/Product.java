package com.ourfancyteamname.officespace.db.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@Entity
@Table(name = "`product`")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

  private static final long serialVersionUID = -276143914573931128L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`part-number`")
  private String partNumber;

  @Column(name = "`name`")
  private String name;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`family`")
  private String family;

  @Column(name = "`cluster-id`")
  private Integer clusterId;
}
