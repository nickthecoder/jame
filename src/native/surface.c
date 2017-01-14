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
#include <SDL2_rotozoom.h>
#include <png.h>

#include "include/uk_co_nickthecoder_jame_Surface.h"
#include "savepng.h"

void initialiseSurface( JNIEnv *env, jobject jsurface, SDL_Surface *surface )
{
    if ( surface ) {

        jclass clazz = (*env)->GetObjectClass(env, jsurface);
        jfieldID fid;

        fid = (*env)->GetFieldID(env,clazz,"pSurface","J");
        (*env)->SetLongField(env,jsurface,fid, (jlong) (intptr_t) surface);

        fid = (*env)->GetFieldID(env,clazz,"width","I");
        (*env)->SetIntField(env,jsurface,fid,surface->w);

        fid = (*env)->GetFieldID(env,clazz,"height","I");
        (*env)->SetIntField(env,jsurface,fid,surface->h);

        fid = (*env)->GetFieldID(env,clazz,"flags","I");
        (*env)->SetIntField(env,jsurface,fid,surface->flags);

        fid = (*env)->GetFieldID(env,clazz,"hasAlpha","Z");
        (*env)->SetBooleanField(env,jsurface,fid,surface->format->Amask != 0);

    }
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1setSurfaceAlphaMod
  (JNIEnv *env, jobject jobj, jlong pSurface, jint alpha)
{
    return SDL_SetSurfaceAlphaMod( (SDL_Surface*) pSurface, alpha );
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1free
  (JNIEnv *env, jobject jsurface, jlong pSurface )
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    SDL_FreeSurface( surface );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1create
  (JNIEnv *env, jobject jsurface, jint width, jint height, jboolean alpha)
{
    SDL_Surface* pSurface = SDL_CreateRGBSurface(0, width, height, alpha ? 32 : 24, 0, 0, 0, 0);
    
    initialiseSurface( env, jsurface, pSurface );
    
    return pSurface == 0;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1load
  (JNIEnv *env, jobject jsurface, jstring jfilename)
{
    const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);

    SDL_Surface *surface = IMG_Load( filename );
    initialiseSurface( env, jsurface, surface );

    (*env)->ReleaseStringUTFChars(env, jfilename, filename);

    return surface == 0;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1saveAsPNG
  (JNIEnv *env, jobject jSurface, jlong pSurface, jstring jfilename )
{
    const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;

    int result = SDL_SavePNG( surface, filename );
    (*env)->ReleaseStringUTFChars(env, jfilename, filename);
    return result;   
}
  


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1fill2
  (JNIEnv *rnv, jobject jsurface, jlong pSurface, jint x, jint y, jint width, jint height, jint red, jint green, jint blue, jint alpha )
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    struct SDL_Rect rect = { .x=x, .y=y, .w=width, .h=height };
    Uint32 color = SDL_MapRGBA( surface->format, red, green, blue, alpha );

    return SDL_FillRect( surface, &rect, color );
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1fill
  (JNIEnv *env, jobject jsurface, jlong pSurface, jint x, jint y, jint width, jint height, jint color)
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    struct SDL_Rect rect = { .x=x, .y=y, .w=width, .h=height };

    return SDL_FillRect( surface, &rect, color );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1flip
  (JNIEnv *env, jobject jsurface, jlong pSurface)
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;

    return SDL_Flip( surface );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1blit
  (JNIEnv *env, jobject jsurface, jlong pSrc, jlong pDest, jint x, jint y)
{
    struct SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;
    struct SDL_Surface *dest = (SDL_Surface*) (intptr_t) pDest;
    struct SDL_Rect srcRect = { .x=0, .y=0, .w=src->w, .h=src->h };
    struct SDL_Rect destRect = { .x=x, .y=y, .w=src->w, .h=src->h };

    return SDL_BlitSurface( src, &srcRect, dest, &destRect );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1blit2
  (JNIEnv *env, jobject jsurface, jlong pSrc, jint sx, jint sy, jint swidth, jint sheight, jlong pDest, jint dx, jint dy, jint dwidth, jint dheight )
{
    struct SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;
    struct SDL_Surface *dest = (SDL_Surface*) (intptr_t) pDest;
    struct SDL_Rect srcRect = { .x=sx, .y=sy, .w=swidth, .h=sheight };
    struct SDL_Rect destRect = { .x=dx, .y=dy, .w=dwidth, .h=dheight };

    return SDL_BlitSurface( src, &srcRect, dest, &destRect );

}

int 
pygame_Blit (SDL_Surface * src, SDL_Rect * srcrect,
             SDL_Surface * dst, SDL_Rect * dstrect, int the_args);

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1blit3
  (JNIEnv *env, jobject jsurface, jlong pSrc, jint sx, jint sy, jint swidth, jint sheight, jlong pDest, jint dx, jint dy, jint dwidth, jint dheight, jint flags )
{
    struct SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;
    struct SDL_Surface *dest = (SDL_Surface*) (intptr_t) pDest;
    struct SDL_Rect srcRect = { .x=sx, .y=sy, .w=swidth, .h=sheight };
    struct SDL_Rect destRect = { .x=dx, .y=dy, .w=dwidth, .h=dheight };

    return pygame_Blit( src, &srcRect, dest, &destRect, flags );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1zoom
  (JNIEnv *env, jobject jsrc, jobject jdest, jlong pSrc, jdouble zoomX, jdouble zoomY, jboolean smooth)
{
    SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;

    SDL_Surface *dest = zoomSurface( src, zoomX, zoomY, smooth );
    initialiseSurface( env, jdest, dest );

    return dest == 0;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1rotoZoom
  (JNIEnv *env, jobject jsrc, jobject jdest, jlong pSrc, jdouble angle, jdouble zoom, jboolean smooth )
{
    SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;
    SDL_Surface *dest = rotozoomSurface( src, angle, zoom, smooth );
    initialiseSurface( env, jdest, dest );

    return dest == 0;
}

JNIEXPORT jboolean JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1pixelOverlap
  (JNIEnv *env, jobject jsurface, jlong pA, jlong pB, jint dx, jint dy, jint alphaThreshold )
{
    SDL_Surface *a = (SDL_Surface*) (intptr_t) pA;
    SDL_Surface *b = (SDL_Surface*) (intptr_t) pB;

    if ( a->format->BitsPerPixel != 32 ) {
        return 0;
    }
    if ( a->format->BitsPerPixel != 32 ) {
        return 0;
    }

    int width = a->w;
    int height = a->h;

    int ax = 0;
    int ay = 0;
    int bx = 0;
    int by = 0;
        
    // Adjust x values
    if ( dx > 0 ) {
        // b is left of a
        bx = dx;
    } else {
        // a is left of b
        ax = -dx;
        width += dx;
    }

    if ( bx + width > b->w ) {
        width = b->w - bx;
    }

    // Now adjust y values
    if ( dy > 0 ) {
        // b is below a
        by = dy;
    } else {
        // a is below b
        ay = -dy;
        height += dy;
    }

    if ( by + height > b->h ) {
        height = b->h - by;
    }

    if ( (height < 0) || (width < 0) ) {
        return 0;
    }

    Uint32 aThresh = alphaThreshold << a->format->Ashift;
    Uint32 bThresh = alphaThreshold << b->format->Ashift;
    Uint32 aMask = a->format->Amask;
    Uint32 bMask = b->format->Amask;

    int aPitch = a->pitch / 4;
    int bPitch = b->pitch / 4;

    SDL_LockSurface( a );
    if ( b != a ) {
        SDL_LockSurface( b );
    }

    Uint32 *aStart = ( (Uint32*) a->pixels ) + ax + ay * aPitch;
    Uint32 *bStart = ( (Uint32*) b->pixels ) + bx + by * bPitch;

    int x, y;
    for ( y = 0; y < height; y ++ ) {
        for ( x = 0; x < width; x ++ ) {
            int aAlpha =  aMask & (aStart[ x + y * aPitch ]);
            if ( aAlpha >= aThresh ) {
                int bAlpha = bMask & (bStart[ x + y * bPitch ]);
                if ( bAlpha >= bThresh ) {
                    if ( b != a ) {
                        SDL_UnlockSurface( b );
                    }
                    SDL_UnlockSurface( a );
                    return 1;
                }
            }
        }        
    }

    if ( b != a ) {
        SDL_UnlockSurface( b );
    }
    SDL_UnlockSurface( a );

    return 0;
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1getPixelColor
  (JNIEnv *env, jobject jsurface, jlong pSurface, jint x, jint y)
{
    SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;    

    if ( surface->format->BytesPerPixel != 4 ) {
        return 0;
    }
    SDL_LockSurface( surface );
    Uint32 value = ( (Uint32*) (surface->pixels) )[ x + y * surface->pitch / 4 ];
    SDL_UnlockSurface( surface );

    return value;
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1getPixelRGBA
  (JNIEnv *env, jobject jsurface, jlong pSurface, jobject jrgba, jint x, jint y)
{
    SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;    

    if ( surface->format->BytesPerPixel != 4 ) {
        return;
    }
    SDL_LockSurface( surface );
    Uint32 value = ( (Uint32*) (surface->pixels) )[ x + y * surface->pitch / 4 ];
    SDL_UnlockSurface( surface );

    jclass clazz = (*env)->GetObjectClass(env, jrgba);
    jfieldID fid;

    fid = (*env)->GetFieldID(env,clazz,"r","I");
    (*env)->SetIntField(env,jrgba,fid, (value & surface->format->Rmask) >> surface->format->Rshift );
    
    fid = (*env)->GetFieldID(env,clazz,"g","I");
    (*env)->SetIntField(env,jrgba,fid, (value & surface->format->Gmask) >> surface->format->Gshift );

    fid = (*env)->GetFieldID(env,clazz,"b","I");
    (*env)->SetIntField(env,jrgba,fid, (value & surface->format->Bmask) >> surface->format->Bshift );
    
    fid = (*env)->GetFieldID(env,clazz,"a","I");
    (*env)->SetIntField(env,jrgba,fid, (value & surface->format->Amask) >> surface->format->Ashift );

}

// Set Pixel
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1setPixel__JIII
  (JNIEnv *env, jobject jSurface, jlong pSurface, jint x, jint y, jint value)
{
   SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;    

    if ( surface->format->BytesPerPixel != 4 ) {
        return;
    }

    SDL_LockSurface( surface );
    ((Uint32*) (surface->pixels))[ x + y * surface->pitch / 4 ] = value;
    SDL_UnlockSurface( surface );
}

/*
 * Class:     uk_co_nickthecoder_jame_Surface
 * Method:    surface_setPixel
 * Signature: (JIILuk/co/nickthecoder/jame/RGBA;)V
 */
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1setPixel__JIIIIII
  (JNIEnv *env, jobject jSurface, jlong pSurface, jint x, jint y, jint r, jint g, jint b, jint a )
{
    SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    
    if ( surface->format->BytesPerPixel != 4 ) {
        return;
    }
    
    Uint32 value =
        ((r&255) << surface->format->Rshift) |
        ((g&255) << surface->format->Gshift) |
        ((b&255) << surface->format->Bshift) |
        ((a&255) << surface->format->Ashift);

    SDL_LockSurface( surface );
    ((Uint32*) (surface->pixels))[ x + y * surface->pitch / 4 ] = value;
    SDL_UnlockSurface( surface );
        
}
