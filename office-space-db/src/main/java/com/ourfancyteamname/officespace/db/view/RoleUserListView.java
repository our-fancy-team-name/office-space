package com.ourfancyteamname.officespace.db.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "`role_user_list_view`")
@AllArgsConstructor
@NoArgsConstructor
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
