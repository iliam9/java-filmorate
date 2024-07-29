INSERT INTO mpa(mpa_id, mpa_name) values ( 1, 'G' );
INSERT INTO mpa(mpa_id, mpa_name) values ( 2, 'PG' );
INSERT INTO mpa(mpa_id, mpa_name) values ( 3, 'PG-13' );
INSERT INTO mpa(mpa_id, mpa_name) values ( 4, 'R' );
INSERT INTO mpa(mpa_id, mpa_name) values ( 5, 'NC-17' );
INSERT INTO genres(genre_id, genre_name) values ( 1, 'Комедия' );
INSERT INTO genres(genre_id, genre_name) values ( 2, 'Драма' );
INSERT INTO genres(genre_id, genre_name) values ( 3, 'Мультфильм' );
INSERT INTO genres(genre_id, genre_name) values ( 4, 'Триллер' );
INSERT INTO genres(genre_id, genre_name) values ( 5, 'Документальный' );
INSERT INTO genres(genre_id, genre_name) values ( 6, 'Боевик' );
INSERT INTO users(name, login, email, birthday)
VALUES('iii', 'iii', 'iii@mail.ru', '1993-26-08'),
      ('qqq', 'qqq', 'qqq@mail.ru', '1992-11-03'),
      ('www', 'www', 'www@mail.ru', '1994-11-04'),
      ('eee', 'eee', 'eee@mail.ru', '1990-06-10');
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('qqq', 'description1', '2001-11-22', '120', 1);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('www', 'description2', '2003-11-16', '160', 2);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('eee', 'description3', '2006-09-15', '180', 3);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('rrr', 'description4', '2010-11-06', '190', 4);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('ttt', 'description5', '2006-11-12', '180', 5);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('yyy', 'description6', '2006-11-24', '160', 5);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('uuu', 'description7', '2011-10-07', '194', 5);
INSERT INTO film_genres(film_id, genre_id) VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 5), (7, 2);
INSERT INTO friends(user_id, friend_user_id) VALUES (1, 2), (1, 3), (2, 1), (2, 4), (3, 1), (3, 4), (4, 1);
