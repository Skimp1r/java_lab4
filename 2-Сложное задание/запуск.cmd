@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-27"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd /d "%~dp0"

if not exist "lib\jfreechart-1.5.6.jar" (
  echo File not found: lib\jfreechart-1.5.6.jar
  pause
  exit /b 1
)

if not exist out mkdir out
javac -encoding UTF-8 -cp "lib\jfreechart-1.5.6.jar" -d out src\main\java\lab4\histogram\HistogramApp.java
if errorlevel 1 (
  echo Compile failed
  pause
  exit /b 1
)

java -cp "out;lib\jfreechart-1.5.6.jar" lab4.histogram.HistogramApp
if errorlevel 1 pause
