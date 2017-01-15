#!/bin/bash

NATIVE_DIR=`pwd`/build/libs/native/linux_x86_64
CLASSPATH=build/libs/jame-2.0.jar
MAIN=uk.co.nickthecoder.jame.test.TestController

echo java -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

java  -Djava.library.path=${NATIVE_DIR} -classpath "${CLASSPATH}" "${MAIN}"

