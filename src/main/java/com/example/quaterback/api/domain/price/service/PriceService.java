package com.example.quaterback.api.domain.price.service;

import com.example.quaterback.api.domain.price.entity.PricePerMwh;
import com.example.quaterback.api.domain.price.repository.PriceRepository;
import com.example.quaterback.api.feature.dashboard.dto.response.CsPriceHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PriceService {
    private final PriceRepository priceRepository;

    public Double getCurrentPrice() {
        PricePerMwh entity = priceRepository.findTopByOrderByUpdatedDateTimeDesc();
        return entity.getPricePerMwh();
    }

    public void updatePrice(Double price) {
        PricePerMwh entity = PricePerMwh.from(price);
        priceRepository.save(entity);
    }

    public List<CsPriceHistory> getCsPriceHistory() {
        List<PricePerMwh> entities = priceRepository.findAllByOrderByUpdatedDateTimeDesc();
        return entities.stream().map(CsPriceHistory::from).toList();
    }
}
