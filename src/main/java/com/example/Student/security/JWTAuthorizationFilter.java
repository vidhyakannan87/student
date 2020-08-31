package com.example.Student.security;

import com.example.Student.model.Role;
import com.example.Student.config.OktaConfig;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.Student.service.utility.UniqueIdGenerator.generateBase64UUID;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private static final String HEADER_STRING = "Authorization";
  private static final String GROUPS = "groups";
  private final OktaConfig oktaConfig;

  private String STUDENT_ROLE =   Role.Names.STUDENT;

  public JWTAuthorizationFilter(AuthenticationManager authManager, OktaConfig oktaConfig) {
    super(authManager);
    this.oktaConfig = oktaConfig;
  }

  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(HEADER_STRING);

    if (header == null) {
      chain.doFilter(req, res);
      return;
    }

    String token = header.substring(7);

    Jwt jwt = null;
    try {
      jwt = oktaConfig.jwtVerifier().decode(token);
    } catch (JwtVerificationException e) {
      req.setAttribute("message", e.getCause().getMessage());
    }

    if (jwt == null) {
      chain.doFilter(req, res);
      return;
    }

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    //Commenting until all valid user groups are added
//    ModelMapper map = new ModelMapper();
//    ArrayList<String> groups = null;


//    if (jwt.getClaims().get(GROUPS) != null) {
//      groups = map.map(jwt.getClaims().get(GROUPS), ArrayList.class);
//    } else {
//      chain.doFilter(req, res);
//    }
//
//    if (!groups.contains(STUDENT_ROLE)) {
//      chain.doFilter(req, res);
//    }

//    if (groups.contains(ADMIN_ROLE)) {
//      authorities.add(new SimpleGrantedAuthority(ADMIN_ROLE));
//    }

    Object oktaId = jwt.getClaims().get("uid");
    String uuid = generateBase64UUID(oktaId);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uuid, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

}