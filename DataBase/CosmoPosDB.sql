#--CosmoPosDB.sql
DROP DATABASE CosmoPos;

CREATE DATABASE CosmoPos;

USE CosmoPos;

CREATE TABLE estados(
	estado_id INT(2)NOT NULL AUTO_INCREMENT,
	estado_nombre VARCHAR(50)NOT NULL,
	descripcion VARCHAR(100)NOT NULL,
	PRIMARY KEY(estado_id)
);
ALTER TABLE estados AUTO_INCREMENT = 1;


CREATE TABLE departamentos(
	departamento_id INT(2)NOT NULL,
	departamento_cod INT(2)NOT NULL,
	INDEX depro_ind(departamento_cod),
	departamento_nombre VARCHAR(30)NOT NULL,
	departamento_iso VARCHAR(3) NOT NULL,
	PRIMARY KEY(departamento_id)
);



CREATE TABLE municipios(
	municipio_id INT(10)NOT NULL AUTO_INCREMENT,
	departamento_cod INT(2)NOT NULL,
	INDEX departamento_ind(departamento_cod),
	FOREIGN KEY(departamento_cod) REFERENCES departamentos(departamento_cod)ON DELETE CASCADE,
	municipio_codigo INT(10)NOT NULL,
	INDEX municipio_ind(municipio_codigo),
	municipio_nombre VARCHAR(30)NOT NULL,
	tipo VARCHAR(7)NOT NULL,
	PRIMARY KEY(municipio_id)
);

CREATE TABLE codigos_postales(
	codigo_postal_id INT(6)NOT NULL AUTO_INCREMENT,
	municipio_codigo INT(10)NOT NULL,
	INDEX minicipio_ind(municipio_codigo),
	FOREIGN KEY(municipio_codigo) REFERENCES municipios(municipio_codigo)ON DELETE CASCADE,
	codigo_postal INT(10)NOT NULL,
	PRIMARY KEY(codigo_postal_id)
);

-- ACTUALIZAR ESTA INFORMACIÓN DE ACUERDO AL ANEXO TÉCNICO
CREATE TABLE tipos_personas(
	tipo_persona_id INT(1)NOT NULL AUTO_INCREMENT,
	tipo_persona_nombre VARCHAR(20)NOT NULL,
	PRIMARY KEY(tipo_persona_id)
);

CREATE TABLE responsabilidades(
	responsabilidad_id INT(3)NOT NULL AUTO_INCREMENT,
	responsabilidad_cod VARCHAR(10),
	responsabilidad_nombre VARCHAR(100),
	PRIMARY KEY(responsabilidad_id)
);

CREATE TABLE direcciones(
	direccion_id INT(11)NOT NULL AUTO_INCREMENT,
	direccion_empresa VARCHAR(200)NOT NULL,
	PRIMARY KEY(direccion_id)
);

CREATE TABLE regimenes(
	regimen_id INT(2)NOT NULL AUTO_INCREMENT,
	regimen_cod VARCHAR(3) NOT NULL,
	regimen_nombre VARCHAR(100)NOT NULL,
	PRIMARY KEY(regimen_id)
);

CREATE TABLE empresas(
	empresa_id INT(11)NOT NULL AUTO_INCREMENT,
	empresa_nombre VARCHAR(100)NOT NULL,
	empresa_nit VARCHAR(20)NOT NULL,
	empresa_dv INT(1)NOT NULL,
	tipo_persona_id INT(1)NOT NULL,
	INDEX tipo_persona_ind(tipo_persona_id),
	FOREIGN KEY(tipo_persona_id) REFERENCES tipos_personas(tipo_persona_id),
	departamento_id INT(2)NOT NULL,
	INDEX departamento_ind(departamento_id),
	FOREIGN KEY(departamento_id) REFERENCES departamentos(departamento_id)ON DELETE CASCADE,
	municipio_id INT(10)NOT NULL,
	INDEX minicipio_ind(municipio_id),
	FOREIGN KEY(municipio_id) REFERENCES municipios(municipio_id)ON DELETE CASCADE,
	codigo_postal_id INT(6)NOT NULL,
	INDEX codigo_postal_ind(codigo_postal_id),
	FOREIGN KEY(codigo_postal_id) REFERENCES codigos_postales(codigo_postal_id)ON DELETE CASCADE,
	direccion_id INT(11)NOT NULL,
	INDEX direccion_ind(direccion_id),
	FOREIGN KEY(direccion_id) REFERENCES direcciones(direccion_id) ON DELETE CASCADE,
	regimen_id INT(2)NOT NULL,
	INDEX regimen_ind(regimen_id),
	FOREIGN KEY(regimen_id) REFERENCES regimenes(regimen_id)ON DELETE CASCADE,
	empresa_telefono VARCHAR(25)NOT NULL,
	empresa_email VARCHAR(50)NOT NULL,
	PRIMARY KEY(empresa_id)
);
ALTER TABLE empresas AUTO_INCREMENT = 1;


