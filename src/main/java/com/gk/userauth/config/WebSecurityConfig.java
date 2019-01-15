package com.gk.userauth.config;

import com.gk.userauth.security.AuthenticationFailureHandler;
import com.gk.userauth.security.AuthenticationSuccessHandler;
import com.gk.userauth.security.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenAuthenticationFilter jwtAuthenticationTokenFilter() throws Exception {
    return new TokenAuthenticationFilter();
  }

  @Autowired
  AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired
  AuthenticationFailureHandler authenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception{

    http
            .addFilterBefore(jwtAuthenticationTokenFilter(), BasicAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .antMatchers("/h2/**").permitAll()
            .antMatchers(HttpMethod.GET, "/product/**").permitAll()
            .antMatchers(HttpMethod.GET, "/group/**").permitAll()
            .antMatchers("/cart/**").permitAll()
            .antMatchers("/v2/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()

            .anyRequest().authenticated()
//                .anyRequest().hasRole("admin") << Works with ROLE entities while we have SimpleGrantedAuthority...
            .anyRequest().hasAuthority("admin")

//               .httpBasic().disable();
            .and().formLogin().successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)

            // From https://github.com/bfwg/springboot-jwt-starter
            .and().csrf().disable();
  }
}
