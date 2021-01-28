package com.ourfancyteamname.officespace.db.entities.view;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "`user_role_list_view`")
public class UserRoleListView implements Serializable {

  private static final long serialVersionUID = -311943895663931128L;

  @Id
  @Column(name = "`id`")
  private int id;

  @Column(name = "`username`")
  private String username;

  @Column(name = "`name`")
  private String name;

  @Column(name = "`email`")
  private String email;

  @Column(name = "`roles`")
  private String roles;
}
