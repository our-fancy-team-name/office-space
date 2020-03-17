package com.ourfancyteamname.officespace.security.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ourfancyteamname.officespace.postgres.enums.PermissionCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsPrinciple implements UserDetails {

  private static final long serialVersionUID = -950644502654056189L;

  private String username;

  private String email;

  private Collection<RoleDto> roles;

  private List<PermissionCode> permissionCodes;

  @JsonIgnore
  private String password;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

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
