#!/bin/bash

RESULTS_DIR=results

cat setup.sql | mysql -u root

for result in $RESULTS_DIR/*.csv; do
	mysql -u root -e "LOAD DATA LOCAL INFILE \"$result\" INTO TABLE performance FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES" imagej
done

mysql -u root -e "SELECT * FROM performance" imagej





