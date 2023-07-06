USE HERO_DB;

-- Heroes
INSERT INTO Hero (heroName, heroDescription, villain, heroImage)
VALUES 
('Superman', 'The Man of Steel', FALSE, NULL),
('Batman', 'The Dark Knight', FALSE, NULL),
('Wonder Woman', 'The Amazon Princess', FALSE, NULL),
('Spider-Man', 'Friendly neighborhood Spider-Man', FALSE, NULL),
('Captain America', 'Star-Spangled Man', FALSE, NULL),
('Joker', 'Clown Prince of Crime', TRUE, NULL),
('Green Goblin', 'Insane industrialist', TRUE, NULL),
('Lex Luthor', 'Criminal mastermind', TRUE, NULL),
('Doctor Octopus', 'Scientist with mechanical tentacles', TRUE, NULL),
('Red Skull', 'Nazi super-soldier', TRUE, NULL);

-- Locations
INSERT INTO Location (locationName, locationDescription, locationAddress, locationLatitude, locationLongitude)
VALUES 
('New York', 'The Big Apple', 'New York, NY', '40.7128', '74.0060'),
('Chicago', 'Windy City', 'Chicago, IL', '41.8781', '87.6298'),
('Atlanta', 'The city in a forest', 'Atlanta, GA', '33.7490', '84.3880'),
('Miami', 'The Magic City', 'Miami, FL', '25.7617', '80.1918'),
('Washington DC', 'The Capital', 'Washington, DC', '38.9072', '77.0369'),
('Philadelphia', 'The City of Brotherly Love', 'Philadelphia, PA', '39.9526', '75.1652'),
('Boston', 'Beantown', 'Boston, MA', '42.3601', '71.0589'),
('Detroit', 'Motor City', 'Detroit, MI', '42.3314', '83.0458');

-- Sightings
INSERT INTO Sighting (sightingDate, heroID, locationID)
VALUES 
('2023-01-01', 1, 1),
('2023-01-02', 2, 2),
('2023-01-03', 3, 3),
('2023-01-04', 4, 4),
('2023-01-05', 5, 5),
('2023-01-06', 6, 6),
('2023-01-07', 7, 7),
('2023-01-08', 8, 8),
('2023-01-09', 9, 1),
('2023-01-10', 10, 2),
('2023-01-11', 1, 3),
('2023-01-12', 2, 4),
('2023-01-13', 3, 5),
('2023-01-14', 4, 6),
('2023-01-15', 5, 7);

-- Organizations
INSERT INTO Organization (orgName, orgDescription, orgPhone, orgEmail, orgOfVillains, orgAddress)
VALUES 
('Justice League', 'Protectors of Earth', '123-456-7890', 'justice@league.com', FALSE, 'Secret Location'),
('Avengers', 'Earth\'s Mightiest Heroes', '098-765-4321', 'avengers@heroes.com', FALSE, 'Stark Tower, NY'),
('Legion of Doom', 'Supervillain team', '555-666-7777', 'legion@doom.com', TRUE, 'Swamp, Unknown');

-- Superpowers
INSERT INTO Superpower (superpowerName, superpowerDescription)
VALUES
('Super Strength', 'Ability to lift, carry, and apply force beyond human norms'),
('Flight', 'Ability to defy gravity and fly'),
('Invisibility', 'Ability to render oneself unseen to the naked eye'),
('Telepathy', 'Ability to read thoughts and mental contents'),
('Telekinesis', 'Ability to manipulate and control objects with the mind'),
('Time Travel', 'Ability to travel in time'),
('Immortality', 'Ability to live forever, or for a very long time');

-- Members
INSERT INTO Members (heroID, orgID)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 3),
(7, 3),
(8, 3),
(9, 3),
(10, 3);

-- HeroSuperpowers
INSERT INTO HeroSuperpowers (heroID, superpowerID)
VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 1),
(3, 2),
(4, 1),
(5, 1),
(5, 2),
(6, 3),
(7, 5),
(8, 4),
(9, 5),
(10, 7);
