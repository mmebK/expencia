package com.expencia.accounts.openfeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
public class Interceptor implements RequestInterceptor {


    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String jwt = null;
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            jwt = requestAttributes.getRequest().getHeader("Authorization");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String token = jwt;

        requestTemplate.header(AUTHORIZATION_HEADER, token);

    }
}
