CREATE DATABASE IF NOT EXISTS `banco` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `banco`;

CREATE TABLE `Roles` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre_rol` VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Paises` (
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Provincias` (
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(100) NOT NULL,
    `id_pais` INT NOT NULL,
    CONSTRAINT `FK_Provincia` FOREIGN KEY (`id_pais`) REFERENCES `Paises`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Localidades` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `id_provincia` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `id_provincia_idx` (`id_provincia` ASC),
  CONSTRAINT `FK_localidades_provincia`
    FOREIGN KEY (`id_provincia`)
    REFERENCES `Provincias` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `Usuarios` (
	`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`nombre_usuario` VARCHAR(50) UNIQUE,
	`contrasenia` VARCHAR(50) NOT NULL,
	`id_rol` INT NOT NULL DEFAULT 2,
    `estado` BOOLEAN NOT NULL DEFAULT TRUE,
	 CONSTRAINT `FK_Rol` FOREIGN KEY (`id_rol`) REFERENCES `Roles`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Domicilios` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `direccion` VARCHAR(150) NOT NULL,
  `id_localidad` INT NOT NULL,
  `id_provincia` INT NOT NULL,
  `estado` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`id`),
  INDEX `id_localidad_idx` (`id_localidad` ASC),
  INDEX `id_provincia_idx` (`id_provincia` ASC),
  CONSTRAINT `fk_domicilios_localidad`
    FOREIGN KEY (`id_localidad`)
    REFERENCES `Localidades` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_domicilios_provincia`
    FOREIGN KEY (`id_provincia`)
    REFERENCES `Provincias` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `Clientes` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `dni` VARCHAR(20) NOT NULL UNIQUE,
    `cuil` VARCHAR(20) NOT NULL UNIQUE,
    `nombre` VARCHAR(50) NOT NULL,
    `apellido` VARCHAR(50) NOT NULL,
    `sexo` CHAR(1) NOT NULL,
    `id_nacionalidad` int NOT NULL,
    `fecha_nacimiento` DATE NOT NULL,
    `id_domicilio` INT NOT NULL,
    `correo_electronico` VARCHAR(50) NOT NULL UNIQUE,
    `telefono` VARCHAR(50) NOT NULL,
    `id_usuario` INT NOT NULL,
    `estado` BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT `fk_nacionalidad_pais`
    FOREIGN KEY (`id_nacionalidad`)
    REFERENCES `Paises` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_domicilio` 
    FOREIGN KEY (`id_domicilio`) 
    REFERENCES `Domicilios`(`id`)
	ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `FK_Usuario` 
    FOREIGN KEY (`id_usuario`) 
    REFERENCES `Usuarios`(`id`)
	ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Tipos_Cuentas` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `descripcion` VARCHAR(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Cuentas` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_cliente` INT NOT NULL,
    `fecha_creacion` DATE NOT NULL,
    `numero_de_cuenta` VARCHAR(100) NOT NULL UNIQUE,
    `id_tipo_cuenta` INT NOT NULL,
    `cbu` VARCHAR(100) NOT NULL UNIQUE,
    `saldo` DECIMAL(15,2) NOT NULL CHECK (`saldo` >= 0),
    `estado` BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT `FK_tipo_cuenta` FOREIGN KEY (`id_tipo_cuenta`) REFERENCES `Tipos_Cuentas`(`id`),
    CONSTRAINT `FK_cliente_cuenta` FOREIGN KEY (`id_cliente`) REFERENCES `Clientes`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Tipos_Movimientos` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `descripcion` VARCHAR(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Transferencias` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `cuenta_origen` INT NOT NULL,
    `cuenta_destino` INT NOT NULL,
    `monto` DECIMAL(15,2) NOT NULL CHECK (monto > 0),
    `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `descripcion` VARCHAR(255),
    CONSTRAINT `FK_transferencia_origen` FOREIGN KEY (`cuenta_origen`) REFERENCES `Cuentas`(`id`),
    CONSTRAINT `FK_transferencia_destino` FOREIGN KEY (`cuenta_destino`) REFERENCES `Cuentas`(`id`)
);

CREATE TABLE `Movimientos` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_cuenta` INT NOT NULL,
    `id_tipo_movimiento` INT NOT NULL,
    `fecha_movimiento` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `importe` DECIMAL(10,2) NOT NULL,
    `detalle` VARCHAR(255),
    `id_transferencia` INT NULL,
    CONSTRAINT `FK_tipo_movimiento` FOREIGN KEY (`id_tipo_movimiento`) REFERENCES `Tipos_Movimientos`(`id`),
    CONSTRAINT `FK_cuenta_movimiento` FOREIGN KEY (`id_cuenta`) REFERENCES `Cuentas`(`id`),
    CONSTRAINT `FK_movimiento_transferencia` FOREIGN KEY (`id_transferencia`) REFERENCES `Transferencias`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `Opciones_Plazo` (
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `cantidad_cuotas` INT, 
    `tasaAnual` DECIMAL(5,2)
);

CREATE TABLE `Prestamos` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_cliente` INT NOT NULL,
    `id_cuenta` INT NOT NULL,
    `fecha_alta` DATE NULL,
    `importe_pedido` DECIMAL(15,2) NOT NULL CHECK (`importe_pedido` >= 0),
    `id_opcion_plazo` INT NOT NULL,
    `estado` TINYINT NOT NULL DEFAULT 0,
    CONSTRAINT `FK_cliente_prestamo` FOREIGN KEY (`id_cliente`) REFERENCES `Clientes`(`id`),
    CONSTRAINT `FK_cuenta_prestamo` FOREIGN KEY (`id_cuenta`) REFERENCES `Cuentas`(`id`),
    CONSTRAINT `FK_plazo_prestamo` FOREIGN KEY (`id_opcion_plazo`) REFERENCES `Opciones_Plazo`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `Cuotas` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_prestamo` INT NOT NULL,
    `nro_cuota` INT NOT NULL,
    `importe` DECIMAL(15,2) NOT NULL CHECK (`importe` >= 0),
    `fecha_pago` DATE NULL,
    `fecha_vencimiento` DATE NOT NULL,
    `estado` BOOLEAN NOT NULL DEFAULT 0,
    CONSTRAINT `FK_prestamo_cuota` FOREIGN KEY (`id_prestamo`) REFERENCES `Prestamos`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DELIMITER $$

CREATE TRIGGER `verificar_saldo_transferencia`
BEFORE INSERT ON `Transferencias`
FOR EACH ROW
BEGIN
    DECLARE saldo_actual DECIMAL(15,2);

    SELECT saldo INTO saldo_actual
    FROM Cuentas
    WHERE id = NEW.cuenta_origen;

    IF saldo_actual < NEW.monto THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Saldo insuficiente en la cuenta de origen.';
    END IF;
END$$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION CalcularCuotaPrestamo(
	MontoSolicitado DECIMAL(15,2), 
    IdOpcionPlazo INT
) RETURNS DECIMAL(15,2)
DETERMINISTIC
BEGIN
	DECLARE cantMeses INT;
    DECLARE tasa DECIMAL(5,2);
    DECLARE importeMensual DECIMAL(15,2);
    
    SELECT cantidad_cuotas, tasaAnual
    INTO cantMeses, tasa
    FROM Opciones_Plazo
    WHERE id = IdOpcionPlazo;
    
    SET importeMensual = ROUND(
        (MontoSolicitado * (tasa / 100 / 12)) /
        (1 - POW(1 + (tasa / 100 / 12), -cantMeses)), 2);
	
    RETURN importeMensual;
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE SolicitarPrestamo(
    IN p_id_cliente INT,
    IN p_id_cuenta INT,
    IN p_importe_pedido DECIMAL(15,2),
    IN p_id_opcion_plazo INT    
)
BEGIN
    INSERT INTO Prestamos (id_cliente, id_cuenta, fecha_alta, importe_pedido, id_opcion_plazo, estado)
    VALUES (p_id_cliente, p_id_cuenta, null , p_importe_pedido, p_id_opcion_plazo, 0);
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE AutorizarPrestamo(
    IN p_id_prestamo INT
)
BEGIN
    DECLARE v_importe_pedido DECIMAL(15,2);
    DECLARE v_id_opcion_plazo INT;
    DECLARE v_id_cuenta INT;
    DECLARE v_cantidad_cuotas INT;
    DECLARE v_importe_mensual DECIMAL(15,2);
    DECLARE v_fecha_vencimiento DATE;
    DECLARE i INT DEFAULT 1;

    -- Obtener datos del préstamo
    SELECT importe_pedido, id_opcion_plazo, id_cuenta
    INTO v_importe_pedido, v_id_opcion_plazo, v_id_cuenta
    FROM Prestamos
    WHERE id = p_id_prestamo;

    -- Obtener cantidad de cuotas
    SELECT cantidad_cuotas INTO v_cantidad_cuotas
    FROM Opciones_Plazo
    WHERE id = v_id_opcion_plazo;

    SET v_importe_mensual = CalcularCuotaPrestamo(v_importe_pedido, v_id_opcion_plazo);

    -- Actualizar estado del préstamo y fecha de alta
    UPDATE Prestamos
    SET estado = 1,
        fecha_alta = CURDATE()
    WHERE id = p_id_prestamo;

    -- Acreditar el dinero en la cuenta del cliente
    UPDATE Cuentas
    SET saldo = saldo + v_importe_pedido
    WHERE id = v_id_cuenta;

    -- Generar cuotas con vencimientos mensuales
    SET v_fecha_vencimiento = DATE_ADD(CURDATE(), INTERVAL 1 MONTH);

    WHILE i <= v_cantidad_cuotas DO
        INSERT INTO Cuotas (id_prestamo, nro_cuota, importe, fecha_vencimiento, estado)
        VALUES (p_id_prestamo, i, v_importe_mensual, v_fecha_vencimiento, 0);
        
        SET i = i + 1;
        SET v_fecha_vencimiento = DATE_ADD(v_fecha_vencimiento, INTERVAL 1 MONTH);
    END WHILE;
END$$

DELIMITER ;


DELIMITER ;

DELIMITER $$
CREATE PROCEDURE SP_BAJA_CLIENTE(IN dni_cliente VARCHAR(8), IN cuil_cliente VARCHAR(20))
BEGIN
    DECLARE id_usuario INT;
    DECLARE id_cliente INT;
    DECLARE id_domicilio INT;
    DECLARE fila_cliente INT DEFAULT 0;
    DECLARE fila_usuario INT DEFAULT 0;
    DECLARE resultado INT DEFAULT 0;

	START TRANSACTION;

    IF EXISTS (SELECT 1 FROM Clientes WHERE dni = dni_cliente AND cuil = cuil_cliente) THEN 
		
        SELECT C.id, C.id_domicilio INTO id_cliente, id_domicilio FROM Clientes AS C WHERE C.dni = dni_cliente AND C.cuil = cuil_cliente;
		SELECT C.id_usuario INTO id_usuario FROM  Clientes AS C WHERE dni = dni_cliente AND cuil = cuil_cliente;

        IF EXISTS(SELECT 1 FROM Usuarios WHERE id = id_usuario) THEN

			UPDATE Clientes SET estado = false WHERE dni = dni_cliente AND cuil = cuil_cliente;
            SET fila_cliente = ROW_COUNT();
			UPDATE Usuarios SET estado = false WHERE id = id_usuario;
            SET fila_usuario = ROW_COUNT();
            UPDATE Cuentas AS Cuenta SET estado = false WHERE Cuenta.id_cliente = id_cliente;
            UPDATE Domicilios AS D SET estado = false WHERE D.id = id_domicilio;
		ELSE
			ROLLBACK;
		END IF;

	COMMIT;

    SET resultado = fila_cliente + fila_usuario;
    SELECT resultado;

    ELSE
		ROLLBACK;
	END IF;
END$$

DELIMITER $$

CREATE PROCEDURE sp_AgregarClienteConUsuario(
	IN p_nombre_usuario varchar(50),
    IN p_contrasenia varchar(50),
    IN p_dni varchar(20),
    IN p_cuil varchar(20),
    IN p_nombre_cliente varchar(50),
    IN p_apellido_cliente varchar(50),
    IN p_sexo char,
    IN p_id_nacionalidad int,
    IN p_fecha_nacimiento date,
    IN p_correo_electronico varchar(50),
    IN p_telefono varchar(50),
    IN p_direccion varchar(150),
    IN p_id_localidad int,
    IN p_id_provincia int,
    OUT p_id_cliente INT
    ) 
BEGIN
	DECLARE v_id_usuario INT;
    DECLARE v_id_domicilio INT;
	DECLARE EXIT HANDLER FOR SQLEXCEPTION

    BEGIN
        ROLLBACK;
        SET p_id_cliente = -1;
    END; 
    
    INSERT INTO usuarios(nombre_usuario, contrasenia, id_rol, estado)
    VALUES (p_nombre_usuario, p_contrasenia, 2, 1);
    
    SET v_id_usuario  = LAST_INSERT_ID();
    
    INSERT INTO domicilios(direccion, id_localidad, id_provincia)
    VALUES (p_direccion, p_id_localidad, p_id_provincia);
    
    SET v_id_domicilio= LAST_INSERT_ID();
    
    INSERT INTO clientes(dni, cuil, nombre, apellido, sexo, id_nacionalidad, fecha_nacimiento, id_domicilio, correo_electronico, telefono, id_usuario, estado)
    VALUES (p_dni, p_cuil, p_nombre_cliente, p_apellido_cliente, p_sexo, p_id_nacionalidad, p_fecha_nacimiento,v_id_domicilio, p_correo_electronico, p_telefono, v_id_usuario, 1);
    
    SET p_id_cliente = LAST_INSERT_ID();
    
    COMMIT;
    
END$$

DELIMITER ;


DELIMITER $$

CREATE PROCEDURE pagar_cuota (
    IN p_id_cuota INT,
    IN p_id_cuenta INT,
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE v_importe DECIMAL(15,2);
    DECLARE v_saldo DECIMAL(15,2);
    DECLARE v_nro_cuota INT;

    -- Obtener importe y número de cuota
    SELECT importe, nro_cuota INTO v_importe, v_nro_cuota
    FROM Cuotas
    WHERE id = p_id_cuota;

    -- Obtener saldo actual
    SELECT saldo INTO v_saldo
    FROM Cuentas
    WHERE id = p_id_cuenta;

    IF v_saldo >= v_importe THEN
        -- Descontar saldo
        UPDATE Cuentas
        SET saldo = saldo - v_importe
        WHERE id = p_id_cuenta;

        -- Marcar cuota como pagada
        UPDATE Cuotas
        SET estado = 1,
            fecha_pago = CURRENT_DATE
        WHERE id = p_id_cuota;

        -- Registrar movimiento (sin id_cliente)
        INSERT INTO Movimientos (
            id_cuenta,
            id_tipo_movimiento,
            importe,
            detalle,
            id_transferencia
        ) VALUES (
            p_id_cuenta,
            3, -- ID tipo movimiento: "Pago de Préstamo"
            -1 * v_importe,
            CONCAT('Pago de cuota nro ', v_nro_cuota),
            NULL
        );

        SET p_resultado = TRUE;
    ELSE
        SET p_resultado = FALSE;
    END IF;
END$$

DELIMITER ;
