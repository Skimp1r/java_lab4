@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-27"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd /d "%~dp0"
if not exist out mkdir out
javac -encoding UTF-8 -d out src\main\java\lab4\shapes\ShapesApp.java
if errorlevel 1 (
  echo Compile failed
  pause
  exit /b 1
)
java -cp out lab4.shapes.ShapesApp
if errorlevel 1 pause
