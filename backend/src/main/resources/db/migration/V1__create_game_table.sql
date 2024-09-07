CREATE TABLE GAME (
                      ID INTEGER PRIMARY KEY,
                      GAME_TITLE TEXT NOT NULL,
                      RELEASE_DATE TEXT,
                      PLATFORM INTEGER,
                      REGION_ID INTEGER,
                      COUNTRY_ID INTEGER,
                      OVERVIEW TEXT,
                      YOUTUBE TEXT,
                      PLAYERS INTEGER,
                      COOP TEXT,
                      RATING TEXT
);

CREATE VIRTUAL TABLE FUZZY_TITLE USING fts5(TITLE);