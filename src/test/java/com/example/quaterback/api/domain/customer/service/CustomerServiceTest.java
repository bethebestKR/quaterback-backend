package com.example.quaterback.api.domain.customer.service;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import com.example.quaterback.api.domain.customer.repository.CustomerRepository;
import com.example.quaterback.api.domain.txinfo.domain.TransactionInfoDomain;
import com.example.quaterback.api.domain.txinfo.repository.TxInfoRepository;
import com.example.quaterback.api.feature.managing.dto.request.CustomerUpdateRequest;
import com.example.quaterback.api.feature.managing.dto.response.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TxInfoRepository txInfoRepository;

    @Test
    void findCustomers_고객ID로_검색() {
        // given
        String keyword = "user123";
        Pageable pageable = PageRequest.of(0, 10);
        CustomerDomain customer = mock(CustomerDomain.class);
        Page<CustomerDomain> page = new PageImpl<>(List.of(customer));

        when(customerRepository.findByCustomerIdContaining(keyword, pageable)).thenReturn(page);

        // when
        CustomerListResponse result = customerService.searchCustomersByCustomerId(keyword, pageable);

        // then
        assertThat(result).hasFieldOrProperty("customerList");
        verify(customerRepository).findByCustomerIdContaining(keyword, pageable);
    }

    @Test
    void findCustomers_고객이름으로_검색() {
        //given
        String keyword = "홍길동";
        Pageable pageable = PageRequest.of(0, 10);
        CustomerDomain customer = mock(CustomerDomain.class);
        Page<CustomerDomain> page = new PageImpl<>(List.of(customer));

        when(customerRepository.findByCustomerNameContaining(keyword, pageable)).thenReturn(page);

        //when
        CustomerListResponse result = customerService.searchCustomersByCustomerName(keyword, pageable);

        //then
        assertThat(result).hasFieldOrProperty("customerList");
        verify(customerRepository).findByCustomerNameContaining(keyword, pageable);
    }

    @Test
    void findCustomers_고객전체조회() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        CustomerDomain customer = mock(CustomerDomain.class);
        Page<CustomerDomain> page = new PageImpl<>(List.of(customer));

        when(customerRepository.findAll(pageable)).thenReturn(page);

        //when
        CustomerListResponse result = customerService.findAllCustomers(pageable);

        //then
        assertThat(result).hasFieldOrProperty("customerList");
        verify(customerRepository).findAll(pageable);
    }

    @Test
    void findByCustomerId_고객상세조회() {
        //given
        String customerId = "user123";
        CustomerDomain customer = mock(CustomerDomain.class);
        when(customerRepository.findByCustomerId(customerId)).thenReturn(customer);

        //when
        CustomerDetailResponse result = customerService.findByCustomerId(customerId);

        //then
        assertThat(result).isNotNull();
        verify(customerRepository).findByCustomerId(customerId);
    }

    @Test
    void updateCustomerInfo_고객정보수정() {
        //given
        String customerId = "user123";
        CustomerUpdateRequest requestDto = CustomerUpdateRequest.builder()
                .customerName("name123")
                .email("email123")
                .phone("phone123")
                .vehicleNo("vehicle123")
                .build();
        when(customerRepository.updateCustomerInfo(any(CustomerDomain.class))).thenReturn(customerId);

        //when
        CustomerUpdateResponse result = customerService.updateCustomerInfo(customerId, requestDto);

        //then
        assertThat(result.customerId()).isEqualTo(customerId);
        verify(customerRepository).updateCustomerInfo(any());
    }

    @Test
    void findChargedLogListByCustomerId_충전내역_기간없이조회() {
        //given
        String customerId = "user123";
        String idToken = "token-abc";
        Pageable pageable = PageRequest.of(0, 10);

        CustomerDomain customerDomain = mock(CustomerDomain.class);
        when(customerDomain.getIdToken()).thenReturn(idToken);

        when(customerRepository.findByCustomerId(customerId)).thenReturn(customerDomain);

        TransactionInfoDomain tx = TransactionInfoDomain.builder()
                .startedTime(LocalDateTime.now())
                .endedTime(LocalDateTime.now())
                .vehicleNo("vehicle123")
                .transactionId("tx001")
                .totalMeterValue(2000.0)
                .totalPrice(2000.0)
                .build();
        Page<TransactionInfoDomain> page = new PageImpl<>(List.of(tx));
        when(txInfoRepository.findByIdTokenOrderByStartedTimeDesc(idToken, pageable)).thenReturn(page);

        //when
        CustomerChargedLogListResponse result =
                customerService.findAllChargedLogListByCustomerId(customerId, pageable);

        //then
        assertThat(result).hasFieldOrProperty("customerChargedLogList");
        verify(txInfoRepository).findByIdTokenOrderByStartedTimeDesc(idToken, pageable);
    }

    @Test
    void findChargedLogListByCustomerId_충전내역_기간조회() {
        String customerId = "user123";
        String idToken = "token-abc";
        Pageable pageable = PageRequest.of(0, 10);
        LocalDate start = LocalDate.of(2024, 6, 30);
        LocalDate end = LocalDate.of(2025, 6, 30);

        CustomerDomain customerDomain = mock(CustomerDomain.class);
        when(customerDomain.getIdToken()).thenReturn(idToken);
        when(customerRepository.findByCustomerId(customerId)).thenReturn(customerDomain);

        TransactionInfoDomain tx = TransactionInfoDomain.builder()
                .startedTime(LocalDateTime.now())
                .endedTime(LocalDateTime.now())
                .vehicleNo("vehicle123")
                .transactionId("tx001")
                .totalMeterValue(2000.0)
                .totalPrice(2000.0)
                .build();
        Page<TransactionInfoDomain> page = new PageImpl<>(List.of(tx));
        when(txInfoRepository.findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(idToken, start, end, pageable)).thenReturn(page);

        CustomerChargedLogListResponse result =
                customerService.findChargedLogListByCustomerIdAndPeriod(customerId, start, end, pageable);

        assertThat(result).hasFieldOrProperty("customerChargedLogList");
        verify(txInfoRepository).findByIdTokenAndStartedTimeBetweenOrderByStartedTimeDesc(idToken, start, end, pageable);
    }

}