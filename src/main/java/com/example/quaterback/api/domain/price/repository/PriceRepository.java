package com.example.quaterback.api.domain.price.repository;

import com.example.quaterback.api.domain.price.entity.PricePerMwh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<PricePerMwh, Long> {
    PricePerMwh findTopByOrderByUpdatedDateTimeDesc();
    List<PricePerMwh> findAllByOrderByUpdatedDateTimeDesc();
}
