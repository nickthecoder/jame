#!/bin/bash

NATIVE_DIR=`pwd`/lib/native/`uname -m`
CLASSPATH=lib/jame.jar
MAIN=uk.co.nickthecoder.jame.test.TestVideo

echo java -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

java  -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

