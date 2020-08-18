package com.example.Tuition.security;


import com.example.Tuition.config.OktaConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private final OktaConfig oktaConfig;
  private final String SIGN_UP_URL = "/students/sign-up";
  private final String LOGIN_URL = "/students/login";
  private final String RESET_PASSWORD = "/students/resetPassword";
  private final String UPDATE_PASSWORD = "/students/updatePassword";

  private final AuthenticationEntryPoint myAuthenticationEntryPoint;

  public WebSecurity(OktaConfig oktaConfig, AuthenticationEntryPoint myAuthenticationEntryPoint) {
    this.oktaConfig = oktaConfig;
    this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
            .antMatchers(HttpMethod.POST, LOGIN_URL).permitAll()
            .antMatchers(HttpMethod.POST,RESET_PASSWORD).permitAll()
            .antMatchers(HttpMethod.POST,UPDATE_PASSWORD).permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/swagger-resources").permitAll()
            .antMatchers("/v2/api-docs").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .anyRequest().authenticated()

            .and().exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
            .and()
            .addFilterAfter(new JWTAuthorizationFilter(authenticationManager(), oktaConfig), BasicAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration());
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.applyPermitDefaultValues();
    corsConfig.addAllowedMethod(HttpMethod.PUT);
    corsConfig.addAllowedMethod(HttpMethod.PATCH);
    corsConfig.addAllowedMethod(HttpMethod.DELETE);

    source.registerCorsConfiguration("/**", corsConfig);

    return source;
  }
}
