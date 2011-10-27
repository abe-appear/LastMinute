@echo off
set CLIENT_HOME=%~dp0
java -Dlog4j.configuration=file:///"%CLIENT_HOME%log4j.xml" -jar LastMinute.jar