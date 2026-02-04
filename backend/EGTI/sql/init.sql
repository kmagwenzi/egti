-- SQL initialization script for EGTI applications database
-- Creates the database and table structure matching the JPA entity.

-- Create database if not exists
CREATE DATABASE IF NOT EXISTS egti CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE egti;

-- Table: applications
CREATE TABLE IF NOT EXISTS applications (
    id BIGINT NOT NULL AUTO_INCREMENT,
    profile_image VARCHAR(255),
    name VARCHAR(255),
    maiden_name VARCHAR(255),
    gender VARCHAR(255),
    date_of_birth DATE,
    place_of_birth VARCHAR(255),
    nationality VARCHAR(255),
    country_of_residence VARCHAR(255),
    identity_number VARCHAR(255),
    passport_number VARCHAR(255),
    expiry_date DATE,
    marital_status VARCHAR(255),
    contact_number_1 VARCHAR(255),
    contact_number_2 VARCHAR(255),
    email VARCHAR(255),
    program VARCHAR(255),
    exemptions VARCHAR(255),
    sponsorship VARCHAR(255),
    sponsors_fullname VARCHAR(255),
    startingDate DATE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional: Insert sample data (commented out)
/*
INSERT INTO applications (name, gender, email, program) VALUES
('John Doe', 'Male', 'john@example.com', 'Degree'),
('Jane Smith', 'Female', 'jane@example.com', 'Masters');
*/
