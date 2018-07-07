#!/bin/sh

IMPORT_FROM=${NBP_IMPORT_FROM:-"2018-01-01"}

java -jar -Dserver.port=80 -Dnbp.import-from=$IMPORT_FROM /api.jar
