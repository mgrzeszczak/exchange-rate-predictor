#!/bin/sh

START_FROM=${$NBP_START_FROM:-"2018-01-01"}

java -jar -Dserver.port=80 -Dnbp.start-from=$START_FROM /api.jar