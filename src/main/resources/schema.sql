DROP TABLE IF EXISTS AlbumGenreRelation;
DROP TABLE IF EXISTS Genre;
DROP TABLE IF EXISTS Album;
DROP TABLE IF EXISTS Artist;
  
CREATE TABLE Artist (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(256) NOT NULL
);

CREATE TABLE Album (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  year_of_release INT NOT NULL,
  artist_id BIGINT NOT NULL,
  CONSTRAINT FK_ARTIST FOREIGN KEY (artist_id) REFERENCES Artist(id)
);


CREATE TABLE Genre (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(256) NOT NULL,
  CONSTRAINT UK_GENRE_NAME UNIQUE KEY (name)
);

CREATE TABLE Album_Genre_Relation (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  album_id INT NOT NULL,
  genre_id INT NOT NULL,
   CONSTRAINT FK_ALBUM FOREIGN KEY (album_id) REFERENCES Album(id),
   CONSTRAINT FK_GENRE FOREIGN KEY (genre_id) REFERENCES Genre(id)
);


