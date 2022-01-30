INSERT INTO MOVIES (name_movie) VALUES
  ('The Fast and the Furious'),
  ('2 Fast 2 Furious'),
  ('The Fast and the Furious: Tokyo Drift'),
  ('Fast & Furious'),
  ('Fast Five'),
  ('Fast & Furious 6'),
  ('Furious 7'),
  ('The Fate of the Furious'),
  ('F9: The Fast Saga');

INSERT INTO MOVIE_SESSION (id_movie,session_time,price,session_date,room) VALUES
  (2,'2022-07-14T17:45:55.9483536', 7.01, PARSEDATETIME('03-february-22','dd-MMMM-yy'),'blue_room'),
  (3,'2022-07-14T17:45:55.9483536', 6.01, PARSEDATETIME('03-february-22','dd-MMMM-yy'),'red_room'),
  (4,'2022-07-14T17:45:55.9483536', 9.01, PARSEDATETIME('03-february-22','dd-MMMM-yy'),'yellow_room'),
  (4,'2022-08-14T17:45:55.9483536', 8.01,PARSEDATETIME('04-february-22','dd-MMMM-yy'),'yellow_room'),
  (5,'2022-09-14T17:45:55.9483536', 9.01,PARSEDATETIME('05-february-22','dd-MMMM-yy'),'yellow_room');

INSERT INTO MOVIE_REVIEW (id_movie,stars,comment) VALUES
  (2,3,'good'),
  (3,6,'bad'),
  (3,1,'silly'),
  (3,9,'smart'),
  (4,4,'Harry Potter');

