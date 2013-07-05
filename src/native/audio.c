#include <jni.h>
#include <SDL.h>
#include <SDL_mixer.h>
#include "audio.h"

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Audio_audio_1open
  (JNIEnv *env, jclass jaudio, jint frequency, jint format, jint channels, jint chunkSize )
{
    if ( format == 0 ) {
        format = MIX_DEFAULT_FORMAT;
    }
    return Mix_OpenAudio( frequency, format, channels, chunkSize );

}


