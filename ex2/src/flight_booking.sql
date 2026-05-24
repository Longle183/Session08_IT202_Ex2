CREATE DATABASE flight_booking;

USE flight_booking;

CREATE TABLE flight (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        flight_number VARCHAR(255),
                        departure_time DATETIME,
                        available_seats INT
);

CREATE TABLE ticket (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        passenger_name VARCHAR(255),
                        flight_id BIGINT,
                        status VARCHAR(50)
);

CREATE TABLE error_log (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           timestamp DATETIME,
                           method_name VARCHAR(255),
                           exception_message TEXT
);

INSERT INTO flight(flight_number, departure_time, available_seats)
VALUES
    ('VN123', '2026-06-01 20:00:00', 10),
    ('VN999', '2026-06-02 10:00:00', 5);