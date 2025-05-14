package com.example.quaterback.api.feature.managing.controller;

import com.example.quaterback.api.domain.customer.service.CustomerService;
import com.example.quaterback.api.feature.managing.dto.request.CustomerUpdateRequest;
import com.example.quaterback.api.feature.managing.dto.response.CustomerChargedLogListResponse;
import com.example.quaterback.api.feature.managing.dto.response.CustomerDetailResponse;
import com.example.quaterback.api.feature.managing.dto.response.CustomerListResponse;
import com.example.quaterback.api.feature.managing.dto.response.CustomerUpdateResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/managing/customer")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerManageController {

    private final CustomerService customerService;

    @GetMapping("/customers")
    public CustomerListResponse getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), "registrationDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return customerService.findAllCustomers(pageable);
    }

    @GetMapping("/customers/search")
    public CustomerListResponse searchCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam @NotBlank String searchType,
            @RequestParam @NotBlank String keyword
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), "registrationDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        if (searchType.equals("customerId")) {
            return customerService.searchCustomersByCustomerId(keyword, pageable);
        }

        return customerService.searchCustomersByCustomerName(keyword, pageable);

    }

    @GetMapping("/{customerId}")
    public CustomerDetailResponse customerDetail(@PathVariable String customerId) {
        return customerService.findByCustomerId(customerId);
    }

    @PostMapping("/{customerId}")
    public CustomerUpdateResponse updateCustomerInfo(
            @PathVariable String customerId,
            @RequestBody @Valid CustomerUpdateRequest dto) {
        return customerService.updateCustomerInfo(customerId, dto);
    }

    @GetMapping("chargedLog/{customerId}")
    public CustomerChargedLogListResponse getAllChargedLog(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startedTime"));
        return customerService.findAllChargedLogListByCustomerId(customerId, pageable);
    }

    @GetMapping("/chargedLog/{customerId}/search")
    public CustomerChargedLogListResponse getChargedLogByPeriod(
            @PathVariable String customerId,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startedTime"));
        return customerService.findChargedLogListByCustomerIdAndPeriod(customerId, startDate, endDate, pageable);
    }

}
