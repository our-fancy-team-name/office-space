package com.ourfancyteamname.officespace.dtos.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto implements Serializable {

  private static final long serialVersionUID = 4844040204809916570L;

  private Integer id;

  private String authority;

  private String description;

  private Boolean isUsing;

}
