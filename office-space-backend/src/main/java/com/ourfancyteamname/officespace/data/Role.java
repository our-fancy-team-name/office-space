package com.ourfancyteamname.officespace.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`role`")
@AllArgsConstructor
@NoArgsConstructor
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
