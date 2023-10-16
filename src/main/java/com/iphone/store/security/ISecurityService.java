package com.iphone.store.security;

public interface ISecurityService {
    Boolean isAdmin(String encodedCredentials);
}
