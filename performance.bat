set BIN_DIR=%1
set RESULTS_DIR=%2
set MAX_MEM=%3
set MACRO_DIR=%BIN_DIR%/macros
set SAMPLE_DIR=build/resources/images/sample

IF NOT EXIST %SAMPLE_DIR% GOTO MAKE_DIR

:MAKE_DIR
	mkdir %SAMPLE_DIR%

FOR /L %%A IN (1,1,10) DO CALL create_image.bat %%A %BIN_DIR% %MAX_MEM% %SAMPLE_DIR%

FOR /F %%B IN (%SAMPLE_DIR%/) DO CALL perform_performance_test.bat %%B %BIN_DIR% %MAX_MEM% %RESULTS_DIR%
