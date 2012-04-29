#!/bin/bash

BIN_DIR=$1
RESULTS_DIR=$2
MAX_MEM=$3
MACRO_DIR=$BIN_DIR/macros

for image in resources/test/images/tif/*.tif; do
	csv=$(basename $image)
	csv=$csv".csv"
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image 10 > $RESULTS_DIR/$csv
done





