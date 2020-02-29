package com.dating.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author elvis
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encode((String)rawPassword));
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encode((String)rawPassword);
    }
}
