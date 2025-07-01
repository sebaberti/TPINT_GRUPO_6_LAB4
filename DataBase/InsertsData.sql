USE `banco`;

INSERT INTO `Tipos_Movimientos` (`descripcion`)
VALUES
('Alta de cuenta'),
('Alta de prestamo'),
('Pago de prestamo'),
('Transferencia Entrada'),
('Transferencia Salida');

INSERT INTO `Tipos_Cuentas` (`descripcion`)
 VALUES
('Caja de ahorro'),
('Cuenta corriente');


INSERT INTO
    Roles (nombre_rol)
VALUES
    ('Administrador'),
    ('Cliente');
    
INSERT INTO `Paises` (`nombre`) VALUES
('Argentina'),
('Bolivia'),
('Brasil'),
('Chile'),
('Colombia'),
('Ecuador'),
('Guyana'),
('Paraguay'),
('Perú'),
('Uruguay'),
('Venezuela'),
('México');

INSERT INTO `Provincias` (`nombre`, `id_pais`) VALUES
-- inserto solo de Arg
('Buenos Aires', 1),
('Catamarca', 1),
('Chaco', 1),
('Chubut', 1),
('Córdoba', 1),
('Corrientes', 1),
('Entre Ríos', 1),
('Formosa', 1),
('Jujuy', 1),
('La Pampa', 1),
('La Rioja', 1),
('Mendoza', 1),
('Misiones', 1),
('Neuquén', 1),
('Río Negro', 1),
('Salta', 1),
('San Juan', 1),
('San Luis', 1),
('Santa Cruz', 1),
('Santa Fe', 1),
('Santiago del Estero', 1),
('Tierra del Fuego', 1),
('Tucumán', 1);

-- Uso Buenos Aires Id 1 y Santa Fe Id 20
INSERT INTO `Localidades` (`nombre`, `id_provincia`) VALUES
('La Plata', 1), ('Mar del Plata', 1),
('San Fernando del Valle de Catamarca', 2), ('Andalgalá', 2),
('Resistencia', 3), ('Presidencia Roque Sáenz Peña', 3),
('Rawson', 4), ('Comodoro Rivadavia', 4),
('Córdoba', 5), ('Villa Carlos Paz', 5),
('Corrientes', 6), ('Goya', 6),
('Paraná', 7), ('Concordia', 7),
('Formosa', 8), ('Clorinda', 8),
('San Salvador de Jujuy', 9), ('Palpalá', 9),
('Santa Rosa', 10), ('General Pico', 10),
('La Rioja', 11), ('Chilecito', 11),
('Mendoza', 12), ('San Rafael', 12),
('Posadas', 13), ('Oberá', 13),
('Neuquén', 14), ('San Martín de los Andes', 14),
('Viedma', 15), ('Bariloche', 15),
('Salta', 16), ('San Ramón de la Nueva Orán', 16),
('San Juan', 17), ('Rawson (SJ)', 17),
('San Luis', 18), ('Villa Mercedes', 18),
('Río Gallegos', 19), ('Caleta Olivia', 19),
('Santa Fe', 20), ('Rosario', 20),
('Santiago del Estero', 21), ('La Banda', 21),
('Ushuaia', 22), ('Río Grande', 22),
('San Miguel de Tucumán', 23), ('Yerba Buena', 23);

INSERT INTO `Usuarios` (`nombre_usuario`, `contrasenia`, `id_rol`, `estado`) 
VALUES
('admin', 'admin', 1, TRUE),  -- admin
('user1', 'user1', 2, TRUE), -- clientes
('user2', 'user2', 2, TRUE),
('user3', 'user3', 2, TRUE),
('user4', 'user4', 2, FALSE),
('user5', 'user5', 2, TRUE),
('user6', 'user6', 2, TRUE),
('user7', 'user7', 2, FALSE),
('user8', 'user8', 2, FALSE),
('user9', 'user9', 2, TRUE),
('user10', 'user10', 2, TRUE),
('user11', 'user11', 2, FALSE);


INSERT INTO `Domicilios` (`direccion`, `id_localidad`, `id_provincia`) 
VALUES
('Pedro Goyena 1543', 1, 1),             -- Tres de Febrero
('Av. Boulogne Sur Mer 837', 2, 1),      -- Pacheco
('Calle 49 entre 8 y 9 Nº1023', 3, 1),   -- La Plata
('Oliden 1190', 4, 1),                   -- Lomas de Zamora
('Calle Rivadavia 2234', 5, 1),          -- Mar del Plata
('Donado 1200', 6, 1),                   -- Bahía Blanca
('Paz 731', 7, 1),                       -- Tandil
('España 416', 8, 1),                    -- San Nicolás
('Av. Aristóbulo del Valle 4800', 9, 20),-- Santa Fe
('Av. Pellegrini 1032', 10, 20),         -- Rosario
('Bv. Lehmann 1470', 11, 20),            -- Rafaela
('Calle Belgrano 236', 12, 20),          -- Venado Tuerto
('Gral. Obligado 889', 13, 20);          -- Reconquista


