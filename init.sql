DROP SCHEMA IF EXISTS pae CASCADE;
CREATE SCHEMA pae;

-- TABLES
CREATE TABLE pae.clients(
	client_id SERIAL PRIMARY KEY,
	nom VARCHAR NOT NULL,
	prenom VARCHAR NOT NULL,
	adresse VARCHAR NOT NULL,
	code_postal VARCHAR NOT NULL,
	ville VARCHAR NOT NULL,
	email VARCHAR NOT NULL,
	telephone VARCHAR NOT NULL
);

CREATE TABLE pae.utilisateurs(
	utilisateur_id SERIAL PRIMARY KEY,
	confirmer boolean NOT NULL,
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
	etat VARCHAR NOT NULL
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
	avant_apres boolean NOT NULL,
	visible boolean NOT NULL,
	type_amenagement INTEGER REFERENCES pae.types_amenagements(type_amenagement),
	devis INTEGER REFERENCES pae.devis(devis_id) NOT NULL
);

ALTER TABLE pae.devis ADD photo_preferee INTEGER REFERENCES pae.photos(photo_id);



