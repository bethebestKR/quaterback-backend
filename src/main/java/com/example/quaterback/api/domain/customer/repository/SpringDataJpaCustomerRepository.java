package com.example.quaterback.api.domain.customer.repository;

import com.example.quaterback.api.domain.customer.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAll();
    Optional<CustomerEntity> findByCustomerId(String customerId);
    Page<CustomerEntity> findByCustomerIdContaining(String keyword, Pageable pageable);
    Page<CustomerEntity> findByCustomerNameContaining(String keyword, Pageable pageable);
}
