package com.example.quaterback.api.domain.customer.service;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import com.example.quaterback.api.domain.customer.repository.CustomerRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.feature.managing.dto.request.CustomerUpdateRequest;
import com.example.quaterback.api.feature.managing.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TxInfoRepository txInfoRepository;

    public CustomerListResponse findAllCustomers(Pageable pageable) {
        Page<CustomerDomain> result = customerRepository.findAll(pageable);
        return CustomerListResponse.from(result.map(CustomerResponse::fromDomain));
    }

    public CustomerListResponse searchCustomersByCustomerId(String customerId, Pageable pageable) {
        Page<CustomerDomain> result = customerRepository.findByCustomerIdContating(customerId, pageable);
        return CustomerListResponse.from(result.map(CustomerResponse::fromDomain));
    }

    public CustomerListResponse searchCustomersByCustomerName(String customerName, Pageable pageable) {
        Page<CustomerDomain> result = customerRepository.findByCustomerNameContaining(customerName, pageable);
        return CustomerListResponse.from(result.map(CustomerResponse::fromDomain));
    }

    public CustomerDetailResponse findByCustomerId(String customerId) {
        CustomerDomain customerDomain = customerRepository.findByCustomerId(customerId);
        return CustomerDetailResponse.fromCustomerDomain(customerDomain);
    }

    @Transactional
    public CustomerUpdateResponse updateCustomerInfo(String customerId, CustomerUpdateRequest customerUpdateRequest) {
        CustomerDomain customerDomain = CustomerDomain.fromCustomerUpdateRequestDto(customerId, customerUpdateRequest);
        String resultCustomerId = customerRepository.updateCustomerInfo(customerDomain);
        return CustomerUpdateResponse.fromCustomerId(resultCustomerId);
    }

    public CustomerChargedLogListResponse findAllChargedLogListByCustomerId(String customerId, Pageable pageable) {
        CustomerDomain customerDomain = customerRepository.findByCustomerId(customerId);
        String idToken = customerDomain.getIdToken();

        Page<TransactionInfoDomain> result = txInfoRepository.findByIdTokenOrderByStartedTimeDesc(idToken, pageable);

        return CustomerChargedLogListResponse
                .from(result.map(CustomerChargedLogResponse::fromTxInfoDomain));
    }

    public CustomerChargedLogListResponse findChargedLogListByCustomerIdAndPeriod(
            String customerId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
            ) {
        CustomerDomain customerDomain = customerRepository.findByCustomerId(customerId);
        String idToken = customerDomain.getIdToken();

        Page<TransactionInfoDomain> result = txInfoRepository.findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(
                    idToken, startDate, endDate, pageable);

        return CustomerChargedLogListResponse
                .from(result.map(CustomerChargedLogResponse::fromTxInfoDomain));
    }

}
