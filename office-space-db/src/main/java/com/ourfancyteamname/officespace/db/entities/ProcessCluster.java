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
@Table(name = "`process_cluster`")
public class ProcessCluster implements Serializable {
  private static final long serialVersionUID = -1652730901265423406L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`name`")
  private String name;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`code`")
  private String code;

}
