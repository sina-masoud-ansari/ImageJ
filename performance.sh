#!/bin/bash

BIN_DIR=$1
MACRO_DIR=$BIN_DIR/macros
MAX_MEM=1024M

for image in resources/test/images/tif/*.tif; do
	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image
done





