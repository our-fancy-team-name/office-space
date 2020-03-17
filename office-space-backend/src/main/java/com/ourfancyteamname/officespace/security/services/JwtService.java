package com.ourfancyteamname.officespace.security.services;

import com.ourfancyteamname.officespace.security.payload.UserDetailsPrinciple;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtService {

  public static final String TOKEN_TYPE = "Bearer";

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    UserDetailsPrinciple userPrincipal = (UserDetailsPrinciple) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("SignatureException JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("MalformedJwtException JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("ExpiredJwtException token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("UnsupportedJwtException token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("IllegalArgumentException claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
