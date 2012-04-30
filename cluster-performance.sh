#!/bin/bash

BIN_DIR=$1
RESULTS_DIR=$2
MAX_MEM=$3
SAMPLE_DIR=$4
NUM_SAMPLES=$5

### Make samples

#if [ ! -d $SAMPLE_DIR ]; then
#	mkdir $SAMPLE_DIR
#fi

#for (( i=1; i<=$NUM_SAMPLES; i++ )); do
#	java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar:$BIN_DIR/jai_codec.jar:$BIN_DIR/jai_core.jar ij.parallel.SampleImageCreator $((1000*$i)) $SAMPLE_DIR
#done

## Create and run jobs

JOBFILE=job.ll
DIR=$HOME/ImageJ


for image in $SAMPLE_DIR/*.tif; do
	csv=$(basename $image)
	csv=$csv".csv"
	#echo "Processing $image @ $(date) ..."
        
        echo "#!/bin/bash" > $JOBFILE
        
        echo "#@ shell = /bin/bash" >> $JOBFILE
        echo "#@ job_name = ${csv}" >> $JOBFILE
        echo "#@ notify_user = s.ansari@auckland.ac.nz" >> $JOBFILE
        echo "#@ notification = always" >> $JOBFILE
        echo "#@ class = default" >> $JOBFILE
        echo "#@ group = nesi" >> $JOBFILE
        echo "#@ account_no = /nz/nesi" >> $JOBFILE
        echo "#@ wall_clock_limit = 10:00:00" >> $JOBFILE
        echo "#@ resources = ConsumableMemory(${MAX_MEM}b) ConsumableVirtualMemory(${MAX_MEM}b)" >> $JOBFILE
        echo "#@ job_type = serial" >> $JOBFILE
        echo "#@ initialdir = $DIR" >> $JOBFILE
        echo "#@ output = $DIR/\$(job_name).\$(jobid).out" >> $JOBFILE
        echo "#@ error = $DIR/\$(job_name).\$(jobid).err" >> $JOBFILE
        echo "#@ queue" >> $JOBFILE
        
        echo "cd $HOME/ImageJ" >> $JOBFILE
        echo "java -Xmx$MAX_MEM -classpath $BIN_DIR/ij.jar ij.parallel.ParallelPerformanceTest $image 10 > $RESULTS_DIR/$csv" >> $JOBFILE
        
        llsubmit $JOBFILE
        
done

rm $JOBFILE



