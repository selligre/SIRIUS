DROP TABLE IF EXISTS district;

CREATE TABLE IF NOT EXISTS district (
    id SERIAL PRIMARY KEY,
    population_percentile REAL NOT NULL,
    name VARCHAR(255)
    );

INSERT INTO TABLE district ('population_percentile', 'name') VALUES (5, "Bleuets - Bordieres - Buttes - Halage - Pinsons");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (6, "Cote d'Or - Sarrazins-Habette - Coteaux du Sud");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (10, "Breche - Croix des Meches - Haye aux Moines - Levriere - Prefecture");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (14, "Front de Lac - Ormetteau - Port");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (16, "Gizeh - Montaigut - Palais");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (7, "Centre Ancien - Chenevier - Demenitroux");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (12, "Echat - Champeval");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (3, "Mont-Mesly");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (18, "Bords de Marne - Val de Brie");
INSERT INTO TABLE district ('population_percentile', 'name') VALUES (9, "La Source - Pointe du Lac");