CREATE TABLE IF NOT EXISTS Bruker (
  Epost TEXT PRIMARY KEY,
  Passord TEXT NOT NULL,
  FulltNavn TEXT NOT NULL
)

CREATE TABLE IF NOT EXISTS Kaffesmaking (
  KaffesmakingsID INTEGER PRIMARY KEY AUTOINCREMENT,
  SmaksDato DATE NOT NULL DEFAULT CURRENT_TIME,
  Notater TEXT,
  Poeng INTEGER NOT NULL CHECK (Poeng BETWEEN 0 AND 10),
  Epost TEXT NOT NULL,
  KaffeID INTEGER NOT NULL,
  FOREIGN KEY (Epost) REFERENCES Bruker(Epost)
  FOREIGN KEY (KaffeID) REFERENCES Kaffe(KaffeID)
)

CREATE TABLE IF NOT EXISTS Kaffe (
  KaffeID INTEGER PRIMARY KEY AUTOINCREMENT,
  Navn TEXT NOT NULL, 
  Beskrivelse TEXT,
  KiloPris DECIMAL NOT NULL,
  BrenningsGrad TEXT NOT NULL CHECK (BrenningsGrad IN ('moerk', 'middels', 'lys')),
  Dato DATE NOT NULL DEFAULT CURRENT_TIME,
  BrenneriID INTEGER NOT NULL,
  PartiID INTEGER NOT NULL,
  FOREIGN KEY (BrenneriID) REFERENCES Brenneri(BrenneriID)
  FOREIGN KEY (PartiID) REFERENCES Parti(PartiID)
)

CREATE TABLE IF NOT EXISTS Brenneri (
  BrenneriID INTEGER PRIMARY KEY AUTOINCREMENT,
  Navn TEXT NOT NULL
)


CREATE TABLE IF NOT EXISTS ForedlingsMetode (
  MetodeNavn TEXT PRIMARY KEY,
  Beskrivelse TEXT
)

CREATE TABLE IF NOT EXISTS Parti (
  PartiID INTEGER PRIMARY KEY AUTOINCREMENT,
  InnhoestingsAar CHAR(4) NOT NULL DEFAULT (strftime('%Y', 'now')),
  BetaltUSD DECIMAL NOT NULL,
  GaardsID INTEGER NOT NULL,
  MetodeNavn TEXT NOT NULL,
  FOREIGN KEY (GaardsID) REFERENCES Gaard(GaardsID),
  FOREIGN KEY (MetodeNavn) REFERENCES ForedlingsMetode(MetodeNavn)
  -- I ER-diagrammet står det at dette skulle vært en (1,n)-relasjon, men vi var usikker her
  -- CHECK (EXISTS(SELECT 1 FROM inneholder WHERE Parti.PartiID = inneholder.PartiID))
)

CREATE TABLE IF NOT EXISTS Gaard (
  GaardsID INTEGER PRIMARY KEY AUTOINCREMENT,
  Navn TEXT NOT NULL,
  Land TEXT NOT NULL,
  Region TEXT NOT NULL,
  HoeydeOverHavet INTEGER NOT NULL
)

CREATE TABLE IF NOT EXISTS BoenneArt (
  ArtsNavn TEXT PRIMARY KEY CHECK (ArtsNavn IN ('coffea arabica', 'coffea robusta', 'coffea liberica'))
)

INSERT OR IGNORE INTO BoenneArt (ArtsNavn) VALUES ('coffea arabica')

INSERT OR IGNORE INTO BoenneArt (ArtsNavn) VALUES ('coffea robusta')

INSERT OR IGNORE INTO BoenneArt (ArtsNavn) VALUES ('coffea liberica')

CREATE TABLE IF NOT EXISTS dyrker (
  ArtsNavn TEXT NOT NULL,
  GaardsID INTEGER NOT NULL,
  FOREIGN KEY (ArtsNavn) REFERENCES BoenneArt(ArtsNavn),
  FOREIGN KEY (GaardsID) REFERENCES Gaard(GaardsID),
  PRIMARY KEY (ArtsNavn, GaardsID)
)

CREATE TABLE IF NOT EXISTS inneholder (
  ArtsNavn TEXT NOT NULL,
  PartiID INTEGER NOT NULL,
  FOREIGN KEY (ArtsNavn) REFERENCES BoenneArt(ArtsNavn),
  FOREIGN KEY (PartiID) REFERENCES Parti(PartiID),
  PRIMARY KEY (ArtsNavn, PartiID)
)