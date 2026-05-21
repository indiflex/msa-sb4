package com.msa.user;

public class JwtException extends RuntimeException {
    public JwtException(String msg) {
        super("JwtErr:" + msg);
    }
}
