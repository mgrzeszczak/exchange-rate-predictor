#!/bin/bash

cd ..
./gradlew clean build
cp api/build/libs/api-*.jar docker/api.jar
cd docker
docker build . -t exchange-rate-predictor:latest