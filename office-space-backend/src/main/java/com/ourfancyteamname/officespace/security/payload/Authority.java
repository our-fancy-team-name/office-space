package com.ourfancyteamname.officespace.security.payload;

import com.ourfancyteamname.officespace.data.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class Authority implements GrantedAuthority {

  private String authority;

  private Boolean isUsing;

  public Authority(Role role, Role currentlyUse) {
    this.authority = role.getCode();
    this.isUsing = role.getId() == currentlyUse.getId();
  }

  @Override
  public String getAuthority() {
    return authority;
  }
}
