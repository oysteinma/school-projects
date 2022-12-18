#!/bin/sh
mvn clean package -Dmaven.test.skip=true
java -jar target/flashy.jar
