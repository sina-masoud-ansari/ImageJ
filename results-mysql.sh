#!/bin/bash

RESULTS_DIR=$PWD/results
QUERIES_DIR=$RESULTS_DIR/queries

cat setup.sql | mysql -u root

for result in $RESULTS_DIR/*.csv; do
	mysql -u root -e "LOAD DATA LOCAL INFILE \"$result\" INTO TABLE performance FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES" imagej
done

# Clear existing query results and make queries dir

if [ -e $QUERIES_DIR ]; then
	rm -r $QUERIES_DIR/*.csv
else
	mkdir $QUERIES_DIR
fi

chmod 777 $QUERIES_DIR

### QUERIES ###

# All results
Q_FILE=$QUERIES_DIR/q_all.csv
mysql -u root -e "SELECT 'File','Processors' UNION (SELECT fname, proc INTO OUTFILE \"$Q_FILE\" FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM performance)" imagej
#mysql -u root -e "SELECT fname, proc INTO OUTFILE \"$Q_FILE\" FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM performance ORDER BY proc DESC" imagej

# COLOR_RGB INDEP P_NONE variation with number of processors
Q_FILE=$QUERIES_DIR/q_rgb_indep_pnone.csv
mysql -u root -e "SELECT * INTO OUTFILE \"$Q_FILE\" FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM performance WHERE itype='COLOR_RGB' AND ptype='P_NONE' AND series='INDEP'" imagej

# COLOR_RGB INDEP P_SERIAL variation with number of processors
Q_FILE=$QUERIES_DIR/q_rgb_indep_pserial.csv
mysql -u root -e "SELECT * INTO OUTFILE \"$Q_FILE\" FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM performance WHERE itype='COLOR_RGB' AND ptype='P_SERIAL' AND series='INDEP'" imagej

# COLOR_RGB INDEP P_SIMPLE variation with number of processors
Q_FILE=$QUERIES_DIR/q_rgb_indep_psimple.csv
mysql -u root -e "SELECT * INTO OUTFILE \"$Q_FILE\" FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' FROM performance WHERE itype='COLOR_RGB' AND ptype='P_SIMPLE' AND series='INDEP'" imagej

# For a large problem, how does the Add Noise filter perform with different approaches and with different different number of cores available?




cat $Q_FILE




