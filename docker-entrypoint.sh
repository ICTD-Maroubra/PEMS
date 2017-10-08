#!/bin/bash

cd /pems

service dbus start
service dbus status

service bluetooth restart

java -Djava.library.path=/pems/ -jar pems-all.jar