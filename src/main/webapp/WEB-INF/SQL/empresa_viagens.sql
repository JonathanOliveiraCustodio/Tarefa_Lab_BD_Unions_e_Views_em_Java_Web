USE master
--DROP DATABASE empresa_viagens

CREATE DATABASE	empresa_viagens
USE empresa_viagens

CREATE TABLE motorista(
codigo				INT				NOT NULL,
nome				VARCHAR(80)		NOT NULL,
naturalidade		VARCHAR(40)		NOT NULL
PRIMARY KEY (codigo)
)
GO


CREATE TABLE onibus(			
placa	    CHAR(7)				NOT NULL,
marca		VARCHAR(20)			NOT NULL,
ano			INT					NOT NULL,
descricao   VARCHAR(30)			NOT NULL
PRIMARY KEY (placa)
)
GO

CREATE TABLE viagem (
    codigo         INT               NOT NULL,
    onibus         CHAR(7)           NOT NULL,
    motorista      INT               NOT NULL,
    hora_Saida     INT               NOT NULL CHECK (Hora_Saida >= 0),
    hora_Chegada   INT               NOT NULL CHECK (Hora_Chegada >= 0),
    partida        VARCHAR(40)       NOT NULL,
    destino        VARCHAR(40)       NOT NULL
    PRIMARY KEY (codigo),
    FOREIGN KEY (onibus) REFERENCES onibus(placa),
    FOREIGN KEY (motorista) REFERENCES motorista(codigo)
)
GO


INSERT INTO motorista VALUES
(12341,	'Julio Cesar',	'S�o Paulo'),
(12342,	'Mario Carmo',	'Americana'),
(12343,	'Lucio Castro',	'Campinas'),
(12344,	'Andr� Figueiredo',	'S�o Paulo'),
(12345,	'Luiz Carlos',	'S�o Paulo'),
(12346,	'Carlos Roberto',	'Campinas'),
(12347,	'Jo�o Paulo',	'S�o Paulo')

INSERT INTO onibus VALUES
('adf0965',	'Mercedes',	2009,	'Leito'),
('bhg7654',	'Mercedes',	2012,	'Sem Banheiro'),
('dtr2093',	'Mercedes',	2017,	'Ar Condicionado'),
('gui7625',	'Volvo',	2014,	'Ar Condicionado'),
('jhy9425',	'Volvo',	2018,	'Leito'),
('lmk7485',	'Mercedes',	2015,	'Ar Condicionado'),
('aqw2374',	'Volvo',	2014,	'Leito')

INSERT INTO viagem VALUES
(101,	'adf0965',	12343,	10,	12,	'S�o Paulo',				'Campinas'),
(102,	'gui7625',	12341,	7,	12,	'S�o Paulo',				'Araraquara'),
(103,	'bhg7654',	12345,	14,	22,	'S�o Paulo',				'Rio de Janeiro'),
(104,	'dtr2093',	12344,	18,	21,	'S�o Paulo',				'Sorocaba'),
(105,	'aqw2374',	12342,	11,	17,	'S�o Paulo',				'Ribeir�o Preto'),
(106,	'jhy9425',	12347,	10,	19,	'S�o Paulo',				'S�o Jos� do Rio Preto'),
(107,	'lmk7485',	12346,	13,	20,	'S�o Paulo',				'Curitiba'),
(108,	'adf0965',	12343,	14,	16,	'Campinas',					'S�o Paulo'),
(109,	'gui7625',	12341,	14,	19,	'Araraquara',				'S�o Paulo'),
(110,	'bhg7654',	12345,	0,	8,	'Rio de Janeiro',			'S�o Paulo'),
(111,	'dtr2093',	12344,	22,	1,	'Sorocaba',					'S�o Paulo'),
(112,	'aqw2374',	12342,	19,	5,	'Ribeir�o Preto',			'S�o Paulo'),
(113,	'jhy9425',	12347,	22,	7,	'S�o Jos� do Rio Preto',	'S�o Paulo'),
(114,	'lmk7485',	12346,	0,	7,	'Curitiba',					'S�o Paulo')

