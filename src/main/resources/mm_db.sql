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
('ING', 'international netherlands group', '2024-03-01', '2024-12-31', TRUE);

CREATE TABLE MM_CUSTOMER(
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(9) UNIQUE NOT NULL,
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
    number_plate VARCHAR(20) UNIQUE NOT NULL,
    type_vehicle VARCHAR(20),
    brand VARCHAR(20),
    model VARCHAR(20),
    color VARCHAR(20),
    dni_customer VARCHAR(9) NOT NULL
);

CREATE TABLE MM_PARTS(
id int AUTO_INCREMENT PRIMARY KEY,
`description` varchar(100),
date_notified date,
number_plate varchar(100),
id_invoice varchar(100),
dni_customer varchar(9)
);

-- INVOICE 

CREATE TABLE MM_INVOICES(
id int AUTO_INCREMENT PRIMARY KEY,
cod_prov varchar(20),
dni_customer varchar(100),
date_emitted date,
company_name varchar(200),
company_cif varchar(200),
company_address varchar(200),
price double,
iva int,
FOREIGN KEY (dni_customer) REFERENCES MM_CUSTOMER(dni),
FOREIGN KEY (cod_prov) REFERENCES MM_PROVIDERS(cod_prov)
);

INSERT INTO MM_INVOICES (cod_prov, dni_customer, date_emitted, company_name, company_cif, company_address, price, iva) VALUES
('CAX', 'X7035305P', '2024-01-15', 'Caixa Corp', 'CAX123456', 'Caixa Street. 123', 1000.50, 21),
('SAN', 'C9276327V', '2024-07-20', 'Santander Corp', 'SAN987654', 'Santander Avenue. 456', 1500.75, 21),
('ING', 'H2796910B', '2024-03-25', 'ING Group', 'ING789123', 'ING Road. 789', 2000.00, 21),
('CAX', 'S8087974R', '2024-06-18', 'Caixa Corp', 'CAX654321', 'Caixa Street. 456', 1200.60, 21),
('SAN', 'F4120976A', '2024-02-22', 'Santander Corp', 'SAN123789', 'Santander Avenue. 789', 1700.85, 21),
('ING', 'B7069097H', '2024-06-28', 'ING Group', 'ING321654', 'ING Road. 123', 2200.10, 21),
('ING', 'Q0254201E', '2024-12-25', 'ING Group', 'ING726523', 'BLING Sda. 789', 2000.00, 21),
('CAX', 'X9524413G', '2024-12-18', 'Caixa Corp', 'CAX654321', 'Caixa Street. 456', 1200.60, 21),
('SAN', 'M3098883E', '2024-10-22', 'Santander Corp', 'SAN123789', 'Santander Avenue. 789', 1700.85, 21),
('ING', 'I1476603U', '2024-05-28', 'ING Group', 'ING321654', 'ING Road. 123', 2200.10, 21),
('CAX', 'Q0254201E', '2024-12-25', 'ING Group', 'ING726523', 'BLING Sda. 789', 2000.00, 21);