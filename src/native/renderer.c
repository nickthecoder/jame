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

JNIEXPORT jlong JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1create
  (JNIEnv *env, jobject obj, jlong pWindow, jint flags)
{
    return (jlong) SDL_CreateRenderer( (SDL_Window*) pWindow, -1, flags );
}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1destroy
  (JNIEnv *env, jobject jobj, jlong pRenderer )
{
    SDL_DestroyRenderer( (SDL_Renderer*) pRenderer );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1getFlags
  (JNIEnv *env, jobject jobj, jlong pRenderer)
{
    SDL_RendererInfo info;
    
    if ( SDL_GetRendererInfo( (SDL_Renderer*) pRenderer, &info ) == 0 ) {
        return info.flags;
    }
    return -1;
}


JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1present
  (JNIEnv *env, jobject obj, jlong pRenderer )
{
    SDL_RenderPresent( (SDL_Renderer*) pRenderer );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setClip
  (JNIEnv *env, jobject obj, jlong pRenderer, jint x, jint y, jint width, jint height)
{
    struct SDL_Rect rect = { .x=x, .y=y, .w=width, .h=height };
    
    return SDL_RenderSetClipRect( (SDL_Renderer*) pRenderer, &rect );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1clearClip
  (JNIEnv *env, jobject obj, jlong pRenderer)
{
    return SDL_RenderSetClipRect( (SDL_Renderer*) pRenderer, NULL );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setViewport
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint x, jint y, jint width, jint height)
{
    SDL_Rect rect = { .x=x, .y=y, .w=width, .h=height };
    return SDL_SetRendererViewport( (SDL_Renderer*) pRenderer, &rect );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1getViewport
  (JNIEnv *env, jobject jobj, jlong pRenderer, jobject jRect)
{
    SDL_Rect rect;
    SDL_GetRendererViewport( (SDL_Renderer*) pRenderer, &rect );
    
    jclass clazz = (*env)->GetObjectClass(env, jRect);
    jfieldID fid;

    fid = (*env)->GetFieldID(env,clazz,"x","I");
    (*env)->SetIntField(env,jRect,fid,rect.x);

    fid = (*env)->GetFieldID(env,clazz,"y","I");
    (*env)->SetIntField(env,jRect,fid,rect.y);

    fid = (*env)->GetFieldID(env,clazz,"width","I");
    (*env)->SetIntField(env,jRect,fid,rect.w);

    fid = (*env)->GetFieldID(env,clazz,"height","I");
    (*env)->SetIntField(env,jRect,fid,rect.h);

}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setLogicalSize
  (JNIEnv *env, jobject obj, jlong pRenderer, jint x, jint y)
{
    return SDL_RendererSetLogicalSize( (SDL_Renderer*) pRenderer, x, y );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setDrawColor
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint r, jint g, jint b, jint a)
{
    int result = SDL_SetRenderDrawColor( (SDL_Renderer*) pRenderer, r, g, b, a );
    return result;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1getDrawColor
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



JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1clear
  (JNIEnv *env, jobject jobj, jlong pRenderer)
{
    int result = SDL_RenderClear( (SDL_Renderer*) pRenderer );
    return result;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1copy
  (JNIEnv *env, jobject jobj, jlong pRenderer, jlong pTexture,
    jint srcX, jint srcY, jint srcWidth, jint srcHeight,
    jint dstX, jint dstY, jint dstWidth, jint dstHeight)
{
    SDL_Rect src = { .x=srcX, .y=srcY, .w=srcWidth, .h=srcHeight };
    SDL_Rect dst = { .x=dstX, .y=dstY, .w=dstWidth, .h=dstHeight };
    
    int result = SDL_RenderCopy( (SDL_Renderer*) pRenderer, (SDL_Texture*) pTexture, &src, &dst );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1copyEx
  (JNIEnv *env, jobject jobj, jlong pRenderer, jlong pTexture,
  jint srcX, jint srcY, jint srcWidth, jint srcHeight,
  jint dstX, jint dstY, jint dstWidth, jint dstHeight,
  jdouble angle, jint ox, jint oy, jint flip)
{
    SDL_Rect src = {.x=srcX, .y=srcY, .w=srcWidth, .h=srcHeight };
    SDL_Rect dst = { .x=dstX, .y=dstY, .w=dstWidth, .h=dstHeight };
    SDL_Point center = {.x=ox, .y=oy };

    return SDL_RenderCopyEx( (SDL_Renderer*) pRenderer, (SDL_Texture*) pTexture, &src, &dst,
        angle, &center, flip );
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setDrawBlendMode
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint mode )
{
    SDL_BlendMode blendMode = mode;
    return SDL_SetRenderDrawBlendMode( (SDL_Renderer*) pRenderer, mode );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1getDrawBlendMode
  (JNIEnv *env, jobject jobj, jlong pRenderer)
{
    SDL_BlendMode blendMode;
    if ( SDL_GetRenderDrawBlendMode( (SDL_Renderer*) pRenderer, &blendMode ) != 0) {
        return -1;
    }
    return blendMode;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1setTarget
  (JNIEnv *env, jobject jobj, jlong pRenderer, jlong pTexture )
{
    return SDL_SetRenderTarget( (SDL_Renderer*) pRenderer, (SDL_Texture*) pTexture );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_texture_1setColorMod
  (JNIEnv *env, jobject jobj, jlong pTexture, jint r, jint g, jint b)
{
    return SDL_TextureColorMod( (SDL_Texture*) pTexture, r, g, b );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1drawRect
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint x, jint y, jint width, jint height)
{
    SDL_Rect rect = { .x = x, .y = y, .w=width, .h=height };
    return SDL_RenderDrawRect( (SDL_Renderer*) pRenderer, &rect );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1fillRect
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint x, jint y, jint width, jint height)
{
    SDL_Rect rect = { .x = x, .y = y, .w=width, .h=height };
    return SDL_RenderFillRect( (SDL_Renderer*) pRenderer, &rect );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Renderer_native_1drawLine
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint x1, jint y1, jint x2, jint y2)
{
    return SDL_RenderDrawLine( (SDL_Renderer*) pRenderer, x1, y1, x2, y2 );
}


