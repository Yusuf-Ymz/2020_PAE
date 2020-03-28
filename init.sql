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

-- Types aménagements

INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Aménagement de jardin de ville');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Aménagement de jardin');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Aménagement de parc paysagiste');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Création de potagers');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Entretien de vergers haute-tige');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Entretien de vergers basse-tige');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Aménagement d''etang');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Aménagement d''etang');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Installation de système d''arrosage automatique');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Terasses en bois');
INSERT INTO pae.types_amenagements VALUES(DEFAULT,'Terrasses en pierres naturelles');

-- Utilisateurs ouvriers
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,true,'2020-04-03','bri','$2a$10$rIBIrDKcpV0HH1XA8wVq1OBARAD98/cIKViwtSNkDx6Gmjd4XlR7O',NULL,'Lehmann','Brigitte','Wavre','brigitte.lehmann@vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,true,'2020-04-03','lau','$2a$10$OXVqQ8uU81EiXxG.i/kiEeQXNJVD9f2lnIkGSTQCRcji2YVbnsp76',NULL,'Leleux','Laurent','Bruxelles','Laurent.leleux@vinci.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,true,true,'2020-04-03','chri','$2a$10$ThQ6EA/dkawuRyPCpVGvNey4lRAgRVZbD6aTl6Bim.pzHOwPB.F3a',NULL,'Damas','Leleux','Bruxelles','christope.damas@vinci.be');

-- Utilisateurs clients
INSERT INTO pae.utilisateurs VALUES(DEFAULT,false,false,'2020-04-03','achil',NULL,'Ile','Achille','Verviers','ach.ile@gmail.com');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,false,false,'2020-04-03','bazz',NULL,'Ile','Basile','Liège','bas.ile@gmail.be');
INSERT INTO pae.utilisateurs VALUES(DEFAULT,false,false,'2020-04-03','caro',NULL,'Line','Caroline','Stoumont','caro.line@hotmail.com');

-- Clients
INSERT INTO pae.clients VALUES(DEFAULT,'Line','Caroline','Rue de l''eglise ',11,'4987','Stoumont', 'caro.line@hotmail.com', '080.12.56.78');
INSERT INTO pae.clients VALUES(DEFAULT,'Ile','Théophile','Rue de Renkin',7,'4800','Verviers', 'theo.phile@proximus.be', '087.25.69.74');

-- Devis
INSERT INTO pae.devis VALUES(DEFAULT,1,'2019-03-01',4260,5,'Visible','2018-11-12');
INSERT INTO pae.devis VALUES(DEFAULT,1,'2019-03-15',18306,25,'Visible','2018-12-15');
INSERT INTO pae.devis VALUES(DEFAULT,1,'2019-11-12',8540,10,'Commande confirmée','2020-03-30');
INSERT INTO pae.devis VALUES(DEFAULT,2,'2020-01-10',6123,7,'Facture de fin de chantier envoyée','2020-03-02');

-- Travaux
INSERT INTO pae.travaux VALUES(1,6);
INSERT INTO pae.travaux VALUES(2,6);
INSERT INTO pae.travaux VALUES(3,3);

INSERT INTO pae.travaux VALUES(4,1);
INSERT INTO pae.travaux VALUES(4,9);

-- Photos
	-- Basse-tige 
		-- Avant
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,1);

		-- Apres
		INSERT INTO pae.photos(SERIAL,'',true,false,6,1);
		INSERT INTO pae.photos(SERIAL,'',true,false,6,1);
		INSERT INTO pae.photos(SERIAL,'',true,false,6,1);

		-- Visible
		INSERT INTO pae.photos(SERIAL,'',true,true,6,1);
		INSERT INTO pae.photos(SERIAL,'',true,true,6,1);

	-- Haute-tige
		-- Avant
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,2);
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,2);

		-- Apres
		INSERT INTO pae.photos(SERIAL,'',true,false,5,2);
		INSERT INTO pae.photos(SERIAL,'',true,false,5,2);
		INSERT INTO pae.photos(SERIAL,'',true,false,5,2);
		INSERT INTO pae.photos(SERIAL,'',true,false,5,2);

		-- Visible
		INSERT INTO pae.photos(SERIAL,'',true,true,5,2);

	-- Parc-paysagiste
		-- Avant
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,3);
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,3);
		INSERT INTO pae.photos(SERIAL,'',false,false,NULL,3);


