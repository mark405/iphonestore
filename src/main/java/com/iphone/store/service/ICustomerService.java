package com.iphone.store.service;

import com.iphone.store.entity.Customer;

import java.util.Optional;

public interface ICustomerService {
    Optional<Customer> findByLogin(String login);
}
