#include <stdio.h>
#include <jni.h>
#include <SDL.h>
#include <SDL_video.h>
#include <SDL_image.h>
#include <SDL_rotozoom.h>

#include "surface.h"

SDL_Surface* Java_uk_co_nickthecoder_jame_Video_getExampleAlphaSurface( void );

void initialiseSurface( JNIEnv *env, jobject jsurface, SDL_Surface *surface )
{
    //printf( "Initialising surface %p - %i,%i\n", surface, surface->w, surface->h );
    if ( surface ) {

        jclass clazz = (*env)->GetObjectClass(env, jsurface);
        jfieldID fid;

        fid = (*env)->GetFieldID(env,clazz,"pSurface","J");
        (*env)->SetLongField(env,jsurface,fid, (jlong) (intptr_t) surface);

        fid = (*env)->GetFieldID(env,clazz,"width","I");
        (*env)->SetIntField(env,jsurface,fid,surface->w);

        fid = (*env)->GetFieldID(env,clazz,"height","I");
        (*env)->SetIntField(env,jsurface,fid,surface->h);

        fid = (*env)->GetFieldID(env,clazz,"hasAlpha","Z");
        (*env)->SetBooleanField(env,jsurface,fid,surface->format->Amask != 0);
    }
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1setAlpha
  (JNIEnv *env, jobject jsurface, jlong pSurface, jint flags, jint alpha )
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    return SDL_SetAlpha( surface, flags, alpha );
}


JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1free
  (JNIEnv *env, jobject jsurface, jlong pSurface )
{
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    SDL_FreeSurface( surface );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1create
  (JNIEnv *env, jobject jsurface, jint width, jint height, jboolean alpha, jboolean hardware)
{
    //printf( "Creating an empty surface %i, %i\n", width, height );
    Uint32 flags = ( hardware ? SDL_HWSURFACE : SDL_SWSURFACE);

    SDL_PixelFormat *fmt;
    if ( alpha ) {
        fmt = Java_uk_co_nickthecoder_jame_Video_getExampleAlphaSurface()->format;
    } else {
        SDL_Surface *screen = SDL_GetVideoSurface();
        fmt = screen->format;
    }

    SDL_Surface *result = SDL_CreateRGBSurface( flags, width, height, fmt->BitsPerPixel, fmt->Rmask, fmt->Gmask, fmt->Bmask, fmt->Amask );

    initialiseSurface( env, jsurface, result );
    
    return result == 0;
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
    // printf( "Surface to fill %p\n", pSurface );
    struct SDL_Surface *surface = (SDL_Surface*) (intptr_t) pSurface;
    //printf( "Filling %d,%d, %d,%d with color %x %x\nLoad", x, y, width, height, color, surface->format->Amask );
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

    /*
    printf( "C Blend %d : (%d,%d-%d,%d) %p (%d,%d-%d %d) %p\n\n",
        flags,
        srcRect.x, srcRect.y, srcRect.w, srcRect.h,
        src,
        destRect.x, destRect.x, destRect.w, destRect.h,
        dest );
    */
    return pygame_Blit( src, &srcRect, dest, &destRect, flags );
}

JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1displayFormat
  (JNIEnv *env, jobject jsurface, jlong pSrc)
{
    struct SDL_Surface *src = (SDL_Surface*) (intptr_t) pSrc;
    struct SDL_Surface *result = (src->format->Amask == 0) ?
        SDL_DisplayFormat( src ) :
        SDL_DisplayFormatAlpha( src );

    initialiseSurface( env, jsurface, result );

    return result == 0;
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

JNIEXPORT jboolean JNICALL Java_uk_co_nickthecoder_jame_Surface_surface_1overlaps
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

    //printf( "a : pixels %p start %p\n", a->pixels, aStart );
    //printf( "b : pixels %p start %p\n", b->pixels, bStart );

    int x, y;
    for ( y = 0; y < height; y ++ ) {
        //printf( "row\n" );
        for ( x = 0; x < width; x ++ ) {
            int aAlpha =  aMask & (aStart[ x + y * aPitch ]);
            if ( aAlpha >  aThresh ) {
                int bAlpha = bMask & (bStart[ x + y * bPitch ]);
                if ( bAlpha > bThresh ) {
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
    //printf( "Pixels location %p %d %d\n", surface->pixels, x, y );
    Uint32 value = ( (Uint32*) (surface->pixels) )[ x + y * surface->pitch / 4 ];
    SDL_UnlockSurface( surface );

    //printf( "Pixel color : %x %x\n", value, surface->format->Amask );
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
    //printf( "Pixels location %p (%d,%d)\n", surface->pixels, x, y );
    Uint32 value = ( (Uint32*) (surface->pixels) )[ x + y * surface->pitch / 4 ];
    SDL_UnlockSurface( surface );

    //printf( "Pixel color : %x\n", value );

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

    //printf( "R : %d, %x, %d\n", (value & surface->format->Rmask) >> surface->format->Rshift, surface->format->Rmask, surface->format->Rshift );
    //printf( "G : %d, %x, %d\n", (value & surface->format->Gmask) >> surface->format->Gshift, surface->format->Gmask, surface->format->Gshift );
    //printf( "B : %d, %x, %d\n", (value & surface->format->Bmask) >> surface->format->Bshift, surface->format->Bmask, surface->format->Bshift );
}