CREATE TABLE responsabilidades_empresas(
	responsabilidad_empresa_id INT(11)NOT NULL AUTO_INCREMENT,
	responsabilidad_id INT(3),
	INDEX responsabilidad_ind(responsabilidad_id),
	FOREIGN KEY(responsabilidad_id) REFERENCES responsabilidades(responsabilidad_id) ON DELETE CASCADE,
	empresa_id INT(11)NOT NULL,
	INDEX empresa_ind(empresa_id),
	FOREIGN KEY(empresa_id) REFERENCES empresas(empresa_id)ON DELETE CASCADE,
	PRIMARY KEY(responsabilidad_empresa_id)
);
 -- incluir el campo software_id
 -- incluir el campo pin_software
 -- incluir el campo llave_tecnica


CREATE TABLE dian(
	dian_id INT(5)NOT NULL AUTO_INCREMENT,
	prefix VARCHAR(10)NOT NULL,
	software_id VARCHAR(100)NOT NULL,
	software_pin INT(5)NOT NULL,
	resolucion_txt VARCHAR(100)NOT NULL,
	resolucion_numero VARCHAR(50)NOT NULL,
	fechaDesde DATE NOT NULL,
	fechaHasta DATE NOT NULL,
	rango_inicial VARCHAR(50)NOT NULL,
	rango_final VARCHAR(50)NOT NULL,
	resolucion_estado VARCHAR(20)NOT NULL,
	empresa_id INT(11)NOT NULL,
	llave_tecnica VARCHAR(100)NOT NULL,
	dian_nit VARCHAR(9)NOT NULL DEFAULT "800197268",
	ambiente_ejecucion INT(1)NOT NULL DEFAULT 2,
	INDEX empresa_ind(empresa_id),
	FOREIGN KEY(empresa_id) REFERENCES empresas(empresa_id)ON DELETE CASCADE,
	PRIMARY KEY(dian_id)
);
ALTER TABLE dian AUTO_INCREMENT = 1;


-- EN CASO DE NESECITAR GENERAR UNA LICENCIA
-- CREATE TABLE sotware_info(soft_id BIT(1)NOT NULL, SoftwarePin INT(5)NOT NULL, SoftwareId VARCHAR(200), SoftwareLicenceCode BLOB NOT NULL, PRIMARY KEY(soft_id) );


CREATE TABLE permisos(
	permiso_id INT(2)NOT NULL AUTO_INCREMENT,
	nombre_permiso VARCHAR(25) NOT NULL,
	descripcion VARCHAR(100)NOT NULL,
	PRIMARY KEY(permiso_id)
);

ALTER TABLE permisos AUTO_INCREMENT = 1;


-- ACTUALIZAR ESTA INFORMACIÓN DE ACUERDO AL ANEXO TÉCNICO
CREATE TABLE tipos_documentos(
	tipo_documento_id INT(2)NOT NULL,
	tipo_documento VARCHAR(3)NOT NULL,
	descripcion VARCHAR(100),
	PRIMARY KEY(tipo_documento_id)
);
ALTER TABLE tipos_documentos AUTO_INCREMENT = 1;

CREATE TABLE tipos_usuarios(
	tipo_usuario_id INT(2) NOT NULL AUTO_INCREMENT,
	tipo_usuario VARCHAR(20)NOT NULL,
	PRIMARY KEY(tipo_usuario_id)
);
ALTER TABLE tipos_usuarios AUTO_INCREMENT = 1;

CREATE TABLE tipos_usuarios_permisos(
	tipos_usuarios_permisos_id INT(11)NOT NULL AUTO_INCREMENT,
	tipo_usuario_id INT(2)NOT NULL,
	permiso_id INT(2)NOT NULL,
	INDEX tipo_usuario_ind(tipo_usuario_id),
	FOREIGN KEY(tipo_usuario_id) REFERENCES tipos_usuarios(tipo_usuario_id)ON DELETE CASCADE,
	INDEX permiso_ind(permiso_id),
	FOREIGN KEY(permiso_id) REFERENCES permisos(permiso_id)ON DELETE CASCADE,
	PRIMARY KEY(tipos_usuarios_permisos_id)
);
ALTER TABLE tipos_usuarios_permisos AUTO_INCREMENT = 1;