INSERT INTO `Clientes` 
(`dni`, `cuil`, `nombre`, `apellido`, `sexo`, `id_nacionalidad`, `fecha_nacimiento`, `id_domicilio`, `correo_electronico`, `telefono`, `id_usuario`) 
VALUES
('30111222', '20-30111222-5', 'Lucía', 'Giménez', 'F', 1, '1990-04-15', 1, 'lucia.gimenez@mail.com', '1122334455', 1),
('28444555', '20-28444555-7', 'Carlos', 'Lopez', 'M', 1, '1985-09-10', 2, 'carlos.lopez@mail.com', '1177889944', 2),
('31222333', '27-31222333-3', 'Martina', 'Ruiz', 'F', 1, '1993-01-22', 3, 'martina.ruiz@mail.com', '1147856985', 3),
('27666111', '23-27666111-4', 'Pablo', 'Fernandez', 'M', 1, '1980-07-30', 4, 'pablo.fernandez@mail.com', '1125369694', 4),
('32555123', '20-32555123-9', 'Natalia', 'Soria', 'F', 1, '1995-11-05', 5, 'natalia.soria@mail.com', '1154879865', 5),
('29888999', '20-29888999-1', 'Emiliano', 'Peralta', 'M', 1, '1992-03-18', 6, 'emiliano.peralta@mail.com', '1125468978', 6),
('31111777', '20-31111777-0', 'Mariela', 'Nuñez', 'F', 1, '1987-12-01', 7, 'mariela.nunez@mail.com', '1130205060', 7),
('30999111', '20-30999111-2', 'Julieta', 'Ramírez', 'F', 1, '1991-06-23', 8, 'julieta.ramirez@mail.com', '1164568975', 8),
('27777333', '20-27777333-5', 'Leonardo', 'Mendoza', 'M', 1, '1982-02-14', 9, 'leonardo.mendoza@mail.com', '1166553397', 9),
('32222444', '27-32222444-6', 'Camila', 'Vega', 'F', 1, '1996-10-09', 10, 'camila.vega@mail.com', '1145454545', 10),
('28888555', '20-28888555-8', 'Matías', 'Ibarra', 'M', 1, '1990-05-17', 11, 'matias.ibarra@mail.com', '1178956987', 11),
('30000333', '23-30000333-4', 'Florencia', 'Benítez', 'F', 1, '1989-11-28', 12, 'florencia.benitez@mail.com', '1147586932', 11),
('26666111', '20-26666111-3', 'Gonzalo', 'Herrera', 'M', 1, '1984-08-05', 13, 'gonzalo.herrera@mail.com', '1124587541', 10);


INSERT INTO `Cuentas` 
(`id_cliente`, `fecha_creacion`, `numero_de_cuenta`, `id_tipo_cuenta`, `cbu`, `saldo`, `estado`)
VALUES
(1, '2025-04-15', '12345678-0001', 1, '98765432101', 1500450.00, TRUE),
(1, '2025-04-15', '12345678-0201', 2, '98765432201', 2900000.00, TRUE),
(2, '2020-02-10', '12345678-0002', 1, '98765432102', 3275020.50, TRUE),
(2, '2020-02-10', '12345678-0202', 2, '98765432202', 1800000.00, TRUE),
(3, '2023-09-30', '12345678-0003', 1, '98765432103', 0.00, TRUE),
(4, '2024-03-13', '12345678-0004', 2, '98765432104', 98000.75, TRUE),
(5, '2025-01-20', '12345678-0005', 1, '98765432105', 2120500.00, TRUE),
(6, '2020-12-14', '12345678-0006', 1, '98765432106', 340000.62, TRUE),
(7, '2024-04-11', '12345678-0007', 2, '98765432107', 5287421.33, TRUE),
(8, '2024-04-06', '12345678-0008', 2, '98765432108', 425000.60, TRUE),
(9, '2023-08-11', '12345678-0009', 1, '98765432109', 7500981.30, TRUE),
(10, '2023-03-01', '12345678-0010', 1, '98765432110', 895700.00, TRUE),
(10, '2022-07-15', '12345678-0011', 2, '98765432111', 2983500.00, TRUE),
(11, '2021-09-20', '12345678-0012', 1, '98765432112', 402000.50, TRUE),
(11, '2020-11-11', '12345678-0013', 2, '98765432113', 600230.00, TRUE);

