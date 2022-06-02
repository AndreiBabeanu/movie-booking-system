CREATE DATABASE if NOT EXISTS moviebooking;

USE moviebooking;

CREATE TABLE if NOT EXISTS locations (
    id int NOT NULL AUTO_INCREMENT,
    city VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE if NOT EXISTS cinemas (
    id int NOT NULL AUTO_INCREMENT,
    location_id int NOT NULL,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (location_id) REFERENCES locations (id)
    );

CREATE TABLE if NOT EXISTS auditorium (
    id int NOT NULL AUTO_INCREMENT,
    cinema_id int NOT NULL,
    hall_number int NOT NULL,
    seats int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
    );

CREATE TABLE if NOT EXISTS movies (
    id int NOT NULL AUTO_INCREMENT,
    cinema_id int NOT NULL,
    name varchar(20) NOT NULL,
    duration int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cinema_id) REFERENCES cinemas (id)
    );

CREATE TABLE if NOT EXISTS movies_genres (
    id int not null AUTO_INCREMENT,
    movie_id int NOT NULL ,
    genre varchar(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movies (id)
);

CREATE TABLE if NOT EXISTS screening (
    id int NOT NULL AUTO_INCREMENT,
    movie_id int NOT NULL,
    auditorium_id int NOT NULL,
    start_time TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movies (id),
    FOREIGN KEY (auditorium_id) REFERENCES auditorium (id)
    );

CREATE TABLE if NOT EXISTS clients (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(20) NOT NULL,
    email varchar(40) NOT NULL,
    PRIMARY KEY (id)
    );

CREATE TABLE if NOT EXISTS tickets (
    id int NOT NULL AUTO_INCREMENT,
    screening_id int NOT NULL,
    client_id int NOT NULL,
    is_paid tinyint(1) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (screening_id) REFERENCES screening (id),
    FOREIGN KEY (client_id) REFERENCES clients (id)
    );

# INSERT INTO locations (id, city) VALUES(1, 'Bucuresti');
# INSERT INTO locations (id, city) VALUES(2, 'Pitesti');
# INSERT INTO locations (id, city) VALUES(3, 'Mioveni');
# INSERT INTO locations (id, city) VALUES(4, 'Sibiu');
# INSERT INTO locations (id, city) VALUES(5, 'Brasov');


# INSERT INTO cinemas (id, location_id, name) VALUES(1, 1, 'AFI Cotroceni');
# INSERT INTO cinemas (id, location_id, name) VALUES(2, 2, 'VIVO Pitesti');
# INSERT INTO cinemas (id, location_id, name) VALUES(3, 2, 'Cinema Trivale');
# INSERT INTO cinemas (id, location_id, name) VALUES(4, 4, 'Cinema City Brasov');
# INSERT INTO cinemas (id, location_id, name) VALUES(5, 1, 'Plaza');

# INSERT INTO auditorium (id, cinema_id, hall_number, seats) VALUES (1, 1, 10, 100);
# INSERT INTO auditorium (id, cinema_id, hall_number, seats) VALUES (2, 1, 11, 100);
# INSERT INTO auditorium (id, cinema_id, hall_number, seats) VALUES (3, 2, 10, 100);
# INSERT INTO auditorium (id, cinema_id, hall_number, seats) VALUES (4, 2, 11, 100);
# INSERT INTO auditorium (id, cinema_id, hall_number, seats) VALUES (5, 2, 12, 100);

# INSERT INTO movies (id, cinema_id, name, duration) VALUES (1, 1, 'Spiderman 2', 90);
# INSERT INTO movies (id, cinema_id, name, duration) VALUES (2, 1, 'Superman', 120);
# INSERT INTO movies (id, cinema_id, name, duration) VALUES (3, 2, 'Never back down', 90);
# INSERT INTO movies (id, cinema_id, name, duration) VALUES (4, 2, '365 Days', 120);
# INSERT INTO movies (id, cinema_id, name, duration) VALUES (5, 2, 'Spiderman 3', 90);

# INSERT INTO movies_genres (movie_id, genre) VALUES (1, 'Action');
# INSERT INTO movies_genres (movie_id, genre) VALUES (1, 'Adventure');
# INSERT INTO movies_genres (movie_id, genre) VALUES (1, 'Sci-Fi');
# INSERT INTO movies_genres (movie_id, genre) VALUES (2, 'Action');
# INSERT INTO movies_genres (movie_id, genre) VALUES (2, 'Adventure');
# INSERT INTO movies_genres (movie_id, genre) VALUES (2, 'Sci-Fi');
# INSERT INTO movies_genres (movie_id, genre) VALUES (3, 'Action');
# INSERT INTO movies_genres (movie_id, genre) VALUES (3, 'Drama');
# INSERT INTO movies_genres (movie_id, genre) VALUES (3, 'Sport');
# INSERT INTO movies_genres (movie_id, genre) VALUES (4, 'Drama');
# INSERT INTO movies_genres (movie_id, genre) VALUES (4, 'Romance');
# INSERT INTO movies_genres (movie_id, genre) VALUES (5, 'Action');
# INSERT INTO movies_genres (movie_id, genre) VALUES (5, 'Adventure');
# INSERT INTO movies_genres (movie_id, genre) VALUES (5, 'Sci-Fi');
#
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (1, 1, 1, '2022-06-10 22:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (2, 4, 3, '2022-06-10 21:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (3, 5, 3, '2022-06-11 23:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (4, 2, 2, '2022-06-11 22:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (5, 3, 4, '2022-06-12 22:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (6, 1, 2, '2022-06-20 20:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (7, 4, 4, '2022-06-20 21:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (8, 5, 4, '2022-06-21 23:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (9, 2, 2, '2022-06-20 20:00:00');
# INSERT INTO screening (id, movie_id, auditorium_id, start_time) VALUES (10, 3, 5, '2022-06-20 22:30:00');
#
# INSERT INTO clients (id, name, email) VALUES (1, 'Andrei', 'babeanu.andrei99@gmail.com');
# INSERT INTO clients (id, name, email) VALUES (2, 'Bogdan', 'budi.sound97@gmail.com');
# INSERT INTO clients (id, name, email) VALUES (3, 'Romeo', 'romeo.mihai98@gmail.com');
# INSERT INTO clients (id, name, email) VALUES (4, 'Radu', 'radu.constatin95@gmail.com');
# INSERT INTO clients (id, name, email) VALUES (5, 'Ramona', 'ramona.maria96@gmail.com');
#
# INSERT INTO tickets (id, screening_id, client_id, is_paid) VALUES (1, 1, 1, 1);
# INSERT INTO tickets (id, screening_id, client_id, is_paid) VALUES (2, 2, 2, 0);
# INSERT INTO tickets (id, screening_id, client_id, is_paid) VALUES (3, 3, 3, 0);
# INSERT INTO tickets (id, screening_id, client_id, is_paid) VALUES (4, 4, 4, 1);
# INSERT INTO tickets (id, screening_id, client_id, is_paid) VALUES (5, 5, 5, 0);