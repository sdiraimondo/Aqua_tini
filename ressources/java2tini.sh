#!/bin/sh
# original script by tv (www.tvtsii.net)
# modif. by seero

NAME=$1
TARGET=$1".java"
TH=/usr/local/tini1.02d/bin
JAVAC=/usr/java/j2sdk1.4.2_09/bin/javac
JAVA=/usr/java/j2sdk1.4.2_09/bin/java
CLASSPATH=.:$TH/tini.jar:/usr/java/j2sdk1.4.2_09/lib/ext/comm.jar:/usr/local/tini1.02d/Taylec:/usr/local/tini1.02d/Taylec/lib:
TAYLEC=/usr/local/tini1.02d/Taylec

rm -f ./*.class
rm -f ./*.tini
echo "Java2tini : Java Makefile & Tini Convertor"
echo
echo "Step 1"
$JAVAC -target 1.1 -O -deprecation -classpath $TH/tiniclasses.jar:$TAYLEC/lib/TaylecTINI.jar $TARGET

if [ $? != "0" ]
then
   echo "ERREUR: javac !!!"; echo
   exit 126
fi

echo "Step 2"
$JAVA -classpath $TH/tini.jar:$CLASSPATH TINIConvertor -f . -f $TAYLEC/lib/TaylecTINI.jar -o $NAME.tini -d $TH/tini.db

#echo "Step 3"
#ftp 192.168.52.75 < $HOME/ftp.txt
