#!/bin/bash

# This can probably be replaced by gradle wrapper

CWD=$(pwd)
INSTALLS=~/.installs
CACHEDIR=~/.cache
GRADLE_ZIP_URL="https://services.gradle.org/distributions/gradle-2.9-bin.zip"
GRADLE_ZIP=$(basename $GRADLE_ZIP_URL)
GRADLE_DIR=$INSTALLS/gradle-2.9
GROOVY_ZIP_URL="http://dl.bintray.com/groovy/maven/apache-groovy-binary-2.4.5.zip"
GROOVY_ZIP=$(basename $GROOVY_ZIP_URL)
GROOVY_DIR=$INSTALLS/groovy-2.4.5
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

if [ ! -f $CACHEDIR/$GROOVY_ZIP ]; then
    wget -O $CACHEDIR/$GROOVY_ZIP $GROOVY_ZIP_URL
fi

if [ ! -d $GROOVY_DIR ]; then
    cd $INSTALLS
    unzip -e $CACHEDIR/$GROOVY_ZIP
fi    

if [ ! -s ~/bin/groovy ]; then
    ln -s $GROOVY_DIR/bin/groovy ~/bin/groovy
fi


gradle build --info
RC=$?

echo "#####################################################"
echo "                 RESULTS XML"
echo "#####################################################"
cat build/test-results/*.xml | fgrep '<testsuite name='
