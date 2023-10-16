package com.iphone.store.repository;

import com.iphone.store.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLogin(String login);
}
