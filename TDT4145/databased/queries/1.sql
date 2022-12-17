INSERT INTO Bruker ('Epost', 'Passord', 'FulltNavn')
VALUES ('elsker.vinterkaffe@email.com', 'superhemmelig123', 'Ola Nordmann');

INSERT INTO Brenneri ('BrenneriID', 'Navn')
VALUES (1, 'Jacobsen & Svart');

INSERT INTO Gaard ('GaardsID', 'Navn', 'HoeydeOverHavet', 'Region', 'Land')
VALUES (1, 'Nombre de Dios', 1500, 'Santa Ana', 'El Salvador');

INSERT INTO Kaffe ('KaffeID', 'Navn', 'Brenningsgrad', 'Beskrivelse', 'KiloPris', 'Dato', 'BrenneriID', 'PartiID')
VALUES (1, 'Vinterkaffe 2022', 'lys', 'En velsmakende og kompleks kaffe for mørketiden', '600', '2022-01-20', 1, 1);

INSERT INTO Kaffesmaking ('Notater', 'Poeng', 'Epost', 'KaffeID')
VALUES ('Wow – en odyssé for smaksløkene: sitrusskall, melkesjokolade, aprikos!', 10, 'elsker.vinterkaffe@email.com', 1);

INSERT INTO Parti ('PartiID', 'InnhoestingsAar', 'BetaltUSD', 'GaardsID', 'MetodeNavn')
VALUES (1, '2021', 8, 1, 'bærtørket Bourbon');

INSERT INTO ForedlingsMetode ('MetodeNavn')
VALUES ('bærtørket Bourbon');

INSERT INTO inneholder ('ArtsNavn', 'PartiID')
VALUES ('coffea arabica', 1);