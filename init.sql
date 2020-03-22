DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

-- TABLES
CREATE TABLE pae.clients(
	client_id SERIAL PRIMARY KEY,
	nom VARCHAR NOT NULL,
	prenom VARCHAR NOT NULL,
	rue VARCHAR NOT NULL,
	no_boite INTEGER NOT NULL,
	code_postal VARCHAR NOT NULL,
	ville VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	telephone VARCHAR NOT NULL,
);

CREATE TABLE pae.utilisateurs(
	utilisateur_id SERIAL PRIMARY KEY,
	confirme boolean NOT NULL,
	ouvrier boolean NOT NULL,
	date_inscription DATE NOT NULL,
	pseudo VARCHAR NOT NULL,
	mot_de_passe VARCHAR NOT NULL,
	client_id INTEGER REFERENCES pae.clients(client_id),
	nom VARCHAR NOT NULL,
	prenom VARCHAR NOT NULL,
	ville VARCHAR NOT NULL,
	email VARCHAR NOT NULL
);

CREATE TABLE pae.devis(
	devis_id SERIAL PRIMARY KEY,
	client INTEGER REFERENCES pae.clients(client_id) NOT NULL,
	date_debut DATE NOT NULL,
	montant_total INTEGER NOT NULL,
	duree INTEGER NOT NULL,
	etat VARCHAR NOT NULL,
	date_devis DATE NOT NULL
);

CREATE TABLE pae.types_amenagements(
	type_amenagement SERIAL PRIMARY KEY,
	libelle VARCHAR NOT NULL
);

CREATE TABLE pae.travaux(
	devis_id INTEGER REFERENCES pae.devis(devis_id) NOT NULL,
	type_amenagement INTEGER REFERENCES pae.types_amenagements(type_amenagement) NOT NULL,
	PRIMARY KEY(devis_id,type_amenagement)
);

CREATE TABLE pae.photos(
	photo_id SERIAL PRIMARY KEY,
	photo VARCHAR NOT NULL,
	avant_apres boolean NOT NULL,
	visible boolean NOT NULL,
	type_amenagement INTEGER REFERENCES pae.types_amenagements(type_amenagement),
	devis INTEGER REFERENCES pae.devis(devis_id) NOT NULL
);

ALTER TABLE pae.devis ADD photo_preferee INTEGER REFERENCES pae.photos(photo_id);

-- MDP = azerty
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,false,'2020-03-09','yusuf','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'yusuf','yilmaz','Bruxelles','yusuf.yilmaz@student.vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,false,'2020-03-09','soumaya','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'soumaya','izmar','Bruxelles','soumaya.izmar@student.vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,false,'2020-03-09','antoine','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'antoine','visschers','Bruxelles','antoine.visschers@student.vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,false,'2020-03-09','mustapha','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'mustapha','ayadi','Bruxelles','mustapha.ayadi@student.vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,false,'2020-03-09','bruno','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'bruno','loverius','Bruxelles','bruno.loverius@student.vinci.be');

--INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,true,'2020-03-16','boss','$2a$10$PxKZ8cmdyrS/BLWw.Llo9utWaMZowp.2rGKtuB/paZCIepGMCbb.u',null,'boss','big','Bruxelles','big.boss@student.vinci.be');