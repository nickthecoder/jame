#include <jni.h>
#include <SDL.h>
#include <SDL_mixer.h>
#include "audio.h"

// open
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Audio_audio_1open
  (JNIEnv *env, jclass jaudio, jint frequency, jint format, jint channels, jint chunkSize)
{
    if ( format == 0 ) {
        format = MIX_DEFAULT_FORMAT;
    }
    return Mix_OpenAudio( frequency, format, channels, chunkSize );
}

// setMixChannel
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Audio_audio_1setMixChannels
  (JNIEnv *env, jclass jaudio, jint mixChannelCount)
{
    return Mix_AllocateChannels( mixChannelCount );
}

// isPlaying
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Audio_audio_1isPlaying
  (JNIEnv *env, jclass jaudio, jint mixChannelNumber )
{
    return Mix_Playing( mixChannelNumber );
}

