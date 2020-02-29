package com.ourfancyteamname.officespace.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {

  private String token;
  private String type;
  private Long id;
  private String username;
  private String email;
}
