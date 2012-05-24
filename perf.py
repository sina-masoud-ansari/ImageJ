#!/usr/bin/python

######################################################################
# Test ImageJ performance and produce summary statistics in CSV format
#
######################################################################

import sys
import multiprocessing
from subprocess import call

### Show debug output?
debug=True

### Determine number of avilable processors
cpus=multiprocessing.cpu_count()

### List available Filters
filters = ['Add Noise', 'Shadows', 'Salt and Pepper']

### List the parallel processing options
ptype = ['NONE', 'SERIAL', 'SIMPLE' ]

### List possible setups
setups = ['INDEPENDENT', 'DEPENDENT']

### List processing stages to time
stages = ['SETUP', 'RUN']

### Arguments passed through from Ant

def usage() :
	print """
USAGE:

perf.py file iterations java_cmd

"""

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
java_cmd = "java -Xmx{0} -Xbootclasspath/p:{1} -classpath {2}/ij.jar ij.parallel.SinglePerformanceTest".format(max_mem, xboot, bin_dir)

if debug :
	print "ImageJ performance testing\nPython version: %s" % sys.version
	print "Using file '%s' with %s iterations" % (file, iter)
	print "Number of CPU cores: %d" % cpus

for f in filters :
	for s in setups :
		for p in ptype 	:
			for c in range(1, cpus+1) :
				for t in stages :
					if s == "DEPENDENT" :
						print '{0} {1} "{2}" {3} {4} {5} {6} {7}'.format(java_cmd, file, f, s, p, c, t, iter)					
   					else :
		                                for i in range(1, int(iter)+1) :
        		                                print '{0} {1} "{2}" {3} {4} {5} {6}'.format(java_cmd, file, f, s, p, c, t)
                		                        #call([java_cmd, file, s, p, c, t])
