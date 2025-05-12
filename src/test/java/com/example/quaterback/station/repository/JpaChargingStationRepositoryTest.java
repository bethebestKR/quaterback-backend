//package com.example.quaterback.station.repository;
//
//import com.example.quaterback.api.domain.station.constant.StationStatus;
//import com.example.quaterback.api.domain.station.domain.ChargingStationDomain;
//import com.example.quaterback.api.domain.station.entity.ChargingStationEntity;
//import com.example.quaterback.api.domain.station.repository.ChargingStationRepository;
//import com.example.quaterback.api.domain.station.repository.JpaChargingStationRepository;
//import com.example.quaterback.api.domain.station.repository.SpringDataJpaChargingStationRepository;
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class JpaChargingStationRepositoryTest {
//
//    @Autowired
//    private SpringDataJpaChargingStationRepository springDataRepository;
//
//    private ChargingStationRepository jpaChargingStationRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @BeforeEach
//    void setUp() {
//        // 💡 DB 초기화: 중복된 stationId 문제 방지
//        springDataRepository.deleteAll();
//
//        jpaChargingStationRepository = new JpaChargingStationRepository(springDataRepository);
//    }
//
//    @Test
//    @Transactional
//    void updateEss_메서드가_essValue_필드를_정상적으로_갱신한다() {
//        // given
//        ChargingStationEntity saved = springDataRepository.save(
//                ChargingStationEntity.builder()
//                        .stationId("station-001")
//                        .model("M1")
//                        .vendorId("quarterback")
//                        .latitude(37.55)
//                        .longitude(126.55)
//                        .address("서울")
//                        .updateStatusTimeStamp(LocalDateTime.now())
//                        .stationStatus(StationStatus.ACTIVE)
//                        .essValue(50)
//                        .build()
//        );
//
//        // when
//        ChargingStationDomain updateDomain = ChargingStationDomain.builder()
//                .stationId("station-001")
//                .essValue(75)
//                .build();
//
//        jpaChargingStationRepository.updateEss(updateDomain);
//
//        // 영속성 컨텍스트 초기화 후 재조회
//        em.flush();
//        em.clear();
//
//        ChargingStationEntity updated = springDataRepository.findByStationId("station-001").get();
//
//        // then
//        assertThat(updated.getEssValue()).isEqualTo(75);
//    }
//}
