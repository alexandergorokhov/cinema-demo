CREATE TABLE IF NOT EXISTS MOVIES (
  id_movie  LONG PRIMARY KEY,
  name_movie VARCHAR(64) NOT NULL);

CREATE TABLE IF NOT EXISTS MOVIE_SESSION (
  id_session  LONG PRIMARY KEY,
  id_movie  LONG NOT NULL,
  session_time  VARCHAR(64),
  price NUMERIC (8,2),
  session_date DATE,
  CONSTRAINT `movie_id_on_movie_session` FOREIGN KEY (`id_movie`) REFERENCES `MOVIES` (`id_movie`)
  );