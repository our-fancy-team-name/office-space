package com.ourfancyteamname.officespace.configurations;

import static com.ourfancyteamname.officespace.test.services.MockHelper.mockReturn;
import static com.ourfancyteamname.officespace.test.services.VerifyHelper.verifyInvoke1Time;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ourfancyteamname.officespace.security.AuthTokenFilter;
import com.ourfancyteamname.officespace.security.services.UserDetailsSecurityServiceImpl;
import com.ourfancyteamname.officespace.test.annotations.UnitTest;

@UnitTest
class WebSecurityConfigTest {

  private static final String[] METHOD_ALLOWED = {"HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"};

  @InjectMocks
  private WebSecurityConfig webSecurityConfig;

  @Mock
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Test
  void bean() {
    assertSame(AuthTokenFilter.class, webSecurityConfig.authenticationJwtTokenFilter().getClass());
    assertSame(BCryptPasswordEncoder.class, webSecurityConfig.passwordEncoder().getClass());
    assertSame(RestTemplate.class, webSecurityConfig.getRestTemplate().getClass());
  }

  @Test
  void authenticationManagerBean() throws Exception {
    WebSecurityConfig config = new WebSecurityConfig();
    AuthenticationManagerBuilder authenticationBuilder = mock(AuthenticationManagerBuilder.class);
    ApplicationContext context = mock(ApplicationContext.class);
    mockReturn(context.getBeanNamesForType(any(Class.class)), new String[]{});
    ReflectionTestUtils.setField(config, "authenticationBuilder", authenticationBuilder);
    ReflectionTestUtils.setField(config, "context", context);
    config.authenticationManagerBean();
    verifyInvoke1Time(context).getBeanNamesForType(any(Class.class));
  }

  @Test
  void configure() throws Exception {
    var managerBuilder = new AuthenticationManagerBuilder(mock(ObjectPostProcessor.class));
    webSecurityConfig.configure(managerBuilder);
    assertEquals(userDetailsService, managerBuilder.getDefaultUserDetailsService());
  }

  @Test
  void configureHttp() throws Exception {
    var httpSecurity =
        new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class),
            mock(Map.class));
    var httpSecurity2 =
        new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class),
            mock(Map.class));
    webSecurityConfig.configure(httpSecurity);
    assertNotSame(httpSecurity2, httpSecurity);
  }

  @Test
  void pathAuthSignIn() throws Exception {
    Field field = WebSecurityConfig.class.getDeclaredField("NO_AUTH_PATH");
    field.setAccessible(true);
    assertArrayEquals(new String[]{"/auth/signin", "/auth/version"}, (String[]) field.get(webSecurityConfig));
  }

  @Test
  void pathAPI() throws Exception {
    Field field = WebSecurityConfig.class.getDeclaredField("API");
    field.setAccessible(true);
    assertEquals("/api/**", field.get(webSecurityConfig));
  }

  @Test
  void corsConfig() throws Exception {
    Method method = WebSecurityConfig.class.getDeclaredMethod("getCorsConfigurationSource");
    method.setAccessible(true);
    CorsConfigurationSource corsConfigurationSource = (CorsConfigurationSource) method.invoke(webSecurityConfig);
    CorsConfiguration corsConfiguration = corsConfigurationSource.getCorsConfiguration(new MockHttpServletRequest());
    assertArrayEquals(METHOD_ALLOWED, corsConfiguration.getAllowedMethods().toArray());
  }

  @Test
  void corsConfigDisableCsrf() throws Exception {
    ReflectionTestUtils.setField(webSecurityConfig, "isDisableCSRF", true);
    HttpSecurity httpSecurity =
        new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class),
            mock(Map.class));
    HttpSecurity httpSecurity2 =
        new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class),
            mock(Map.class));
    webSecurityConfig.configure(httpSecurity);
    assertNotSame(httpSecurity2, httpSecurity);
  }
}
