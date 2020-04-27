insert into Artist(name) values('Michael Jackson');
insert into Artist(name) values('Justin Bieber');
insert into Artist(name) values('Elvis Presley');

insert into Album(name,year_of_release,artist_id) values ('Immortal',1997,1);
insert into Album(name,year_of_release,artist_id) values ('This Is It',1992,1);
insert into Album(name,year_of_release,artist_id) values ('My World',2009,2);
insert into Album(name,year_of_release,artist_id) values ('Loving You',1996,3);


insert into Genre(name) values ('Pop');
insert into Genre(name) values ('Dance-Pop');
insert into Genre(name) values ('Electronic');
insert into Genre(name) values ('Funk / Soul');
insert into Genre(name) values ('Stage & Screen');
insert into Genre(name) values ('Hip Hop');
insert into Genre(name) values ('Rock');
insert into Genre(name) values ('Non-Music');

insert into Album_Genre_Relation(album_id,genre_id) values(1,1);
insert into Album_Genre_Relation(album_id,genre_id) values(1,3);
insert into Album_Genre_Relation(album_id,genre_id) values(1,4);
insert into Album_Genre_Relation(album_id,genre_id) values(1,5);
insert into Album_Genre_Relation(album_id,genre_id) values(2,6);
insert into Album_Genre_Relation(album_id,genre_id) values(2,7);
insert into Album_Genre_Relation(album_id,genre_id) values(2,8);
insert into Album_Genre_Relation(album_id,genre_id) values(2,1);
insert into Album_Genre_Relation(album_id,genre_id) values(3,1);
insert into Album_Genre_Relation(album_id,genre_id) values(3,2);
insert into Album_Genre_Relation(album_id,genre_id) values(4,1);
insert into Album_Genre_Relation(album_id,genre_id) values(4,5);
insert into Album_Genre_Relation(album_id,genre_id) values(4,7);

