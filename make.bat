if not exist "..\SAM_realese_%date%\" mkdir ..\SAM_realese_%date%
if not exist "..\SAM_realese_%date%\SAM-System" mkdir ..\SAM_realese_%date%\SAM-System
if not exist "..\SAM_realese_%date%\pitest" mkdir ..\SAM_realese_%date%\pitest
if not exist "..\SAM_realese_%date%\pitest-html" mkdir ..\SAM_realese_%date%\pitest-html

xcopy /E .\SAM-System\target\classes ..\SAM_realese_%date%\SAM-System
xcopy /E .\pitest\target\classes ..\SAM_realese_%date%\pitest
xcopy /E .\pitest-html\target\classes ..\SAM_realese_%date%\pitest-html

xcopy  .\run.bat ..\SAM_realese_%date%\

