@ECHO OFF
cd flashy
call mvn clean package -Dmaven.test.skip=true
java -jar target/flashy.jar
pause 
