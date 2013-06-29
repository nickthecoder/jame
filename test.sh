#!/bin/bash

NATIVE_DIR=`pwd`/native/`uname -m`
CLASSPATH=jame.jar
MAIN=uk.co.nickthecoder.jame.test.TestVideo

echo java -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

java  -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

