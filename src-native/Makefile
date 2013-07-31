CC=gcc

CFLAGS=-O3 -fPIC

JAVA_INCLUDE=-I/usr/lib/jvm/java-6-openjdk-amd64/include

ARCH=`uname -m`

INCLUDE=-I. $(JAVA_INCLUDE) -I/include/linux -I/usr/include -I/usr/include/SDL -I/usr/local/include -I/usr/local/include/SDL


JAME_OBJ=jame.o video.o surface.o trueTypeFont.o events.o audio.o sound.o softBlitPyGame.o

SDLJAVA_LIB=-lSDL

allFromScratch : clean all

all:	libjame.so copy

%.o: %.c
	echo Java home : ${JAVA_HOME}
	$(CC) $(CFLAGS) $(INCLUDE) -c $< -o $@

libjame.so: $(JAME_OBJ)
	$(CC) -shared -o libjame.so ${JAME_OBJ} -lSDL -lSDL_image -lSDL_ttf -lSDL_gfx -lSDL_mixer

clean:
	-rm *.o *.so

copy:
	mkdir -p ../lib/native/$(ARCH)
	cp libjame.so ../lib/native/$(ARCH)/

