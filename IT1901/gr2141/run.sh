#!/bin/sh
cd flashy
mvn clean package -Dmaven.test.skip=true
java -jar target/flashy.jar