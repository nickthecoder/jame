#include <jni.h>
#include <SDL.h>
#include <SDL_mixer.h>
#include "sound.h"

void initialiseSound( JNIEnv *env, jobject jsound, Mix_Chunk *chunk )
{
    //printf( "Initialising sound %p\n", chunk );
    if ( chunk ) {

        jclass clazz = (*env)->GetObjectClass(env, jsound);
        jfieldID fid;

        fid = (*env)->GetFieldID(env,clazz,"pSound","J");
        (*env)->SetLongField(env,jsound,fid, (jlong) (intptr_t) chunk);
    }

}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1load
  (JNIEnv *env, jobject jsound, jstring jfilename)
{
    const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);

    Mix_Chunk *chunk = Mix_LoadWAV( filename );
    initialiseSound( env, jsound, chunk );

    (*env)->ReleaseStringUTFChars(env, jfilename, filename);

    return chunk == 0;

}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1free
  (JNIEnv *env, jobject jsound, jlong pSound )
{
    Mix_Chunk *chunk = (Mix_Chunk*) (intptr_t) pSound;
    Mix_FreeChunk( chunk );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Sound_sound_1play
  (JNIEnv *env, jobject jsound, jlong pSound )
{
    Mix_Chunk *chunk = (Mix_Chunk*) (intptr_t) pSound;

    Mix_PlayChannel( -1, chunk, 0 );
}


