@echo off
:count
echo|set /p="Num of HTTP Connections(port:80): " & netstat -na |findstr "ESTABLISHED" | find /C ":80"
timeout 2 > nul
GOTO count