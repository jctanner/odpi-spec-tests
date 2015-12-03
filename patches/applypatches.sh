#!/bin/bash

COMMANDS="hadoop hdfs mapred yarn"

for COMMAND in $COMMANDS; do
    BASEDIR=$(which $COMMAND)
    BASEDIR=$(readlink $BASEDIR)
    BASEDIR=$(dirname $BASEDIR)
    echo "$COMMAND --> $BASEDIR"
    su -c "cp -f $COMMAND.distro.patched $BASEDIR/$COMMAND.distro"
done


su -c "cp -f odpi-env.sh /etc/profile.d/."
