@echo off
rem Runs the Espresso tests on every running emulator or connected device.
rem
rem   run-tests.bat                  the three conversation scenarios (Android 4)
rem   run-tests.bat all              every test class of the project
rem   run-tests.bat <testName>       one conversation scenario, for example:
rem                                  run-tests.bat sentQuestionIsDisplayedInOwnChat

setlocal
cd /d "%~dp0"

set TEST_CLASS=com.example.foroom.tests.ConversationTests
set REPORT=%~dp0app\build\reports\androidTests\connected\debug\index.html

if "%~1"=="" (
    call "%~dp0gradlew.bat" :app:connectedDebugAndroidTest "-Pandroid.testInstrumentationRunnerArguments.class=%TEST_CLASS%"
) else if /i "%~1"=="all" (
    call "%~dp0gradlew.bat" :app:connectedDebugAndroidTest
) else (
    call "%~dp0gradlew.bat" :app:connectedDebugAndroidTest "-Pandroid.testInstrumentationRunnerArguments.class=%TEST_CLASS%#%~1"
)
set RESULT=%ERRORLEVEL%

echo.
echo Report: %REPORT%
exit /b %RESULT%
