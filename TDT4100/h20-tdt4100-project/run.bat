@ECHO OFF

call mvn clean package -Dmaven.test.skip=true  

java -jar --enable-preview target/banana-editor-1.0.0.jar

pause