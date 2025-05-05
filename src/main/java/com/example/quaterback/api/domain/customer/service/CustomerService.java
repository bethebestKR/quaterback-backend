package com.example.quaterback.api.domain.customer.service;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import com.example.quaterback.api.domain.customer.repository.CustomerRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.feature.managing.dto.*;
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

    public Page<CustomerResponseDto> findCustomers(String searchType, String keyword, Pageable pageable) {
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

        return result.map(CustomerResponseDto::fromDomain);
    }

    public CustomerDetailResponseDto findByCustomerId(String customerId) {
        CustomerDomain customerDomain = customerRepository.findByCustomerId(customerId);
        return CustomerDetailResponseDto.fromCustomerDomain(customerDomain);
    }

    @Transactional
    public CustomerUpdateResponseDto updateCustomerInfo(String customerId, CustomerUpdateRequestDto customerUpdateRequestDto) {
        CustomerDomain customerDomain = CustomerDomain.fromCustomerUpdateRequestDto(customerId, customerUpdateRequestDto);
        String resultCustomerId = customerRepository.updateCustomerInfo(customerDomain);
        return CustomerUpdateResponseDto.fromCustomerId(resultCustomerId);
    }

    public Page<CustomerChargedLogResponseDto> findChargedLogListByCustomerId(
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
        return result.map(CustomerChargedLogResponseDto::fromTxInfoDomain);
    }

}
