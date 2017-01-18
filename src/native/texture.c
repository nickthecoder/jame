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
#include <SDL_image.h>

#include "include/uk_co_nickthecoder_jame_Texture.h"

JNIEXPORT jlong JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1create
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint format, jint access, jint width, jint height)
{
    return (long) SDL_CreateTexture( (SDL_Renderer*) pRenderer, format, access, width, height );
}

JNIEXPORT jlong JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1createFromSurface
  (JNIEnv *env, jobject jobj, jlong pRenderer, jlong pSurface)
{
    return (long) SDL_CreateTextureFromSurface( (SDL_Renderer*) pRenderer, (SDL_Surface*) pSurface);
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1destroy
  (JNIEnv *env, jobject jobj, jlong pTexture)
{
    SDL_DestroyTexture( (SDL_Texture*) pTexture );
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_renderer_1setBlendMode
  (JNIEnv *env, jobject jobj, jlong pTexture, jint mode)
{
    return SDL_SetTextureBlendMode( (SDL_Texture*) pTexture, mode );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_renderer_1getBlendMode
  (JNIEnv *env, jobject jobj, jlong pTexture)
{
    SDL_BlendMode result;
    if ( SDL_GetTextureBlendMode( (SDL_Texture*) pTexture, &result ) != 0 ) {
        return -1;
    } else {
        return result;
    }
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1setAlpha
  (JNIEnv *env, jobject jobj, jlong pTexture, jint alpha)
{
    return SDL_SetTextureAlphaMod( (SDL_Texture*) pTexture, alpha );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1getAlpha
  (JNIEnv *env, jobject jobj, jlong pTexture)
{
    return SDL_GetTExtureAlphaMod( (SDL_Texture*) pTexture );
}    

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1update
  (JNIEnv *env, jobject job, jlong pTexture, jlong surface)
{
    SDL_Surface *pSurface = (SDL_Surface*) surface;
    
    SDL_Rect rect = { .x=0, .y=0, pSurface->w, pSurface->h };
    return SDL_UpdateTexture( (SDL_Texture*) pTexture, &rect, pSurface->pixels, pSurface->pitch);
}

/*
    Fills the java RGBA with the pixel at (0,0) of the renderer
*/
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Texture_native_1getPixel
  (JNIEnv *env, jobject jobj, jlong pRenderer, jint pixelFormat, jobject jRGBA)
{
    SDL_Rect rect = {.x=0,.y=0,.w=1,.h=1 };
    Uint32 pixels;
    
    int result = SDL_RenderReadPixels( (SDL_Renderer*) pRenderer, &rect, pixelFormat, &pixels, 4 /* four bytes */);
    if ( result != 0 ) {
        return result;
    }
    
    Uint8 red;
    Uint8 green;
    Uint8 blue;
    Uint8 alpha;
    
    SDL_PixelFormat* pf = SDL_AllocFormat(pixelFormat);
    SDL_GetRGBA( pixels, pf, &red, &green, &blue, &alpha );
    SDL_FreeFormat(pf);

    jclass clazz = (*env)->GetObjectClass(env, jRGBA);
    jfieldID fid;

    fid = (*env)->GetFieldID(env,clazz,"r","I");
    (*env)->SetIntField(env,jRGBA,fid, red );
    
    fid = (*env)->GetFieldID(env,clazz,"g","I");
    (*env)->SetIntField(env,jRGBA,fid, green );

    fid = (*env)->GetFieldID(env,clazz,"b","I");
    (*env)->SetIntField(env,jRGBA,fid, blue );
    
    fid = (*env)->GetFieldID(env,clazz,"a","I");
    (*env)->SetIntField(env,jRGBA,fid, alpha );

    return 0;
}


