CREATE DATABASE IF NOT EXISTS imagej;
USE imagej;
DROP TABLE IF EXISTS performance;

CREATE TABLE performance (
  fname VARCHAR(50),
  itype VARCHAR(20),
  npixels INT,
  proc INT, 
  filter VARCHAR(100),
  series VARCHAR(20),
  run FLOAT, 
  setup FLOAT,
  ptype VARCHAR(20)
);
