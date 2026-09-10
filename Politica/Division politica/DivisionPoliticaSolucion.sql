CREATE TABLE Moneda (
    Id SERIAL PRIMARY KEY,
    Moneda VARCHAR(50) NOT NULL,
    Sigla VARCHAR(10) NULL,
    Imagen VARCHAR(255) NULL
);

INSERT INTO Moneda (Moneda)
SELECT DISTINCT Moneda 
FROM Pais 
WHERE Moneda IS NOT NULL;

ALTER TABLE Pais ADD COLUMN IdMoneda INTEGER NULL;

UPDATE Pais P
SET IdMoneda = M.Id
FROM Moneda M
WHERE P.Moneda = M.Moneda;

ALTER TABLE Pais 
ADD COLUMN Mapa VARCHAR(255) NULL,
ADD COLUMN Bandera VARCHAR(255) NULL;

ALTER TABLE Pais DROP COLUMN Moneda;

ALTER TABLE Pais 
ADD CONSTRAINT fkPais_IdMoneda FOREIGN KEY (IdMoneda)
    REFERENCES Moneda(Id);

CREATE OR REPLACE VIEW vwCiudades AS
    SELECT C.Id IdCiudad, C.Nombre Ciudad,
        R.Id IdRegion, R.Nombre Region,
        P.Id IdPais, P.Nombre Pais,
        C.CapitalPais, C.CapitalRegion
    FROM Pais P
        LEFT JOIN Region R ON R.IdPais=P.Id
        LEFT JOIN Ciudad C ON C.IdRegion = R.Id;