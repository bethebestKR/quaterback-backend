package com.example.quaterback.api.domain.customer.repository;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import com.example.quaterback.api.domain.customer.entity.CustomerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JpaCustomerRepository implements CustomerRepository {

    private final SpringDataJpaCustomerRepository springDataJpaCustomerRepository;

    @Override
    public Page<CustomerDomain> findAll(Pageable pageable) {
        Page<CustomerEntity> customerEntityList = springDataJpaCustomerRepository.findAll(pageable);
        return customerEntityList.map(CustomerEntity::toDomain);
    }

    @Override
    public CustomerDomain findByCustomerId(String id) {
        CustomerEntity customerEntity = springDataJpaCustomerRepository.findByCustomerId(id)
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));
        return customerEntity.toDomain();
    }

    @Override
    public String updateCustomerInfo(CustomerDomain customerDomain) {
        CustomerEntity entity = springDataJpaCustomerRepository.findByCustomerId(customerDomain.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("entity not found"));
        String result = entity.updateCustomerInfo(customerDomain);
        return result;
    }

    @Override
    public Page<CustomerDomain> findByCustomerIdContating(String keyword, Pageable pageable) {
        Page<CustomerEntity> customerEntityPage = springDataJpaCustomerRepository.findByCustomerIdContaining(keyword, pageable);
        return customerEntityPage.map(CustomerEntity::toDomain);
    }

    @Override
    public Page<CustomerDomain> findByCustomerNameContaining(String keyword, Pageable pageable) {
        Page<CustomerEntity> customerEntityPage = springDataJpaCustomerRepository.findByCustomerNameContaining(keyword, pageable);
        return customerEntityPage.map(CustomerEntity::toDomain);
    }


}
