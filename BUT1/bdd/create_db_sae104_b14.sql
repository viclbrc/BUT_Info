DROP TABLE IF EXISTS client;
CREATE TABLE client(
    id_client INT PRIMARY KEY,
    nom_cli VARCHAR(50) NOT NULL,
    prenom_cli VARCHAR(50) NOT NULL,
    adresse_cli1 VARCHAR(100) NOT NULL,
    adresse_cli2 VARCHAR(100),
    adresse_cli3 VARCHAR(100),
    code_postal_cli VARCHAR(5) NOT NULL,
    ville_cli VARCHAR(56) NOT NULL,
    telephone_cli VARCHAR(15) NOT NULL,
    mail_cli VARCHAR(100) NOT NULL
)

DROP TABLE IF EXISTS compteur;
CREATE TABLE compteur(
    pdl_prm VARCHAR(14) PRIMARY KEY,
    adresse1 VARCHAR(100) NOT NULL,
    adresse2 VARCHAR(100),
    adresse3 VARCHAR(100),
    code_postal VARCHAR(5) NOT NULL,
    ville VARCHAR(56) NOT NULL,
    date_hiver_ete DATE,
    date_ete_hiver DATE
)

DROP TABLE IF EXISTS contrat;
CREATE TABLE contrat(
    no_contrat VARCHAR(20) PRIMARY KEY,
    date_debut_contrat DATE NOT NULL
)

DROP TABLE IF EXISTS date;
CREATE TABLE date(
    date DATE PRIMARY KEY
)

DROP TABLE IF EXISTS tarif_abonnement;
CREATE TABLE tarif_abonnement(
    prix_abo_ht FLOAT,
    prix_abo_ttc FLOAT
)

DROP TABLE IF EXISTS formule_tarifaire;
CREATE TABLE formule_tarifaire(
    id_formule INT PRIMARY KEY,
    lib_formule VARCHAR(50) NOT NULL,
    descriptif_formule VARCHAR(255) NOT NULL,
    date_commercialisation DATE NOT NULL
)