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
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`package`")
public class Package implements Serializable {
  private static final long serialVersionUID = -1236737789465423406L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`product-id`")
  private Integer productId;

  @Column(name = "`serial-number`")
  private String serialNumber;

  @Column(name = "`description`")
  private String description;

}
