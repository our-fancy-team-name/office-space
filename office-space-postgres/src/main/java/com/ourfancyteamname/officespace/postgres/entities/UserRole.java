package com.ourfancyteamname.officespace.postgres.entities;

import lombok.AllArgsConstructor;
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
@Table(name = "`user_role`")
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

  private static final long serialVersionUID = 6317463440830284105L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`id`")
  private int id;

  @Column(name = "`user_id`")
  private Integer userId;

  @Column(name = "`role_id`")
  private Integer roleId;

  @Column(name = "`recently_use`")
  private Boolean isRecentlyUse;

}
