package com.ourfancyteamname.officespace.db.entities;

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
@Table(name = "`role_permission`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
