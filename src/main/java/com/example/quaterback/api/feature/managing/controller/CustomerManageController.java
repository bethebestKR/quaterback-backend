package com.example.quaterback.api.feature.managing.controller;

import com.example.quaterback.api.domain.customer.service.CustomerService;
import com.example.quaterback.api.feature.managing.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/managing/customer")
public class CustomerManageController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public Page<CustomerResponseDto> customerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(required = false) String searchType,
            @RequestParam(required = false) String keyword
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), "registrationDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findCustomers(searchType, keyword, pageable);
    }

    @GetMapping("/customers/{customerId}")
    public CustomerDetailResponseDto customerDetail(@PathVariable String customerId) {
        return customerService.findByCustomerId(customerId);
    }

    @PostMapping("/customers/{customerId}")
    public CustomerUpdateResponseDto updateCustomerInfo(
            @PathVariable String customerId,
            @RequestBody CustomerUpdateRequestDto dto) {
        return customerService.updateCustomerInfo(customerId, dto);
    }

    @GetMapping("/chargedLog/{customerId}/")
    public Page<CustomerChargedLogResponseDto> chargedLog(
            @PathVariable String customerId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startedTime"));
        return customerService.findChargedLogListByCustomerId(customerId, startDate, endDate, pageable);
    }

}
