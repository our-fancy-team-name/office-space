package com.ourfancyteamname.officespace.db.entities.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`role_user_list_view`")
public class RoleUserListView implements Serializable {

  private static final long serialVersionUID = -311943914363931128L;

  @Id
  @Column(name = "`id`")
  private int id;

  @Column(name = "`code`")
  private String code;

  @Column(name = "`description`")
  private String description;

  @Column(name = "`users`")
  private String users;
}
