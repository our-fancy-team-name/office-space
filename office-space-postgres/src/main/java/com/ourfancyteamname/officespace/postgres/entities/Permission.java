package com.ourfancyteamname.officespace.postgres.entities;


import com.ourfancyteamname.officespace.postgres.converters.PermissionCodeConverter;
import com.ourfancyteamname.officespace.postgres.enums.PermissionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`permission`")
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

  private static final long serialVersionUID = -311943914573931128L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`code`")
  @Convert(converter = PermissionCodeConverter.class)
  private PermissionCode code;
}
