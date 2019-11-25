#!/bin/bash

JAR="target/zombie-apocalypse-4.4-SNAPSHOT.jar"
test -f ${JAR} || ./mvnw package && java -jar ${JAR}
