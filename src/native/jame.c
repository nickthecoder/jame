/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
#include <jni.h>
#include <SDL.h>
#include <SDL_video.h>
#include "include/uk_co_nickthecoder_jame_Jame.h"

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
    const char* message = SDL_GetError();
    return( (*env)->NewStringUTF(env, message));
}


