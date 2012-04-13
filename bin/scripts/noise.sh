#!/bin/bash

## Some comments about this

BIN_DIR=..
MACRO_DIR=../macros
MAX_MEM=1024M
FILE=$1

java -Xmx$MAX_MEM -classpath $BIN_DIR/headless.jar:ij.jar ij.ImageJ -batch $MACRO_DIR/noise.ijm $FILE
