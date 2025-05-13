package com.example.quaterback.websocket.authorize.service;

import com.example.quaterback.api.domain.customer.repository.CustomerRepository;
import com.example.quaterback.websocket.authorize.converter.AuthorizeConverter;
import com.example.quaterback.websocket.authorize.domain.AuthorizeDomain;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final CustomerRepository customerRepository;
    private final AuthorizeConverter converter;

    public boolean authorize(JsonNode jsonNode) {
        AuthorizeDomain authorizeDomain = converter.convertToAuthorizeDomain(jsonNode);
        return customerRepository.existsByIdToken(authorizeDomain.extractIdToken());
    }

}
