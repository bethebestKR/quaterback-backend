-- 🔥 외래키 체크 해제
SET FOREIGN_KEY_CHECKS = 0;

-- 1. 기존 데이터 삭제
DELETE FROM tx_log;
DELETE FROM tx_info;
DELETE FROM charger_info WHERE station_id = 'station-001';
DELETE FROM charging_station WHERE station_id = 'station-001';
DELETE FROM customer;

-- 🔥 외래키 체크 복구
SET FOREIGN_KEY_CHECKS = 1;

-- 2. 새 데이터 삽입
INSERT INTO charging_station (station_id, model, vendor_id, latitude, longitude, address, update_status_time_stamp, station_status, station_name)
VALUES ('station-001', 'R1', 'quarterback', 37.5665, 126.9780, '서울특별시 중구 세종대로 110', '2025-04-17T11:20:00', 'inactive', 'sejong');

INSERT INTO charger_info (evse_id, charger_status, update_status_time_stamp, station_id)
VALUES (1, 'Available', '2025-04-20T12:30:00', 'station-001'),
       (2, 'Available', '2025-04-20T12:30:00', 'station-001'),
       (3, 'Available', '2025-04-20T12:30:00', 'station-001');

INSERT INTO customer (customer_id, customer_name, id_token, email, phone, vehicle_no, registration_date)
VALUES
    ('user1', '이름1', 'token001', 'e1@gmail.com', '01012345678', '12-4234', '2024-05-20T16:30:00'),
    ('user2', '이름2', 'user-002', 'e2@gmail.com', '01034550101', '12-2353', '2024-05-21T16:30:00'),
    ('user3', '이름3', 'user-003', 'e3@gmail.com', '01049494949', '12-0999', '2024-05-22T16:30:00'),
    ('user4', '이름4', 'user-004', 'e4@gmail.com', '01012345673', '12-2222', '2024-05-23T16:30:00'),
    ('user5', '이름5', 'user-005', 'e5@gmail.com', '01012334455', '12-6666', '2024-05-24T16:30:00'),
    ('user6', '이름6', 'user-006', 'e6@gmail.com', '01010044499', '12-8989', '2024-05-25T16:30:00');
