package com.expencia.users.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //private UserDetailsService userDetailsService;
    private final UserDetailsServiceImpl userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * this is session authentication
         */
        //http.formLogin();

        /**
         * this is JWT authentication
         */
        http.csrf().disable();
        //no session allowed
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // here we excluding anyone who hasn't the role 'ADMIN' To access /appUsers and /appRoles
        http.authorizeRequests().antMatchers("/login/**", "/register/**", "/addRole/**", "/test/**").permitAll();
        // http.authorizeRequests().antMatchers(HttpMethod.GET,"/categories/**","/products/**").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/currentUser/**").permitAll();

        // http.authorizeRequests().antMatchers("/appUsers/**", "/appRoles/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));


        http.addFilterBefore(new JWTAuThorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
