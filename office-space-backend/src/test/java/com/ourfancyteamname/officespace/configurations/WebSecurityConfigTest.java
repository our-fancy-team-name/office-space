package com.ourfancyteamname.officespace.configurations;

import com.ourfancyteamname.officespace.security.AuthEntryPointJwt;
import com.ourfancyteamname.officespace.security.AuthTokenFilter;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class WebSecurityConfigTest {

  private static final String[] METHOD_ALLOWED = {"HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"};

  @InjectMocks
  private WebSecurityConfig webSecurityConfig;

  @Mock
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Mock
  private AuthEntryPointJwt unauthorizedHandler;

  @Mock
  WebSecurityConfigurerAdapter adapter;

  @Test
  public void bean() throws Exception {
    Assert.assertSame(AuthTokenFilter.class, webSecurityConfig.authenticationJwtTokenFilter().getClass());
    Assert.assertSame(BCryptPasswordEncoder.class, webSecurityConfig.passwordEncoder().getClass());
  }

  @Test
  public void configure() throws Exception {
    AuthenticationManagerBuilder managerBuilder =
        new AuthenticationManagerBuilder(Mockito.mock(ObjectPostProcessor.class));
    webSecurityConfig.configure(managerBuilder);
    Assert.assertEquals(userDetailsService, managerBuilder.getDefaultUserDetailsService());
  }

  @Test
  public void configureHttp() throws Exception {
    HttpSecurity httpSecurity =
        new HttpSecurity(Mockito.mock(ObjectPostProcessor.class), Mockito.mock(AuthenticationManagerBuilder.class),
            Mockito.mock(Map.class));
    HttpSecurity httpSecurity2 =
        new HttpSecurity(Mockito.mock(ObjectPostProcessor.class), Mockito.mock(AuthenticationManagerBuilder.class),
            Mockito.mock(Map.class));
    webSecurityConfig.configure(httpSecurity);
    Assert.assertNotSame(httpSecurity2, httpSecurity);
  }

  @Test
  public void pathAuthSignIn() throws Exception {
    Field field = WebSecurityConfig.class.getDeclaredField("AUTH_SIGNIN");
    field.setAccessible(true);
    Assert.assertEquals("/auth/signin", field.get(webSecurityConfig));
  }

  @Test
  public void pathAPI() throws Exception {
    Field field = WebSecurityConfig.class.getDeclaredField("API");
    field.setAccessible(true);
    Assert.assertEquals("/api/**", field.get(webSecurityConfig));
  }

  @Test
  public void corsConfig() throws Exception {
    Method method = WebSecurityConfig.class.getDeclaredMethod("getCorsConfigurationSource");
    method.setAccessible(true);
    CorsConfigurationSource corsConfigurationSource = (CorsConfigurationSource) method.invoke(webSecurityConfig);
    CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(new MockHttpServletRequest());
    Assert.assertArrayEquals(METHOD_ALLOWED, corsConfiguration.getAllowedMethods().toArray());
  }
}
