package com.iphone.store.security.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashGenerator {
    public static String decodePassword(String password) {
        String salt = BCrypt.gensalt();

        // Hash the password using the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }
}
