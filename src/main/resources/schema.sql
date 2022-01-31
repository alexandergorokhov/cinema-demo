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

CREATE TABLE IF NOT EXISTS MOVIE_REVIEW (
  id_review  LONG PRIMARY KEY,
  id_movie  LONG NOT NULL,
  stars  NUMERIC(1),
  comment VARCHAR(200),
  CONSTRAINT `movie_id_on_movie_review` FOREIGN KEY (`id_movie`) REFERENCES `MOVIES` (`id_movie`)
  );

CREATE TABLE IF NOT EXISTS EXTERNAL_MOVIE_DESCRIPTION_STORAGE (
  id_external_description  LONG PRIMARY KEY,
  id_movie  LONG NOT NULL,
  id_external  VARCHAR(100),
  name_external_provider VARCHAR(50),
  CONSTRAINT `movie_id_on_external_movie_description_storage` FOREIGN KEY (`id_movie`) REFERENCES `MOVIES` (`id_movie`)
  );