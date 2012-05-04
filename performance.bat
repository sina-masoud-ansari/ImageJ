set BIN_DIR=%1
set RESULTS_DIR=%2
set MAX_MEM=%3
set MACRO_DIR=%BIN_DIR%\macros
set SAMPLE_DIR=%BIN_DIR%\images

IF NOT EXIST "%SAMPLE_DIR%" ( mkdir %SAMPLE_DIR% )

FOR /L %%A IN (1,1,10) DO CALL create_image.bat %%A %BIN_DIR% %MAX_MEM% %SAMPLE_DIR% 

FOR /F %%B IN ( 'dir %SAMPLE_DIR%\*.tif /B' ) DO CALL perform_performance_test.bat %%B %BIN_DIR% %MAX_MEM% %RESULTS_DIR% %SAMPLE_DIR%