CREATE TABLE usuarios(
	usuario_id INT(4) NOT NULL AUTO_INCREMENT,
	nombre_usuario VARCHAR(50) NOT NULL,
	passwd VARCHAR(50) NOT NULL,
	tipo_usuario_id INT(2),
	INDEX tipo_usuario_ind(tipo_usuario_id),
	FOREIGN KEY(tipo_usuario_id) REFERENCES tipos_usuarios(tipo_usuario_id)ON DELETE CASCADE,
	PRIMARY KEY(usuario_id)
);
ALTER TABLE usuarios AUTO_INCREMENT = 1;


-- revisar que se va a romper con lOS cambioS de nombreS
-- revisar InformeVentasController.java

CREATE TABLE lineas(
	linea_id INT(11) NOT NULL AUTO_INCREMENT,
	linea_nombre VARCHAR(25)NOT NULL,
	descripcion VARCHAR(100) NOT NULL DEFAULT "NUEVA LINEA",
	linea_estado INT(2) NOT NULL DEFAULT 1,
	PRIMARY KEY(linea_id)
);
ALTER TABLE lineas AUTO_INCREMENT = 1;

CREATE TABLE tipos_productos(
	tipo_producto_id INT(11)NOT NULL AUTO_INCREMENT,
	tipo_producto VARCHAR(50)NOT NULL,
	descripcion VARCHAR(100)NOT NULL,
	PRIMARY KEY(tipo_producto_id)
);
ALTER TABLE tipos_productos AUTO_INCREMENT = 1;

CREATE TABLE sub_tipos(
	sub_tipo_id INT(11)NOT NULL AUTO_INCREMENT,
	tipo_producto_id INT(11)NOT NULL,
	sub_tipo VARCHAR(50)NOT NULL,
	INDEX tipo_producto_ind(tipo_producto_id),
	FOREIGN KEY(tipo_producto_id) REFERENCES tipos_productos(tipo_producto_id)ON DELETE CASCADE,
	PRIMARY KEY(sub_tipo_id)
);
ALTER TABLE sub_tipos AUTO_INCREMENT = 1;

CREATE TABLE clientes(
	cliente_id INT(9) NOT NULL AUTO_INCREMENT,
	tipo_documento_id INT(2)NOT NULL,
	documento VARCHAR(15) NOT NULL,
	nombres VARCHAR(30)NOT NULL,
	apellidos VARCHAR(30),
	telefono VARCHAR(10),
	direccion VARCHAR(100),
	email VARCHAR(50),
	INDEX tipo_documento_ind(tipo_documento_id),
	FOREIGN KEY(tipo_documento_id) REFERENCES tipos_documentos(tipo_documento_id),
	PRIMARY KEY(cliente_id)
);
ALTER TABLE clientes AUTO_INCREMENT = 1;

CREATE TABLE proveedores(
	proveedor_id INT(5)NOT NULL AUTO_INCREMENT,
	proveedor_nombre VARCHAR(100)NOT NULL,
	proveedor_telefono VARCHAR(25)NOT NULL,
	proveedor_direccion VARCHAR(100)NOT NULL,
	municipio_id INT(10)NOT NULL,
	INDEX minicipio_ind(municipio_id),
	FOREIGN KEY(municipio_id) REFERENCES municipios(municipio_id)ON DELETE CASCADE,
	proveedor_cuenta VARCHAR(50)NOT NULL,
	PRIMARY KEY(proveedor_id)
);

ALTER TABLE proveedores AUTO_INCREMENT = 1;

CREATE TABLE compras(
	compra_id INT(11)NOT NULL AUTO_INCREMENT,
	proveedor_id INT(5)NOT NULL,
	factura_no INT(11)NOT NULL,
	factura_fechac DATE,
	factura_fechav DATE,
	INDEX proveedor_ind(proveedor_id),
	FOREIGN KEY(proveedor_id) REFERENCES proveedores(proveedor_id)ON DELETE CASCADE,
	PRIMARY KEY(compra_id) 
);
ALTER TABLE compras AUTO_INCREMENT = 1;

CREATE TABLE productos(
	producto_id INT(11)NOT NULL AUTO_INCREMENT,
	producto_nombre VARCHAR(100)NOT NULL,
	compra_id INT(5)NOT NULL DEFAULT 1,
	estado_producto VARCHAR(10) DEFAULT 'ACTIVO',
	INDEX compra_ind(compra_id),
	FOREIGN KEY(compra_id) REFERENCES compras(compra_id)ON DELETE CASCADE,
	linea_id INT(11)NOT NULL DEFAULT 17,
	INDEX linea_ind(linea_id),
	FOREIGN KEY(linea_id) REFERENCES lineas(linea_id)ON DELETE CASCADE,
	PRIMARY KEY(producto_id)
);
ALTER TABLE productos AUTO_INCREMENT = 1;

