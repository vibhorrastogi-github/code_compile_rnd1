set fileName=%1

set dirName=%2

echo %fileName%
echo %dirName%

cd %dirName%


javac %fileName%.java

java %fileName%