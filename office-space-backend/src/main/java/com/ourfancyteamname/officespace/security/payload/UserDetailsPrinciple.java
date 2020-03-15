package com.ourfancyteamname.officespace.security.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourfancyteamname.officespace.data.enums.PermissionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsPrinciple implements UserDetails {

  private static final long serialVersionUID = -950644502654056189L;

  private String username;

  private String email;

  private Collection<? extends GrantedAuthority> authorities;

  private List<PermissionCode> permissionCodes;

  @JsonIgnore
  private String password;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
