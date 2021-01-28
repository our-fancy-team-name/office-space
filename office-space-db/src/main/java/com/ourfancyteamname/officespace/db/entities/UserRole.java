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
@Table(name = "`user_role`")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
