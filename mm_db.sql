DROP DATABASE IF EXISTS MM_IDS;
CREATE DATABASE IF NOT EXISTS MM_IDS;
USE MM_IDS;

CREATE TABLE MM_PROVIDERS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    cod_prov VARCHAR(3) UNIQUE NOT NULL,
    prov_name VARCHAR(100) NOT NULL,
    date_ini DATE NOT NULL,
    date_end DATE,
    swiact boolean NOT NULL -- If active or inactive
);

SELECT * FROM MM_PROVIDERS WHERE swiact = TRUE AND COALESCE(@p_date, CURRENT_DATE()) BETWEEN date_ini AND COALESCE(date_end, '2099-12-30');

INSERT INTO MM_PROVIDERS (cod_prov, prov_name, date_ini, date_end, swiact) 
VALUES 
('CAX', 'caixa', '2024-01-01', '2024-12-31', TRUE),
('SAN', 'santander', '2024-02-01', NULL, TRUE),
('ING', 'international netherlands group', '2024-03-01', '2024-12-31', FALSE);

CREATE TABLE MM_CUSTOMER(
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(9) UNIQUE,
    `name` VARCHAR(255),
    first_surname VARCHAR(255),
	second_surname VARCHAR(255),
	email longtext,
    birth_date date,
    postal_code varchar(255),
    street_type varchar(255),
    city_customer varchar(255),
    street_number varchar(255),
    telephone varchar(255),
    gender enum('M', 'F'),
    licence_plate VARCHAR(20)
);

CREATE TABLE MM_INTERFACE(
	id INT AUTO_INCREMENT PRIMARY KEY,
	ext_cod VARCHAR(20),
    int_cod VARCHAR(20),
    cod_prov VARCHAR(3),
    cont_json LONGTEXT, -- JSON CONTENT
    creation_date TIMESTAMP,
    last_updated DATE,
    created_by VARCHAR(100), -- CREATE BY WHOM
    updated_by VARCHAR(100), -- UPDATED BY WHOM
	cod_error VARCHAR(20),
    error_message VARCHAR(4000),
    status_process ENUM('N','E','P'), -- N (not processed), E (Error), P (Processed)
    operation ENUM('NEW', 'UPD'), -- IF UPDATED OR NEW PROCESS
    resources ENUM('CUS', 'VEH','PRT'), -- IF COMES FROM CUSTOMER, VEHICLES, OR PARTES
    FOREIGN KEY (cod_prov) REFERENCES MM_PROVIDERS(cod_prov)
);

CREATE TABLE MM_TRANSLATIONS (
	id INT AUTO_INCREMENT PRIMARY KEY,
    cod_prov VARCHAR(3),
    cod_int VARCHAR(100), -- Example = MD stands for Motomami id document
	cod_ext VARCHAR(100), -- Example = CD stands for caixa id document
	date_ini DATE NOT NULL,
    date_end DATE,
	FOREIGN KEY (cod_prov) REFERENCES MM_PROVIDERS(cod_prov)
);

INSERT INTO MM_TRANSLATIONS (cod_prov, cod_int, cod_ext, date_ini, date_end) 
VALUES 
('CAX', 'mm_calle', 'Road', '2024-01-01', '2099-12-31'),
('SAN', 'mm_calle', 'Road', '2024-02-01', '2099-12-31'),
('ING', 'mm_calle', 'Road', '2024-03-01', '2099-12-31'),
('CAX', 'mm_avenida', 'Avenue', '2024-01-01', '2099-12-31'),
('SAN', 'mm_avenida', 'Avenue', '2024-02-01', '2099-12-31'),
('ING', 'mm_avenida', 'Avenue', '2024-03-01', '2099-12-31'),
('CAX', 'mm_goldenrod', 'Goldenrod', '2024-01-01', '2099-12-31'),
('SAN', 'mm_fuscia', 'Fuscia', '2024-02-01', '2099-12-31'),
('ING', 'mm_fuscia', 'Fuscia', '2024-03-01', '2099-12-31'),
('CAX', 'mm_teal', 'Teal', '2024-01-01', '2099-12-31'),
('SAN', 'mm_puce', 'Puce', '2024-02-01', '2099-12-31'),
('ING', 'mm_purple', 'Purple', '2024-03-01', '2099-12-31'),
('CAX', 'mm_aquamarine', 'Aquamarine', '2024-01-01', '2099-12-31'),
('SAN', 'mm_yellow', 'Yellow', '2024-02-01', '2099-12-31'),
('ING', 'mm_blue', 'Blue', '2024-03-01', '2099-12-31'),
('CAX', 'mm_pink', 'Pink', '2024-01-01', '2099-12-31'),
('SAN', 'mm_turquoise', 'Turquoise', '2024-02-01', '2099-12-31'),
('ING', 'mm_red', 'Red', '2024-03-01', '2099-12-31'),
('CAX', 'mm_green', 'Green', '2024-01-01', '2099-12-31'),
('SAN', 'mm_orange', 'Orange', '2024-02-01', '2099-12-31');

CREATE TABLE MM_VEHICLE(
	id INT AUTO_INCREMENT PRIMARY KEY,
    number_plate VARCHAR(20) UNIQUE,
    type_vehicle VARCHAR(20),
    brand VARCHAR(20),
    model VARCHAR(20),
    color VARCHAR(20),
    dni_customer VARCHAR(20),
    FOREIGN KEY (dni_customer) REFERENCES MM_CUSTOMER(dni)
);

CREATE TABLE MM_PARTS(
id varchar(100) PRIMARY KEY,
cod_ext varchar(100),
cod_int varchar(100),
descripcion varchar(100),
date_notification date,
number_plate varchar(100),
id_invoice varchar(100),
dni_vehicle varchar(100),
FOREIGN KEY (dni_vehicle) REFERENCES MM_VEHICLE(dni_customer),
FOREIGN KEY (cod_ext) REFERENCES MM_VEHICLE(number_plate) -- > EL CODIGO EXTERNO ES LA MATRICULA DEL VEHICULO YA QUE ES UNICO NO SE VA A DAR ESTEN DOS MATRICULAS EN UNA ASEGURADORA
);
