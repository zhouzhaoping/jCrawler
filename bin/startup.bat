@echo off

set PROGRAMN_HOME=%cd%

echo %PROGRAMN_HOME%

set CLASSPATH=%CLASSPATH%;%PROGRAMN_HOME%\jCrawler.jar

java jCrawler.main.Main