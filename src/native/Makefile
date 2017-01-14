CC=gcc

CFLAGS=-O3 -fPIC

JAVA_INCLUDE=

ARCH=linux_`uname -m`
ARCH_DIR=../../build/libs/native/$(ARCH)

LIBJAME=${ARCH_DIR}/libjame.so

INCLUDE=-I. $(JAVA_INCLUDE) -I/include/linux -I/usr/include -I/usr/include/SDL2 -I/usr/local/include -I/usr/local/include/SDL2

JAME_OBJ=jame.o window.o surface.o texture.o renderer.o savepng.o trueTypeFont.o events.o audio.o sound.o softBlitPyGame.o

SDLJAVA_LIB=-lSDL

all: ${LIBJAME}

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDE) -c $< -o $@

${LIBJAME}: $(JAME_OBJ)
	mkdir -p $(ARCH_DIR)
	$(CC) -shared -o ${LIBJAME} ${JAME_OBJ} -lSDL2 -lSDL2_image -lSDL2_ttf -lSDL2_gfx -lSDL2_mixer

