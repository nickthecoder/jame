#include <jni.h>
#include <SDL.h>
#include <SDL_video.h>
#include "jame.h"

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Jame_jame_1init
  (JNIEnv *env, jclass class )
{
    return SDL_Init( 0 );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Jame_jame_1initSubsystem
  (JNIEnv *env, jclass class, jint subsystem )
{
    return SDL_InitSubSystem( subsystem );
}

JNIEXPORT jstring JNICALL Java_uk_co_nickthecoder_jame_Jame_jame_1getError
  (JNIEnv *env, jclass class )
{
    char* message = SDL_GetError();
    return( (*env)->NewStringUTF(env, message));
}


