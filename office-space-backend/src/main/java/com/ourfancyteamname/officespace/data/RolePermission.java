package com.ourfancyteamname.officespace.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`role_permission`")
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission implements Serializable {

  private static final long serialVersionUID = 2490648594038340179L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`role_id`")
  private Integer roleId;

  @Column(name = "`permission_id`")
  private Integer permissionId;
}
