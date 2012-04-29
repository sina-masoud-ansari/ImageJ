#!/bin/bash

BIN_DIR=$1
RESULTS_DIR=$2
MAX_MEM=$3
MACRO_DIR=$BIN_DIR/macros
SAMPLE_DIR=build/resources/images/sample


if [ ! -d $SAMPLE_DIR ]; then
	mkdir $SAMPLE_DIR
fi

for i in {1..10}; do
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.SampleImageCreator $((500*$i)) $SAMPLE_DIR
done

for image in $SAMPLE_DIR/*.tif; do
	csv=$(basename $image)
	csv=$csv".csv"
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image 10 > $RESULTS_DIR/$csv
done





