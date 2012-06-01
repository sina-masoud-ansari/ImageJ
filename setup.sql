CREATE DATABASE IF NOT EXISTS imagej;
USE imagej;
DROP TABLE IF EXISTS performance;

CREATE TABLE performance (
  fname VARCHAR(100),
  nchannels INT,
  bitdepth INT,
  npixels INT,
  threads INT,
  setup VARCHAR(50), 
  filter VARCHAR(100),
  method VARCHAR(20),
  stage VARCHAR(20),
  time INT 
);
