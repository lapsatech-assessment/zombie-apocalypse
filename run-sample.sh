#!/bin/bash

JAR="zombie-apocalypse-game/target/zombie-apocalypse-game-CURRENT-SNAPSHOT.jar"
test -f ${JAR} || ./mvnw package && java -jar ${JAR}  < sample-input.txt
