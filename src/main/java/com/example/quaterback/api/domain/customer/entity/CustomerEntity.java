package com.example.quaterback.api.domain.customer.entity;

import com.example.quaterback.api.domain.customer.domain.CustomerDomain;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerId;
    private String customerName;
    private String idToken;
    private String email;
    private String phone;
    private String vehicleNo;
    private String registrationDate;

    public CustomerDomain toDomain() {
        return CustomerDomain.builder()
                .customerId(customerId)
                .customerName(customerName)
                .idToken(idToken)
                .email(email)
                .phone(phone)
                .vehicleNo(vehicleNo)
                .registrationDate(registrationDate)
                .build();
    }

    public static CustomerEntity fromDomain(CustomerDomain customerDomain) {
        return CustomerEntity.builder()
                .customerId(customerDomain.getCustomerId())
                .customerName(customerDomain.getCustomerName())
                .idToken(customerDomain.getIdToken())
                .vehicleNo(customerDomain.getVehicleNo())
                .registrationDate(customerDomain.getRegistrationDate())
                .build();
    }

    public String updateCustomerInfo(CustomerDomain customerDomain) {
        this.customerName = customerDomain.getCustomerName();
        this.email = customerDomain.getEmail();
        this.phone = customerDomain.getPhone();
        this.vehicleNo = customerDomain.getVehicleNo();
        return customerId;
    }
}
