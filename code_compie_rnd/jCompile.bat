set fileName=%1

echo %fileName%

javac %fileName%.java

echo start
java %fileName% -Xmx10m