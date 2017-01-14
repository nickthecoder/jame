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

#include "include/uk_co_nickthecoder_jame_Renderer.h"

JNIEXPORT jlong JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1create
  (JNIEnv *env, jobject obj, jlong pWindow, jint flags)
{
    return (jlong) SDL_CreateRenderer( (SDL_Window*) pWindow, -1, flags );
}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1destroy
  (JNIEnv *env, jobject jobj, jlong pRenderer )
{
    SDL_DestroyRenderer( (SDL_Renderer*) pRenderer );
}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1present
  (JNIEnv *env, jobject obj, jlong pRenderer )
{
    SDL_RenderPresent( (SDL_Renderer*) pRenderer );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1setClip
  (JNIEnv *env, jobject obj, jlong pRenderer, jint x, jint y, jint width, jint height)
{
    struct SDL_Rect rect = { .x=x, .y=y, .w=width, .h=height };
    
    return SDL_RendererSetClipRect( (SDL_Renderer*) pRenderer, &rect );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1setLogicalSize
  (JNIEnv *env, jobject obj, jlong pRenderer, jint x, jint y)
{
    return SDL_RendererSetLogicalSize( (SDL_Renderer*) pRenderer, x, y );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1setDrawColor
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint r, jint g, jint b, jint a)
{
    int result = SDL_SetRenderDrawColor( (SDL_Renderer*) pRenderer, r, g, b, a );
    return result;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1getDrawColor
  (JNIEnv *env, jobject job, jlong pRenderer, jobject jRGBA)
{
    int r;
    int g;
    int b;
    int a;
    
    int result = SDL_GetRendererDrawColor( (SDL_Renderer*) pRenderer, &r, &g, &b, &a );
    if ( result != 0 ) {
        return result;
    }
    
    jclass clazz = (*env)->GetObjectClass(env, jRGBA);
    jfieldID fid;

    fid = (*env)->GetFieldID(env,clazz,"r","I");
    (*env)->SetIntField(env,jRGBA,fid,r);

    fid = (*env)->GetFieldID(env,clazz,"g","I");
    (*env)->SetIntField(env,jRGBA,fid,g);

    fid = (*env)->GetFieldID(env,clazz,"b","I");
    (*env)->SetIntField(env,jRGBA,fid,b);

    fid = (*env)->GetFieldID(env,clazz,"a","I");
    (*env)->SetIntField(env,jRGBA,fid,a);
}



JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1clear
  (JNIEnv *env, jobject jobj, jlong pRenderer)
{
    int result = SDL_RenderClear( (SDL_Renderer*) pRenderer );
    return result;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1copy
  (JNIEnv *env, jobject jobj, jlong pRenderer, jlong pTexture, jint sx, jint sy, jint sw, jint sh, jint dx, jint dy, jint dw, jint dh)
{
    SDL_Rect src = {.x=sx, .y=sy, .w=sw, .h=sh };
    SDL_Rect dest = { .x=dx, .y=dy, .w=dw, .h=dh };
    
    int result = SDL_RenderCopy( (SDL_Renderer*) pRenderer, (SDL_Texture*) pTexture, &src, &dest );
}



JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1setDrawBlendMode
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint mode )
{
    return SDL_SetRendererDrawBlendMode( (SDL_Renderer*) pRenderer, mode );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_renderer_1getDrawBlendMode
  (JNIEnv *env, jobject jobj, jlong pRenderer)
{
    return SDL_GetRendererDrawBlendMode( (SDL_Renderer*) pRenderer );
}


