DROP DATABASE IF EXISTS HERO_test_DB;
CREATE DATABASE HERO_test_DB;

USE HERO_test_DB;

CREATE TABLE Hero (
	heroID INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR (50) NOT NULL,
    heroDescription VARCHAR(255) NOT NULL,
    villain BOOLEAN Default false,
    heroImage MEDIUMBLOB
);

CREATE TABLE Superpower (
	superpowerID INT PRIMARY KEY AUTO_INCREMENT,
    superpowerName VARCHAR (50) NOT NULL,
    superpowerDescription VARCHAR(255) NOT NULL
);

CREATE TABLE Location (
	locationID INT PRIMARY KEY AUTO_INCREMENT,
    locationName VARCHAR(50) NOT NULL,
    locationDescription VARCHAR(255) NOT NULL,
    locationAddress VARCHAR(255) NOT NULL,
    locationLatitude VARCHAR(15) NOT NULL,
    locationLongitude VARCHAR(15) NOT NULL
);

CREATE TABLE Organization (
	orgID INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescription VARCHAR(255) NOT NULL,
    orgPhone CHAR(12),
    orgEmail VARCHAR(50),
    orgOfVillains BOOLEAN Default false,
    orgAddress VARCHAR(255)
);

CREATE TABLE Sighting (
	sightingID INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate DATE NOT NULL, 
    heroID INT NOT NULL,
    locationID INT NOT NULL, 
    FOREIGN KEY (locationID) REFERENCES Location(locationID),
    FOREIGN KEY (heroID) REFERENCES Hero(heroID)
);

CREATE TABLE Members (
    heroID INT NOT NULL,
    orgID INT NOT NULL, 
    FOREIGN KEY (orgID) REFERENCES Organization(orgID),
    FOREIGN KEY (heroID) REFERENCES Hero(heroID)
);

CREATE TABLE HeroSuperpowers (
    heroID INT NOT NULL,
    superpowerID INT NOT NULL, 
    FOREIGN KEY (superpowerID) REFERENCES Superpower(superpowerID),
    FOREIGN KEY (heroID) REFERENCES Hero(heroID)
);