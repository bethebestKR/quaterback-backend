package com.example.quaterback.api.domain.price.repository;

import com.example.quaterback.api.domain.price.entity.PricePerMwh;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<PricePerMwh, Long> {
}