INSERT INTO Opciones_Plazo (cantidad_cuotas, tasaAnual) VALUES
(3, 5.00),
(6, 7.00),
(9, 10.00),
(12, 12.00),
(18, 14.00),
(24, 16.00),
(30, 18.00),
(36, 20.00),
(42, 22.00),
(48, 24.00),
(60, 26.00);

INSERT INTO Prestamos (id_cliente, id_cuenta, fecha_alta, importe_pedido, id_opcion_plazo, estado)
VALUES 
(1, 1, null, 30000000.00, 2, 0),
(2, 2, null, 4500000.00, 3, 0),
(2, 2, null, 500000.00, 3, 0), 
(2, 2, null, 2000000.00, 4, 0), 
(3, 3, null, 500000000.00, 3, 2); 

CALL AutorizarPrestamo(1);
CALL AutorizarPrestamo(2);
CALL AutorizarPrestamo(3);

UPDATE Cuotas SET estado = 1, fecha_pago = '2025-01-15', fecha_vencimiento= '2025-01-15' WHERE id = 7;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-02-15', fecha_vencimiento= '2025-02-15'  WHERE id = 8;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-03-15', fecha_vencimiento= '2025-03-15' WHERE id = 9;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-04-15', fecha_vencimiento= '2025-04-15' WHERE id = 10;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-05-15', fecha_vencimiento= '2025-05-15'  WHERE id = 11;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-06-15', fecha_vencimiento= '2025-06-15' WHERE id = 12;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-07-15', fecha_vencimiento= '2025-07-15'  WHERE id = 13;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-08-15', fecha_vencimiento= '2025-08-15'  WHERE id = 14;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-09-15', fecha_vencimiento= '2025-09-15'  WHERE id = 15;

UPDATE Cuotas SET estado = 1, fecha_pago = '2025-01-15', fecha_vencimiento= '2025-01-15' WHERE id = 16;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-02-15', fecha_vencimiento= '2025-02-15'  WHERE id = 17;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-03-15', fecha_vencimiento= '2025-03-15' WHERE id = 18;
UPDATE Cuotas SET estado = 1, fecha_pago = '2025-04-15', fecha_vencimiento= '2025-04-15' WHERE id = 19;
UPDATE Cuotas SET fecha_vencimiento= '2025-05-15'  WHERE id = 20;
UPDATE Cuotas SET fecha_vencimiento= '2025-06-15' WHERE id = 21;
UPDATE Cuotas SET fecha_vencimiento= '2025-07-15'  WHERE id = 22;
UPDATE Cuotas SET fecha_vencimiento= '2025-08-15'  WHERE id = 23;
UPDATE Cuotas SET fecha_vencimiento= '2025-09-15'  WHERE id = 24;

INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-01-15', 5102578.12, 'pago cuota 1');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-02-15', 5102578.12, 'pago cuota 2');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-03-15', 5102578.12, 'pago cuota 3');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-04-15', 5102578.12, 'pago cuota 4');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-05-15', 5102578.12, 'pago cuota 5');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-06-15', 5102578.12, 'pago cuota 6');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-07-15', 5102578.12, 'pago cuota 7');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-08-15', 5102578.12, 'pago cuota 8');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-09-15', 5102578.12, 'pago cuota 9');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,3,3,'2025-01-15', 5102578.12, 'pago cuota 1');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,3,3,'2025-02-15', 5102578.12, 'pago cuota 2');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,3,3,'2025-03-15', 5102578.12, 'pago cuota 3');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,3,'2025-04-15', 5102578.12, 'pago cuota 4');

INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2025-01-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2025-02-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2025-03-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2025-04-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2025-05-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2025-06-10', 150000, 'transferencias recibidas');

INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2025-01-10', 100000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2025-02-10', 100000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2025-03-10', 100000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2025-04-10', 100000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,5,'2025-05-10', 100000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,5,'2025-06-10', 100000, 'transferencias salidas');

INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2024-01-10', 50000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2024-02-10', 150000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,4,'2024-03-10', 120000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2024-04-10', 130000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2024-05-10', 50000, 'transferencias recibidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,4,'2024-06-10', 50000, 'transferencias recibidas');

INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2024-01-10', 30000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2024-02-10', 40000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(3,2,5,'2024-03-10', 500000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,5,'2024-04-10', 5000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,5,'2024-05-10', 40000, 'transferencias salidas');
INSERT INTO movimientos (id_cuenta, id_cliente, id_tipo_movimiento, fecha_movimiento, importe, detalle) values(4,2,5,'2024-06-10', 6000, 'transferencias salidas');