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

    public CustomerListResponse findCustomers(String searchType, String keyword, Pageable pageable) {
        Page<CustomerDomain> result;

        if (searchType != null && searchType.equals("customerId") && keyword != null && !keyword.isBlank()) {
            result = customerRepository.findByCustomerIdContating(keyword, pageable);
        }
        else if (searchType != null && searchType.equals("customerName") && keyword != null && !keyword.isBlank()) {
            result = customerRepository.findByCustomerNameContaining(keyword, pageable);
        }
        else {
            result = customerRepository.findAll(pageable);
        }

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

    public CustomerChargedLogListResponse findChargedLogListByCustomerId(
            String customerId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
            ) {
        CustomerDomain customerDomain = customerRepository.findByCustomerId(customerId);
        String idToken = customerDomain.getIdToken();

        Page<TransactionInfoDomain> result;
        if (startDate != null && endDate != null) {
            result = txInfoRepository.findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(
                    idToken, startDate, endDate, pageable
            );
        }
        else {
            result = txInfoRepository.findByIdTokenOrderByStartedTimeDesc(idToken, pageable);
        }
        return CustomerChargedLogListResponse
                .from(result.map(CustomerChargedLogResponse::fromTxInfoDomain));
    }

}
