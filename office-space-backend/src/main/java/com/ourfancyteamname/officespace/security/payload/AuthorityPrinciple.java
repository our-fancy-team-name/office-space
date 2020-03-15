package com.ourfancyteamname.officespace.security.payload;

import com.ourfancyteamname.officespace.data.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class AuthorityPrinciple implements GrantedAuthority {

  private static final long serialVersionUID = 4844040204809916570L;

  private String authority;

  private Boolean isUsing;

  public AuthorityPrinciple(Role role, Role currentlyUse) {
    this.authority = role.getCode();
    this.isUsing = role.getId() == currentlyUse.getId();
  }

  @Override
  public String getAuthority() {
    return authority;
  }
}
