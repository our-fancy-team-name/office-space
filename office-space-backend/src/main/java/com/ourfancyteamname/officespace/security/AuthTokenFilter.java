package com.ourfancyteamname.officespace.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ourfancyteamname.officespace.security.services.JwtService;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final var jwt = parseJwt(request);
    if (jwtService.validateJwtToken(jwt)) {
      final var userDetails = userDetailsService.loadUserByUsername(jwtService.getUserNameFromJwtToken(jwt));
      userDetails.setCurrentRole(request.getHeader("Role"));
      final var authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    final var headerAuth = request.getHeader("Authorization");
    if (StringUtils.length(headerAuth) >= 7 && headerAuth.startsWith(JwtService.TOKEN_TYPE)) {
      return headerAuth.substring(7);
    }
    return null;
  }
}
