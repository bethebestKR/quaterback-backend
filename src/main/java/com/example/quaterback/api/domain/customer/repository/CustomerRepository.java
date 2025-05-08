package com.example.quaterback.api.domain.customer.repository;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomerRepository {
    Page<CustomerDomain>  findAll(Pageable pageable);
    CustomerDomain findByCustomerId(String customerId);
    String updateCustomerInfo(CustomerDomain customerDomain);
    Page<CustomerDomain> findByCustomerIdContating(String keyword, Pageable pageable);
    Page<CustomerDomain> findByCustomerNameContaining(String keyword, Pageable pageable);
}
