/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
#include <stdio.h>
#include <jni.h>
#include <SDL.h>
#include <SDL_video.h>

#include "include/uk_co_nickthecoder_jame_Window.h"

JNIEXPORT jlong JNICALL Java_uk_co_nickthecoder_jame_Window_native_1create
  (JNIEnv *env, jobject jwindow, jstring jtitle, jint x, jint y, jint w, jint h, jint flags)
{
    const char *title = (*env)->GetStringUTFChars(env, jtitle, 0);
    
    return (long) SDL_CreateWindow( title, x, y, w, h, flags );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Window_native_1getWindowID
  (JNIEnv *env, jobject jwindow, jlong pWindow)
{
    return SDL_GetWindowID((SDL_Window*) pWindow);
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1destroy
  (JNIEnv *env, jobject jobj, jlong pWindow )
{
    SDL_DestroyWindow( (SDL_Window*) pWindow );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1getPosition
  (JNIEnv *env, jobject jobj, jlong pWindow, jobject jRect )
{
    int x;
    int y;
    int width;
    int height;
    
    SDL_GetWindowPosition( (SDL_Window*) pWindow, &x, &y );
    SDL_GetWindowSize( (SDL_Window*) pWindow, &width, &height );
    
    jclass clazz = (*env)->GetObjectClass(env, jRect);
    jfieldID fid;

    fid = (*env)->GetFieldID(env,clazz,"x","I");
    (*env)->SetIntField(env,jRect,fid,x);

    fid = (*env)->GetFieldID(env,clazz,"y","I");
    (*env)->SetIntField(env,jRect,fid,y);

    fid = (*env)->GetFieldID(env,clazz,"width","I");
    (*env)->SetIntField(env,jRect,fid,width);

    fid = (*env)->GetFieldID(env,clazz,"height","I");
    (*env)->SetIntField(env,jRect,fid,height);

}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1hide
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_HideWindow( (SDL_Window*) pWindow );
}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1show
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_ShowWindow( (SDL_Window*) pWindow );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1maximize
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_MaximizeWindow( (SDL_Window*) pWindow );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1restore
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_RestoreWindow( (SDL_Window*) pWindow );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1raise
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_RaiseWindow( (SDL_Window*) pWindow );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Window_native_1fullScreen
  (JNIEnv *env, jobject jobj, jlong pWindow, jint flags)
{
    return SDL_SetWindowFullscreen( (SDL_Window*) pWindow, flags );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setIcon
  (JNIEnv *env, jobject jobj, jlong pWindow, jlong pSurface)
{
    SDL_SetWindowIcon( (SDL_Window*) pWindow, (SDL_Surface*) pSurface );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setInputFocus
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    return SDL_SetWindowInputFocus( (SDL_Window*) pWindow );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setPosition
  (JNIEnv *env, jobject jobj, jlong pWindow, jint x, jint y)
{
    SDL_SetWindowPosition( (SDL_Window*) pWindow, x, y );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setResizable
  (JNIEnv *env, jobject jobj, jlong pWindow, jboolean resizable )
{
    SDL_SetWindowResizable( (SDL_Window*) pWindow, resizable );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setSize
  (JNIEnv *env, jobject jobj, jlong pWindow, jint width, jint height)
{
    SDL_SetWindowSize( (SDL_Window*) pWindow, width, height );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Window_native_1setTitle
  (JNIEnv *env, jobject jobj, jlong pWindow, jstring jtitle )
{
    const char *title = (*env)->GetStringUTFChars(env, jtitle, 0);

    SDL_SetWindowTitle( (SDL_Window*) pWindow, title );

    (*env)->ReleaseStringUTFChars(env, jtitle, title);
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Window_native_1getPixelFormat
  (JNIEnv *env, jobject jobj, jlong pWindow )
{
    return SDL_GetWindowPixelFormat( (SDL_Window*) pWindow );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Window_native_1getRefreshRate
  (JNIEnv *env, jobject jobj, jlong pWindow)
{
    SDL_DisplayMode mode;
    
    if ( SDL_GetWindowDisplayMode( (SDL_Window*) pWindow, &mode ) == 0 ) {
        return mode.refresh_rate;
    } else {
        return -1;
    }
}


