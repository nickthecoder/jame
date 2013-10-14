SET NATIVE_DIR=lib\native\win32
SET CLASSPATH=lib\jame.jar
SET MAIN=uk.co.nickthecoder.jame.test.TestVideo

#
# Because Windows has a horrible search order for its dlls, then I'm forced to have the
# dlls in the game's current directory, but I don't want them polluting the base
# directory on other platforms, so I copy them when run from windows.
#
copy %NATIVE_DIR%\*.dll .

java  -Djava.library.path="." -classpath "%CLASSPATH%" "%MAIN%"
