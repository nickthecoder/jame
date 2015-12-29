CC=gcc

CFLAGS=-O3 -fPIC

JAVA_INCLUDE=

ARCH=linux_`uname -m`
ARCH_DIR=../../build/libs/native/$(ARCH)

LIBJAME=${ARCH_DIR}/libjame.so

INCLUDE=-I. $(JAVA_INCLUDE) -I/include/linux -I/usr/include -I/usr/include/SDL -I/usr/local/include -I/usr/local/include/SDL

JAME_OBJ=jame.o video.o surface.o trueTypeFont.o events.o audio.o sound.o softBlitPyGame.o

SDLJAVA_LIB=-lSDL

all: ${LIBJAME}

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDE) -c $< -o $@

${LIBJAME}: $(JAME_OBJ)
	mkdir -p $(ARCH_DIR)
	$(CC) -shared -o ${LIBJAME} ${JAME_OBJ} -lSDL -lSDL_image -lSDL_ttf -lSDL_gfx -lSDL_mixer

