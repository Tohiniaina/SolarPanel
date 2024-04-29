CREATE DATABASE Solaire;

\c solaire;

CREATE SEQUENCE seq_centre;
CREATE SEQUENCE seq_sourceSolaire;
CREATE SEQUENCE seq_batterie;
CREATE SEQUENCE seq_energie;
CREATE SEQUENCE seq_salle;
CREATE SEQUENCE seq_presence;
CREATE SEQUENCE seq_coupure;

DROP SEQUENCE seq_centre;
DROP SEQUENCE seq_sourceSolaire;
DROP SEQUENCE seq_batterie;
DROP SEQUENCE seq_energie;
DROP SEQUENCE seq_salle;
DROP SEQUENCE seq_presence;
DROP SEQUENCE seq_coupure;

CREATE TABLE centre
(
    idCentre VARCHAR(15) DEFAULT 'CENTRE'||nextval('seq_centre') PRIMARY KEY,
    nomCentre VARCHAR(100)
);

INSERT INTO centre
    (nomCentre)
VALUES('ITU Andoharanofotsy');

CREATE TABLE source_solaire
(
    idsource_solaire VARCHAR(15) DEFAULT 'SRCSOL'||nextval('seq_sourceSolaire') PRIMARY KEY,
    puissance DOUBLE PRECISION
);

INSERT INTO source_solaire
    (puissance)
VALUES(20000);
INSERT INTO source_solaire
    (puissance)
VALUES(25000);
INSERT INTO source_solaire
    (puissance)
VALUES(15000);

CREATE TABLE batterie
(
    idBatterie VARCHAR(15) DEFAULT 'BAT'||nextval('seq_batterie') PRIMARY KEY,
    capacite DOUBLE PRECISION
);

INSERT INTO batterie
    (capacite)
VALUES(20000);
INSERT INTO batterie
    (capacite)
VALUES(30000);
INSERT INTO batterie
    (capacite)
VALUES(40000);

CREATE TABLE source_energie
(
    idsource_energie VARCHAR(15) DEFAULT 'SRCENE'||nextval('seq_energie') PRIMARY KEY,
    idCentre VARCHAR(15),
    nom VARCHAR(100),
    idsource_solaire VARCHAR(15) REFERENCES source_solaire,
    idBatterie VARCHAR(15) REFERENCES batterie
);

INSERT INTO source_energie
    (idCentre, nom, idsource_solaire, idBatterie)
VALUES('CENTRE1', 'secteur1', 'SRCSOL1', 'BAT1');
INSERT INTO source_energie
    (idCentre, nom, idsource_solaire, idBatterie)
VALUES('CENTRE1', 'secteur2', 'SRCSOL2', 'BAT3');
INSERT INTO source_energie
    (idCentre, nom, idsource_solaire, idBatterie)
VALUES('CENTRE1', 'secteur2', 'SRCSOL3', 'BAT2');

CREATE TABLE salle
(
    idsalle VARCHAR(15) DEFAULT 'SALLE'||nextval('seq_salle') PRIMARY KEY,
    nom VARCHAR(100),
    idsource_energie VARCHAR(15) REFERENCES source_energie
);

INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle1', 'SRCENE1');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle2', 'SRCENE1');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle3', 'SRCENE2');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle4', 'SRCENE2');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle5', 'SRCENE3');

CREATE TABLE presence_salle
(
    idpresence VARCHAR(15) DEFAULT 'PRE'||nextval('seq_presence') PRIMARY KEY,
    date DATE NOT NULL,
    idsalle VARCHAR(15) REFERENCES salle,
    nbr_personne INT DEFAULT 0,
    partie INT,
    puissance DOUBLE PRECISION
);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE1', 89, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE1', 91, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE1', 102, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE1', 98, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE1', 94, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE1', 92, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE1', 85, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE1', 94, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE2', 75, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE2', 72, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE2', 87, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE2', 80, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE2', 78, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE2', 84, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE2', 90, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE2', 94, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE3', 74, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE3', 78, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE3', 80, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE3', 85, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE3', 78, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE3', 81, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE3', 82, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE3', 79, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE4', 80, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE4', 85, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE4', 89, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE4', 90, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE4', 91, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE4', 88, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE4', 87, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE4', 90, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE5', 110, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-04', 'SALLE5', 105, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE5', 102, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-11', 'SALLE5', 99, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE5', 106, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-18', 'SALLE5', 107, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE5', 112, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-25', 'SALLE5', 108, 1);

CREATE TABLE coupure
(
    idcoupure VARCHAR(15) DEFAULT 'COUP'||nextval('seq_coupure') PRIMARY KEY,
    idsource_energie VARCHAR(15) REFERENCES source_energie,
    date DATE NOT NULL,
    heure TIME NOT NULL,
    puissance_etudiant DOUBLE PRECISION
);

