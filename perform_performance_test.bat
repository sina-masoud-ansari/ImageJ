set image = %1
echo %1
set BIN_DIR = %2
set MAX_MEM = %3
set RESULTS_DIR = %4
set SAMPLE_DIR = %5
set /a ITER = 10

set	csv1=%~n1
set	csv2=%csv1%.csv
java -Xmx%MAX_MEM% -classpath %BIN_DIR%/ij.jar ij.parallel.ParallelPerformanceTest %SAMPLE_DIR%\%1 %ITER% >> %RESULTS_DIR%/%csv2%