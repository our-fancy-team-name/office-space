package com.ourfancyteamname.officespace.data;

import com.ourfancyteamname.officespace.data.converter.PermissionCodeConverter;
import com.ourfancyteamname.officespace.data.enums.PermissionCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
