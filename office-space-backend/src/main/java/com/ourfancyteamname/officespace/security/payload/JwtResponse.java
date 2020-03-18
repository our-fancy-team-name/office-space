package com.ourfancyteamname.officespace.security.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse<U extends UserDetails> {

  private String token;
  private String type;
  private U userDetails;
}
