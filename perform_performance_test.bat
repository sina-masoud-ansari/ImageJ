set image = %1
set BIN_DIR = %2
set MAX_MEM = %3
set RESULTS_DIR = %4

set	csv=basename %1
set	csv=%csv%".csv"
set	java -Xmx%MAX_MEM% -classpath %BIN_DIR%/ij.jar ij.parallel.ParallelPerformanceTest %1 10 > %RESULTS_DIR%/%csv%