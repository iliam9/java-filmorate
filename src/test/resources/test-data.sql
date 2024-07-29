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
VALUES('qqq', 'qqq', 'qqq@mail.ru', '1993-03-12'),
      ('www', 'www', 'www@mail.ru', '1994-02-02'),
      ('eee', 'eee', 'eee@mail.ru', '1992-12-01'),
      ('rrr', 'rrr', 'rrr@mail.ru', '1990-05-24');
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('qqq', 'description1', '2001-11-22', '121', 1);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('www', 'description2', '2002-11-14', '174', 2);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('eee', 'description3', '2003-11-15', '180', 3);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('rrr', 'description4', '2004-11-06', '167', 4);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('ttt', 'description5', '2005-11-12', '188', 5);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('yyy', 'description6', '2006-11-24', '167', 5);
INSERT INTO films(name, description, release_date, duration, mpa_id)
VALUES ('uuu', 'description7', '2010-11-07', '198', 5);
INSERT INTO film_genres(film_id, genre_id) VALUES (1, 1), (2, 2), (3, 3), (4, 4), (5, 5), (6, 5), (7, 2);
INSERT INTO friends(user_id, friend_user_id) VALUES (1, 2), (1, 3), (2, 1), (2, 4), (3, 1), (3, 4), (4, 1);