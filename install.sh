#!/bin/bash

# This can probably be replaced by gradle wrapper

CWD=$(pwd)
INSTALLS=~/.installs
CACHEDIR=~/.cache
GRADLE_ZIP_URL="https://services.gradle.org/distributions/gradle-2.9-bin.zip"
GRADLE_ZIP=$(basename $GRADLE_ZIP_URL)
GRADLE_DIR=$INSTALLS/gradle-2.9
#echo $GRADLE_ZIP


which wget > /dev/null 2>&1
RC=$?
if [[ "$RC" != "0" ]]; then
     su - -c 'yum -y install wget'
fi

if [ ! -d $CACHEDIR ]; then
    mkdir $CACHEDIR
fi

if [ ! -d $INSTALLS ]; then
    mkdir $INSTALLS
fi

if [ ! -f $CACHEDIR/$GRADLE_ZIP ]; then
    wget -O $CACHEDIR/$GRADLE_ZIP $GRADLE_ZIP_URL
fi

if [ ! -d $GRADLE_DIR ]; then
    cd $INSTALLS
    unzip -e $CACHEDIR/$GRADLE_ZIP
fi    

if [ ! -d ~/bin ]; then
    mkdir ~/bin
fi

if [ ! -s ~/bin/gradle ]; then
    ln -s $GRADLE_DIR/bin/gradle ~/bin/gradle
fi


gradle build --info
