package com.ourfancyteamname.officespace.configurations;

import com.ourfancyteamname.officespace.security.AuthEntryPointJwt;
import com.ourfancyteamname.officespace.security.AuthTokenFilter;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] METHOD_ALLOWED = {"HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"};

  private static final String[] NO_AUTH_PATH = {"/auth/signin", "/auth/version"};

  private static final String API = "/api/**";

  @Value("${cors.disable-CSRF}")
  private boolean isDisableCSRF;

  @Value("${spring.config.import}")
  private String a;

  @Autowired
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    if (isDisableCSRF) {
      http.csrf().disable();
    }
    http.cors()
        .configurationSource(getCorsConfigurationSource())
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        .antMatchers(NO_AUTH_PATH).permitAll()
        .antMatchers(API).authenticated()
        .anyRequest().permitAll();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  private CorsConfigurationSource getCorsConfigurationSource() {
    return request -> {
      CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.setAllowedMethods(Arrays.asList(METHOD_ALLOWED));
      return corsConfiguration.applyPermitDefaultValues();
    };
  }
}
