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
@Table(name = "`role`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable {
  private static final long serialVersionUID = 3446737422191923606L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`code`")
  private String code;

}
