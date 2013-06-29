#include <jni.h>
#include <SDL.h>
#include <SDL_ttf.h>
#include "trueTypeFont.h"

// init
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1init
  (JNIEnv *env, jclass class )
{
    TTF_Init();
}

// open
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1open
  (JNIEnv *env, jobject jfont, jstring jfilename, jint pointSize )
{
    const char *filename = (*env)->GetStringUTFChars(env, jfilename, 0);

    TTF_Font *font = TTF_OpenFont( filename, pointSize );    
    if ( font ) {

        jclass clazz = (*env)->GetObjectClass(env, jfont);
        jfieldID fid;

        fid = (*env)->GetFieldID(env,clazz,"pFont","J");
        (*env)->SetLongField(env,jfont,fid, (jlong) (intptr_t) font);
    }


    (*env)->ReleaseStringUTFChars(env, jfilename, filename);
    return font == 0;
}

// close
JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1close
  (JNIEnv *env, jobject jfont, jlong pFont )
{
    TTF_Font *font = (TTF_Font*) (intptr_t) pFont;

    TTF_CloseFont( font );
}

// renderSolid
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1renderSolid
  (JNIEnv *env, jobject jfont, jlong pFont, jobject jsurface, jstring jtext, jint red, jint green, jint blue )
{
    TTF_Font *font = (TTF_Font*) (intptr_t) pFont;
    SDL_Color color = { .r=red, .g=green, .b= blue };

    const char *text = (*env)->GetStringUTFChars(env, jtext, 0);

    SDL_Surface *surface = TTF_RenderUTF8_Solid( font, text, color );
    initialiseSurface( env, jsurface, surface );

    (*env)->ReleaseStringUTFChars(env, jtext, text);
    return surface == NULL;
}
// renderBlended
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1renderBlended
  (JNIEnv *env, jobject jfont, jlong pFont, jobject jsurface, jstring jtext, jint red, jint green, jint blue )
{
    TTF_Font *font = (TTF_Font*) (intptr_t) pFont;
    SDL_Color color = { .r=red, .g=green, .b= blue };

    const char *text = (*env)->GetStringUTFChars(env, jtext, 0);

    SDL_Surface *surface = TTF_RenderUTF8_Blended( font, text, color );
    initialiseSurface( env, jsurface, surface );

    (*env)->ReleaseStringUTFChars(env, jtext, text);
    return surface == NULL;
}

// renderShaded
JNIEXPORT jint JNICALL Java_uk_co_nickthecoder_jame_TrueTypeFont_trueTypeFont_1renderShaded
  (JNIEnv *env, jobject jfont, jlong pFont, jobject jsurface, jstring jtext, jint fgr, jint fgg, jint fgb, jint bgr, jint bgg, jint bgb )
{
    TTF_Font *font = (TTF_Font*) (intptr_t) pFont;
    SDL_Color fg = { .r=fgr, .g=fgg, .b=fgb };
    SDL_Color bg = { .r=bgr, .g=bgg, .b=bgb };

    const char *text = (*env)->GetStringUTFChars(env, jtext, 0);

    SDL_Surface *surface = TTF_RenderUTF8_Shaded( font, text, fg, bg );
    initialiseSurface( env, jsurface, surface );

    (*env)->ReleaseStringUTFChars(env, jtext, text);
    return surface == NULL;
}