-- DROP TABLE precios_productos;
CREATE TABLE precios_productos(
	producto_precio_id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	sub_tipo_id INT(11) NOT NULL,
	producto_precio INT(7)NOT NULL,
	producto_referencia VARCHAR(50),
	producto_id INT(11)NOT NULL,
	INDEX sub_tipo_ind(sub_tipo_id),
	FOREIGN KEY(sub_tipo_id) REFERENCES sub_tipos(sub_tipo_id) ON DELETE CASCADE,
	INDEX producto_ind(producto_id),
	FOREIGN KEY(producto_id) REFERENCES productos(producto_id) ON DELETE CASCADE
);
ALTER TABLE precios_productos AUTO_INCREMENT = 1;

-- DROP TABLE costos;
CREATE TABLE costos(
	costo_id INT(11)NOT NULL AUTO_INCREMENT,
	producto_id INT(11) NOT NULL,
	costo INT(11)NOT NULL,
	INDEX producto_ind(producto_id),
	FOREIGN KEY(producto_id) REFERENCES productos(producto_id)ON DELETE CASCADE,
	PRIMARY KEY(costo_id)
);
ALTER TABLE costos AUTO_INCREMENT = 1;

-- ACTUALIZAR ESTA INFORMACIÓN DE ACUERDO AL ANEXO TÉCNICO
CREATE TABLE tipos_facturas(
	tipo_factura_id INT(2)NOT NULL,
	tipo_factura_code INT(4)NOT NULL,
	tipo_factura_name VARCHAR(10)NOT NULL,
	descripcion VARCHAR(100)NOT NULL,
	PRIMARY KEY(tipo_factura_id)
);

CREATE TABLE z_diarios(
	z_diario_id INT(11)NOT NULL AUTO_INCREMENT,
	z_diario INT(11)NOT NULL,
	z_diario_fecha DATE NOT NULL,
	PRIMARY KEY(z_diario_id)
);
ALTER TABLE z_diarios AUTO_INCREMENT = 1;


-- CREATE TABLE pagos_medios();

-- CREATE TABLE pagos_formas();

CREATE TABLE facturas(
	factura_id INT(11)NOT NULL AUTO_INCREMENT,
	nro_factura INT(11)NOT NULL,
	factura_fecha DATE NOT NULL,
	factura_hora TIME NOT NULL,
	cliente_id INT(9)NOT NULL,
	INDEX cliente_ind(cliente_id),
	FOREIGN KEY(cliente_id) REFERENCES clientes(cliente_id)ON DELETE CASCADE,
	PRIMARY KEY(factura_id)
);
ALTER TABLE facturas AUTO_INCREMENT = 1;


-- CREATE TABLE facturas_electronicas();

-- revisar el DAO el query departamento

CREATE TABLE ventas(
	venta_id INT(11)NOT NULL AUTO_INCREMENT,
	producto_id INT(11),
	linea_id INT(11),
	cantidad INT(4)NOT NULL,
	INDEX linea_ind(linea_id),
	FOREIGN KEY(linea_id) REFERENCES lineas(linea_id) ON DELETE CASCADE,
	INDEX producto_ind(producto_id),
	FOREIGN KEY(producto_id) REFERENCES productos(producto_id) ON DELETE CASCADE,
	subtotal INT(11)NOT NULL,
	vr_iva INT(11)NOT NULL,
	total INT(11)NOT NULL,
	factura_id INT(11)NOT NULL,
	INDEX factura_ind (factura_id),
	FOREIGN KEY(factura_id) REFERENCES facturas(factura_id) ON DELETE CASCADE,
	z_diario_id INT(11)NOT NULL,
	INDEX z_diario_ind(z_diario_id),
	FOREIGN KEY(z_diario_id) REFERENCES z_diarios(z_diario_id)ON DELETE CASCADE,
	PRIMARY KEY(venta_id)
);
ALTER TABLE ventas AUTO_INCREMENT = 1;


CREATE TABLE inventarios(
	registro_id INT(11)NOT NULL AUTO_INCREMENT,
	producto_id INT(11)NOT NULL,
	cantidad INT(11)NOT NULL,
	INDEX producto_ind(producto_id),
	FOREIGN KEY(producto_id) REFERENCES productos(producto_id)ON DELETE CASCADE,
	PRIMARY KEY(registro_id)
);
ALTER TABLE inventario AUTO_INCREMENT = 1;
SHOW TABLES;


