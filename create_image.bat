set iteration = %1
set BIN_DIR = %2
set MAX_MEM = %3
set SAMPLE_DIR = %4

set /a size = 500*%1
java -Xmx%MAX_MEM% -classpath %SAMPLE_DIR%;%BIN_DIR%/ij.jar -jar ./build/macros/jai_codec.jar ij.parallel.SampleImageCreator %size% %SAMPLE_DIR%