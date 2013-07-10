#include <jni.h>
#include <SDL.h>
#include <SDL_video.h>
#include "video.h"
#include "surface.h"

static SDL_Surface *exampleAlphaSurface;

SDL_Surface* Java_uk_co_nickthecoder_jame_Video_getExampleAlphaSurface( void )
{
    return exampleAlphaSurface;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Video_video_1setMode
  (JNIEnv *env, jclass jvideo, jobject jsurface, jint width, jint height, jint bpp, jint flags)
{
    SDL_Surface *surface = SDL_SetVideoMode( width, height, bpp, flags );
    initialiseSurface( env, jsurface, surface );
    
    SDL_Surface *tempSurface = SDL_CreateRGBSurface( 0, 1, 1, 32, 0xff000000,0x00ff0000,0x0000ff00,0x000000ff );
    exampleAlphaSurface = SDL_DisplayFormatAlpha( tempSurface );
    SDL_FreeSurface( tempSurface );

    return surface == 0;
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Video_video_1setWindowTitle
  (JNIEnv *env, jclass jvideo, jstring jtitle)
{
    const char *title = (*env)->GetStringUTFChars(env, jtitle, 0);

    SDL_WM_SetCaption( title, NULL );

   (*env)->ReleaseStringUTFChars(env, jtitle, title);
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Video_video_1setWindowIcon
  (JNIEnv *env, jclass jvideo, jstring jfilename)
{
    const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);

    SDL_WM_SetIcon(SDL_LoadBMP( filename ), NULL);

   (*env)->ReleaseStringUTFChars(env, jfilename, filename);
}
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Video_video_1showMousePointer
  (JNIEnv *env, jclass jvideo, jint jvalue)
{
    SDL_ShowCursor( jvalue );
}