-- Meteo
CREATE TABLE puissance_solaire
(
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    h_debut TIME NOT NULL,
    h_fin TIME NOT NULL,
    niveau DOUBLE PRECISION
);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:08:00', '00:09:00', 2);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:09:00', '00:10:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:10:00', '00:11:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:11:00', '00:12:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:12:00', '00:13:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:13:00', '00:14:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:14:00', '00:15:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:15:00', '00:16:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-04', '00:16:00', '00:17:00', 3);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:08:00', '00:09:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:09:00', '00:10:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:10:00', '00:11:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:11:00', '00:12:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:12:00', '00:13:00', 9);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:13:00', '00:14:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:14:00', '00:15:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:15:00', '00:16:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:16:00', '00:17:00', 3);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:08:00', '00:09:00', 1);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:09:00', '00:10:00', 2);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:10:00', '00:11:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:11:00', '00:12:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:12:00', '00:13:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:13:00', '00:14:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:14:00', '00:15:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:15:00', '00:16:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-18', '00:16:00', '00:17:00', 3);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:08:00', '00:09:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:09:00', '00:10:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:10:00', '00:11:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:11:00', '00:12:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:12:00', '00:13:00', 9);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:13:00', '00:14:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:14:00', '00:15:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:15:00', '00:16:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-25', '00:16:00', '00:17:00', 4);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:08:00', '00:09:00', 2);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:09:00', '00:10:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:10:00', '00:11:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:11:00', '00:12:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:12:00', '00:13:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:13:00', '00:14:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:14:00', '00:15:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:15:00', '00:16:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-01', '00:16:00', '00:17:00', 3);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:08:00', '00:09:00', 1);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:09:00', '00:10:00', 2);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:10:00', '00:11:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:11:00', '00:12:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:12:00', '00:13:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:13:00', '00:14:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:14:00', '00:15:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:15:00', '00:16:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2024-01-08', '00:16:00', '00:17:00', 2);

--- View details source_energie
CREATE OR REPLACE VIEW v_details_source_energie AS
SELECT se.idsource_energie, se.nom, se.idcentre, ce.nomcentre, se.idsource_solaire, se.idBatterie, ss.puissance, bt.capacite
FROM source_energie se
    JOIN centre ce ON se.idCentre = ce.idCentre
    JOIN source_solaire ss ON se.idsource_solaire = ss.idsource_solaire
    JOIN batterie bt ON se.idBatterie = bt.idBatterie;


--- View details source_energie d'un salle
CREATE OR REPLACE VIEW v_source_energie_salle AS
SELECT sl.*, se.nom nomsecteur, se.idcentre, ce.nomcentre, se.idsource_solaire, se.idBatterie, ss.puissance, bt.capacite
FROM salle sl
    JOIN source_energie se ON sl.idsource_energie = se.idsource_energie
    JOIN centre ce ON se.idCentre = ce.idCentre
    JOIN source_solaire ss ON se.idsource_solaire = ss.idsource_solaire
    JOIN batterie bt ON se.idBatterie = bt.idBatterie;


--- Test Data

INSERT INTO batterie
    (capacite)
VALUES(140000);


INSERT INTO source_solaire
    (puissance)
VALUES(150000);

INSERT INTO source_energie
    (idCentre, nom, idsource_solaire, idBatterie)
VALUES('CENTRE1', 'secteur4', 'SRCSOL4', 'BAT4');

INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle6', 'SRCENE4');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle7', 'SRCENE4');

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-27', 'SALLE6', 120, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-27', 'SALLE6', 120, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-27', 'SALLE7', 420, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-27', 'SALLE7', 323, 1);


INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:08:00', '00:09:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:09:00', '00:10:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:10:00', '00:11:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:11:00', '00:12:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:12:00', '00:13:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:13:00', '00:14:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:14:00', '00:15:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:15:00', '00:16:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-11-27', '00:16:00', '00:17:00', 3);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:08:00', '00:09:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:09:00', '00:10:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:10:00', '00:11:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:11:00', '00:12:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:12:00', '00:13:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:13:00', '00:14:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:14:00', '00:15:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:15:00', '00:16:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-11', '00:16:00', '00:17:00', 3);

-------------------------------------------------

INSERT INTO batterie
    (capacite)
VALUES(725);


INSERT INTO source_solaire
    (puissance)
VALUES(110.3);

INSERT INTO source_energie
    (idCentre, nom, idsource_solaire, idBatterie)
VALUES('CENTRE1', 'secteur5', 'SRCSOL5', 'BAT5');

INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle8', 'SRCENE5');
INSERT INTO salle
    (nom,idsource_energie)
VALUES('Salle9', 'SRCENE5');

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-01', 'SALLE8', 75, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-01', 'SALLE8', 60, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-01', 'SALLE9', 80, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-01', 'SALLE9', 45, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-08', 'SALLE8', 100, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-08', 'SALLE8', 40, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-08', 'SALLE9', 110, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-08', 'SALLE9', 55, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-15', 'SALLE8', 90, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-15', 'SALLE8', 50, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-15', 'SALLE9', 140, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-15', 'SALLE9', 30, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-22', 'SALLE8', 120, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-22', 'SALLE8', 35, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-22', 'SALLE9', 90, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-22', 'SALLE9', 40, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-29', 'SALLE8', 80, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-29', 'SALLE8', 45, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-29', 'SALLE9', 70, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-11-29', 'SALLE9', 75, 1);

INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-06', 'SALLE8', 10, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-06', 'SALLE8', 7, 1);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-06', 'SALLE9', 10, 0);
INSERT INTO presence_salle
    (date, idsalle, nbr_personne, partie)
VALUES('2023-12-06', 'SALLE9', 8, 1);


INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:08:00', '00:09:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:09:00', '00:10:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:10:00', '00:11:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:11:00', '00:12:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:12:00', '00:13:00', 9);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:13:00', '00:14:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:14:00', '00:15:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:15:00', '00:16:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-06', '00:16:00', '00:17:00', 6);

INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:08:00', '00:09:00', 5);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:09:00', '00:10:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:10:00', '00:11:00', 8);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:11:00', '00:12:00', 6);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:12:00', '00:13:00', 9);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:13:00', '00:14:00', 7);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:14:00', '00:15:00', 4);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:15:00', '00:16:00', 3);
INSERT INTO puissance_solaire
    (date, h_debut, h_fin, niveau)
VALUES('2023-12-13', '00:16:00', '00:17:00', 6);

