#!/bin/bash

APP="${PWD##*/}"

# Compiling and buildpacking docker image
echo "** Compiling $APP"
docker image rm polysnap/$APP
mvn clean spring-boot:build-image -Dspring-boot.build-image.imageName="polysnap/$APP"
echo "** Done"