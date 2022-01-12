package com.expencia.accounts.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class JWTAuthorisationFilter extends OncePerRequestFilter {
    String token;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("we here 1");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response);

        } else {
            String jwt = request.getHeader(SecurityParams.JWT_HEADER_NAME);
            setToken(jwt);
            System.out.println("this.token is: " + this.token);
            storeToken();
            System.out.println("we get jwt" + jwt);
            if (jwt == null || !jwt.startsWith(SecurityParams.HEADER_PREFIX)) {
                System.out.println("not valid jwt");
                filterChain.doFilter(request, response);
                return;
            }
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
            DecodedJWT decodedJWT = verifier.verify(jwt.substring(SecurityParams.HEADER_PREFIX.length()));
            String userName = decodedJWT.getSubject();
            System.out.println("the username" + userName);
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
            System.out.println(roles);
            List<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken(userName, null, authorities);
            System.out.println(user);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(request, response);

        }
    }

    void setToken(String token) {
        this.token = token;
    }

    public String storeToken() {
        System.out.println("the token from storeToken " + this.token);
        return token;
    }
}
