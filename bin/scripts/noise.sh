#!/bin/bash

## Some comments about this

BIN_DIR=~/ImageJ/bin
MACRO_DIR=~/ImageJ/bin/macros
MAX_MEM=1024M
FILE=$1


java -Xmx$MAX_MEM -classpath $BIN_DIR/headless.jar:$BIN_DIR/ij.jar ij.ImageJ -batch $MACRO_DIR/noise.ijm $FILE
