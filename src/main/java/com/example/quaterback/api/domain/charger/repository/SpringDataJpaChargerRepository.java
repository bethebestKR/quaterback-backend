package com.example.quaterback.api.domain.charger.repository;

import com.example.quaterback.api.domain.charger.entity.ChargerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaChargerRepository extends JpaRepository<ChargerEntity, Long> {
    Optional<ChargerEntity> findByStation_StationIdAndEvseId(String stationId, Integer evseId);
    List<ChargerEntity> findByStation_StationId(String stationId);

    @Query("select c from ChargerEntity c where c.station.stationId =:stationId")
    List<ChargerEntity> findAllByStationId(@Param("stationId")String stationId);
}
