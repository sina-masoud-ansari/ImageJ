#!/bin/bash

BIN_DIR=$1
MACRO_DIR=$BIN_DIR/macros
MAX_MEM=1024M

for image in resources/test/images/tif/*.tif; do
	echo $image
	#time -p java -Xmx$MAX_MEM -classpath $BIN_DIR/headless.jar:$BIN_DIR/ij.jar ij.ImageJ -batch $MACRO_DIR/noise.ijm $image
	time -p java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image
done





