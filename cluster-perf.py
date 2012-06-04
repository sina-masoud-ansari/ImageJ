#!/usr/bin/python

######################################################################
# Test ImageJ performance and produce summary statistics in CSV format
#
######################################################################

import sys
import multiprocessing
from subprocess import call

### Show debug output?
debug=False

### Determine number of avilable processors
#cpus=multiprocessing.cpu_count()
cpus=12

### List available Filters
filters = ['Add Noise', 'Shadows', 'Salt and Pepper']

### List the parallel processing options
ptype = ['P_NONE', 'P_SERIAL', 'P_SIMPLE', 'P_EXECUTOR', 'P_PARATASK', 'P_FORK_JOIN']

### List possible setups
setups = ['INDEPENDENT', 'DEPENDENT']

### List processing stages to time
stages = ['SETUP', 'RUN']

### Arguments passed through from Ant

def usage() :
	
	print "Incorrect number of arguments: %d" % len(sys.argv)
	print """
USAGE:

perf.py file iterations bin_dir max_mem xboot

"""

def submitJob(cmd, cores) :
	# Create the LL job file
	jobdesc =  """
#!/bin/bash

#@ job_name = ImageJ
#@ job_type = parallel
#@ total_tasks = %d
#@ blocking = %d
#@ class = default
#@ group = chemistry
#@ account_no = uoa
#@ initialdir = $(home)/ImageJ
#@ node_resources = ConsumableMemory(%sb) ConsumableVirtualMemory(%sb)
#@ queue

%s >> ~/RESULTS.csv

""" % (cores, cores, max_mem, max_mem, cmd)	

	f = open('job.ll', 'w')
	f.write(jobdesc)
	f.close()
#	print "%s" % jobdesc
	
	submit = "llsubmit job.ll"
#	sys.exit(0)
	call(submit, shell=True)


### Check correct usage
if len(sys.argv) != 6 :
	print usage()
	sys.exit(0)

### The image file
file = sys.argv[1]

### The number of iterations to run
iter = sys.argv[2]

### Arguments passed through from Ant to create the Java command
bin_dir = sys.argv[3]
max_mem = sys.argv[4]
xboot = sys.argv[5]

### Setup the basic command to run
java_cmd = "java -Xmx{0} -Xbootclasspath/p:{1} -classpath {2}/ij.jar ij.parallel.ParallelPerformanceTest".format(max_mem, xboot, bin_dir)

if debug :
	print "ImageJ performance testing\nPython version: %s" % sys.version
	print "Using file '%s' with %s iterations" % (file, iter)
	print "Number of CPU cores: %d" % cpus
	print "FileName, NumChannels, BitDepth, TotalPixels, Threads, Setup, Filter, Stage, TimeTaken"

for f in filters :
	for s in setups :
		for p in ptype 	:
			for c in range(1, cpus+1) :
				for t in stages :
					if s == "DEPENDENT" :
						cmd = '{0} {1} "{2}" {3} {4} {5} {6} {7}'.format(java_cmd, file, f, s, p, c, t, iter)
						submitJob(cmd, c)
						#call(cmd, shell=True)
  					else :
		                                for i in range(1, int(iter)+1) :
        		                                cmd = '{0} {1} "{2}" {3} {4} {5} {6}'.format(java_cmd, file, f, s, p, c, t)
							submitJob(cmd, c)
							#call(cmd, shell=True)
													
