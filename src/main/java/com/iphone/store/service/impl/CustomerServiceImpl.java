package com.iphone.store.service.impl;

import com.iphone.store.entity.Customer;
import com.iphone.store.repository.CustomerRespository;
import com.iphone.store.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {
    private final CustomerRespository customerRespository;
    @Override
    public Optional<Customer> findByLogin(String login) {
        return customerRespository.findByLogin(login);
    }
}