SELECT v.codigo AS codViagem, v.onibus AS placaOnibus, v.motorista AS codMotorista, 
                 v.hora_Saida AS horaSaida, v.hora_Chegada AS horaChegada, v.partida AS partida, v.destino AS destino,
                 o.marca AS marcaOnibus, m.nome AS nomeMotorista 
                 FROM viagem v 
                 INNER JOIN onibus o ON v.onibus = o.placa 
                 INNER JOIN motorista m ON v.motorista = m.codigo 

SELECT * FROM onibus
SELECT * FROM viagem
--Exerc�cio:															
--1) Criar um Union das tabelas Motorista e �nibus, com as colunas ID (C�digo e Placa) e Nome (Nome e Marca)

SELECT CAST(codigo AS CHAR(7)) AS ID, nome AS Nome, 'motorista' AS Origem FROM motorista
UNION
SELECT placa AS ID, marca AS Nome, 'onibus' AS Origem FROM onibus;


--2) Criar uma View (Chamada v_motorista_onibus) do Union acima		

CREATE VIEW v_motorista_onibus AS
SELECT CAST(codigo AS VARCHAR(7)) AS ID, nome AS Nome, 'motorista' AS Origem FROM motorista
UNION
SELECT placa AS ID, marca AS Nome, 'onibus' AS Origem FROM onibus;

SELECT * FROM v_motorista_onibus
--3) Criar uma View (Chamada v_descricao_onibus) que mostre o C�digo da Viagem, o Nome do motorista, a placa do �nibus (Formato XXX-0000), a Marca do �nibus, o Ano do �nibus e a descri��o do onibus		

DROP VIEW v_descricao_onibus 
CREATE VIEW v_descricao_onibus AS
SELECT
    v.codigo AS codigoViagem,
    m.nome AS nomeMotorista,
    CONCAT(UPPER(SUBSTRING(o.placa, 1, 3)), '-', UPPER(SUBSTRING(o.placa, 4, 4))) AS placaOnibus,
    o.marca AS marcaOnibus,
    o.ano AS anoOnibus,
    o.descricao AS descricaoOnibus
FROM
    viagem v
    INNER JOIN motorista m ON v.motorista = m.codigo
    INNER JOIN onibus o ON v.onibus = o.placa;


	SELECT * FROM v_descricao_onibus

--4) Criar uma View (Chamada v_descricao_viagem) que mostre o C�digo da viagem, a placa do �nibus(Formato XXX-0000), a Hora da Sa�da da viagem (Formato HH:00), a Hora da Chegada da viagem (Formato HH:00), partida e destino															
DROP VIEW v_descricao_viagem
CREATE VIEW v_descricao_viagem AS
SELECT
    codigo AS codigoViagem,
    CONCAT(UPPER(SUBSTRING(onibus, 1, 3)), '-', UPPER(SUBSTRING(onibus, 4, 4))) AS placaOnibus,
    CONVERT(VARCHAR, hora_Saida, 108) + ':00' AS horaSaida,
    CONVERT(VARCHAR, hora_Chegada, 108) + ':00' AS horaChegada,
    partida,
    destino
FROM
    viagem;

	SELECT * FROM v_descricao_viagem


CREATE VIEW v_listar AS
SELECT v.codigo AS codigoViagem, v.onibus AS placaOnibus, v.motorista AS codigoMotorista,
v.hora_Saida AS horaSaida, v.hora_Chegada AS horaChegada, v.partida AS partida, v.destino AS destino,
o.marca AS marcaOnibus, m.nome AS nomeMotorista
FROM viagem v
INNER JOIN onibus o ON v.onibus = o.placa 
INNER JOIN motorista m ON v.motorista = m.codigo

SELECT * FROM v_listar