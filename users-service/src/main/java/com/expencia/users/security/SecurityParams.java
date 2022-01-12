package com.expencia.users.security;

public interface SecurityParams {
    String JWT_HEADER_NAME = "Authorization";
    String SECRET = "secret@don'tTell.com";
    long EXPIRATION = 10 * 24 * 3600 * 1000;
    String HEADER_PREFIX = "Bearer ";

}
