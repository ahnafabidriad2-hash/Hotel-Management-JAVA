@echo off
cd /d "%~dp0"
javac -d out src\*.java || (pause & exit /b 1)
java -cp out Start
