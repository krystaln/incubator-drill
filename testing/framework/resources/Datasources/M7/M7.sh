#!/bin/bash

set -x

/root/incubator-drill/testing/framework/resources/Datasources/M7/createM7Tables.sh
wait
pig /root/incubator-drill/testing/framework/resources/Datasources/M7/loadM7Tables.pig 

set +x
