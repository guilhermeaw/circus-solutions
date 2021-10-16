--CRIA UMA PROCED QUE VISUALIZA QUANTIDADE DE VEZES QUE ARTISTA PARTICIPOU DE ESPETACULO NA DATA
CREATE TYPE tartist_quant AS
(name VARCHAR(100), COUNT(id))

CREATE OR REPLACE FUNCTION artist_quant
(pstart_time timestamp, pend_time timestamp)
RETURNS SETOF tartist_quant
AS $$
SELECT a.nome
FROM artists a, schedule s
WHERE a.codartista=s.codartista AND
s.end_time BETWEEN pstart_time AND pend_time
$$ LANGUAGE sql;

SELECT * FROM tartist_quant('01/09/21', '29/09/21')

--CRIA UMA PROCED QUE VISUALIZA QUANTIDADE DE SHOWS COM MAXIMO DE INGRESSOS VENDIDOS NA DATA
CREATE TYPE show_full_capacity AS
(id INT, date TIMESTAMP, cityId INT COUNT(id))

CREATE OR REPLACE FUNCTION show_full_capacity
(pstart_time timestamp, pend_time timestamp)
RETURNS SETOF tartist_quant
AS $$
SELECT sw.nome, s.quant
FROM shows sw, schedule s
WHERE sw.codshow=s.codshow AND
s.end_time BETWEEN pstart_time AND pend_time
$$ LANGUAGE sql;

SELECT * FROM show_full_capacity('01/09/21', '29/09/21')

--SUBCONSULTA DE ARTISTAS PARA RELATORIO, REFEERNCIANDO UM TIPO ESPECIFICO DE CARGO
SELECT id, name, cpf, phone, ref_occupation
FROM artists
where ref_occupation=5

--CRIA VISUALIZADOR DE SHOWS DE SETEMBRO
CREATE VIEW v_shows_setembro_2021
AS
SELECT s.id, s.date, s.capacity
FROM shows s
WHERE s.date BETWEEN '01/09/21' AND '29/09/21'
ORDER BY s.id;