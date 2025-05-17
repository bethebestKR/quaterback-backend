-- 🔥 외래키 체크 해제
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 기존 데이터 삭제
DELETE FROM tx_log;
DELETE FROM tx_info;
DELETE FROM charger_info WHERE station_id = 'station-001';
DELETE FROM charging_station WHERE station_id = 'station-001';
DELETE FROM customer;
DELETE FROM cs_price;
DELETE FROM kepco_price;

-- 🔥 외래키 체크 복구
SET FOREIGN_KEY_CHECKS = 1;

-- 2. 새 데이터 삽입
INSERT INTO charging_station (station_id, model, vendor_id, latitude, longitude, address, update_status_time_stamp, station_status, station_name, ess_value)
VALUES ('station-001', 'R1', 'quarterback', 37.5665, 126.9780, '서울특별시 중구 세종대로 110', '2025-04-17T11:20:00', 'inactive', 'sejong', 100.0);

INSERT INTO charger_info (evse_id, charger_status, update_status_time_stamp, station_id)
VALUES (1, 'Available', '2025-04-20T12:30:00', 'station-001'),
       (2, 'Available', '2025-04-20T12:30:00', 'station-001'),
       (3, 'Available', '2025-04-20T12:30:00', 'station-001'),
       (4, 'Fault', '2025-04-20T12:30:00', 'station-001');

INSERT INTO customer (customer_id, customer_name, id_token, email, phone, vehicle_no, registration_date)
VALUES
    ('user1', '이름1', 'token001', 'e1@gmail.com', '01012345678', '12-4234', '2024-05-20T16:30:00'),
    ('user2', '이름2', 'user-002', 'e2@gmail.com', '01034550101', '12-2353', '2024-05-21T16:30:00'),
    ('user3', '이름3', 'user-003', 'e3@gmail.com', '01049494949', '12-0999', '2024-05-22T16:30:00'),
    ('user4', '이름4', 'user-004', 'e4@gmail.com', '01012345673', '12-2222', '2024-05-23T16:30:00'),
    ('user5', '이름5', 'user-005', 'e5@gmail.com', '01012334455', '12-6666', '2024-05-24T16:30:00'),
    ('user6', '이름6', 'user-006', 'e6@gmail.com', '01010044499', '12-8989', '2024-05-25T16:30:00');

INSERT INTO cs_price (price_per_mwh, updated_date_time) VALUES (100, '2025-05-16T12:00:00');

INSERT INTO kepco_price (season, time_slot, price_per_kwh) VALUES
    ('SUMMER', 'OFF_PEAK', 79.2),
    ('SUMMER', 'MID_PEAK', 137.4),
    ('SUMMER', 'ON_PEAK', 190.4),
    ('SPRING_FALL', 'OFF_PEAK', 80.2),
    ('SPRING_FALL', 'MID_PEAK', 91.0),
    ('SPRING_FALL', 'ON_PEAK', 94.9),
    ('WINTER', 'OFF_PEAK', 96.6),
    ('WINTER', 'MID_PEAK', 127.7),
    ('WINTER', 'ON_PEAK', 165.5);

-- 통계 api 테스트용 데이터
INSERT INTO tx_info (transaction_id, started_time, ended_time,vehicle_no, id_token, station_id,total_meter_value, total_price, error_code)
VALUES('TX123456','2025-05-15T10:00:00','2025-05-15T10:00:10','12가3456','token001','station-001',15.0,1500,'00'),
      ('TX12123','2025-05-16T10:20:00','2025-05-16T10:20:20','12가3456','token001','station-001',15.0,1000,'00'),
      ('TX789012','2025-04-02T14:10:00',          '2025-04-02T14:50:00','34나7890','token001','station-001',22.7,6800,'00');

INSERT INTO cs_price (price_per_mwh, updated_date_time)
VALUES
    (70, '2025-05-01T12:00:00'),
    (75, '2025-05-02T12:00:00'),
    (80, '2025-05-03T12:00:00'),
    (85, '2025-05-04T12:00:00'),
    (90, '2025-05-10T12:00:00'),
    (95, '2025-05-11T12:00:00'),
    (97, '2025-05-12T12:00:00'),
    (98, '2025-05-13T12:00:00'),
    (100, '2025-05-14T12:00:00');