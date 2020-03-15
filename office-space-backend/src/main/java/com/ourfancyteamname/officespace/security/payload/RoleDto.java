package com.ourfancyteamname.officespace.security.payload;

import com.ourfancyteamname.officespace.data.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

  private static final long serialVersionUID = 4844040204809916570L;

  private String authority;

  private Boolean isUsing;

  public RoleDto(Role role, Role currentlyUse) {
    this.authority = role.getCode();
    this.isUsing = role.getId() == currentlyUse.getId();
  }
}
