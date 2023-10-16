package com.iphone.store.security.impl;

import com.iphone.store.entity.Customer;
import com.iphone.store.security.ISecurityService;
import com.iphone.store.security.utils.PasswordHashGenerator;
import com.iphone.store.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SecurityServiceImpl implements ISecurityService {
    private final ICustomerService iCustomerService;
    @Override
    public Boolean isAdmin(String authorization) {
        String decodedLogin = authorization.split(":")[0];
        String decodedPassword = authorization.split(":")[1];

        Optional<Customer> customerOptional = iCustomerService.findByLogin(decodedLogin);
        if (customerOptional.isEmpty()) {
            return false;
        }

        System.out.println(BCrypt.hashpw(decodedPassword, BCrypt.gensalt()));
        if (!BCrypt.checkpw(decodedPassword, customerOptional.get().getPasswordHash())) {
            return false;
        }

        return true;
    }
}
