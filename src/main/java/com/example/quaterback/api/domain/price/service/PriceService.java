package com.example.quaterback.api.domain.price.service;

import com.example.quaterback.api.domain.price.entity.PricePerMwh;
import com.example.quaterback.api.domain.price.repository.PriceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public Double getCurrentPrice() {
        PricePerMwh entity = priceRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Price not found"));
        return entity.getPricePerMwh();
    }

    @Transactional
    public void updatePrice(Double price) {
        PricePerMwh entity = priceRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Price not found"));
        entity.setPricePerMwh(price);
    }
}
