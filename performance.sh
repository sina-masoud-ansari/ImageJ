#!/bin/bash

BIN_DIR=$1
RESULTS_DIR=$2
MAX_MEM=$3
SAMPLE_DIR=$4
NUM_SAMPLES=$5


if [ ! -d $SAMPLE_DIR ]; then
	mkdir $SAMPLE_DIR
fi

for (( i=1; i<=$NUM_SAMPLES; i++ )); do
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar:$BIN_DIR/jai_codec.jar:$BIN_DIR/jai_core.jar ij.parallel.SampleImageCreator $((1000*$i)) $SAMPLE_DIR
done

for image in $SAMPLE_DIR/*.tif; do
	csv=$(basename $image)
	csv=$csv".csv"
	echo "Processing $image @ $(date) ..."
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image 10 > $RESULTS_DIR/$csv
done





