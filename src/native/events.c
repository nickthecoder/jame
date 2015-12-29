/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
#include <jni.h>
#include <SDL.h>
#include <SDL_events.h>
#include "include/uk_co_nickthecoder_jame_Events.h"


JNIEXPORT jobject JNICALL Java_uk_co_nickthecoder_jame_Events_events_1poll
  (JNIEnv *env, jclass eventsClass )
{
    SDL_Event e;
    while ( 1 ) {
        int status = SDL_PollEvent( &e );
        
        if ( status == 0 ) {
            return 0; // How do I return a null jobject?
        }

        int type = e.type;
        //printf( "Found event type %d\n", e.type );

        if ( type == SDL_QUIT ) {
            //printf( "Quit event\n" );
            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/QuitEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );
            return jevent;

        } else if ( type == SDL_ACTIVEEVENT ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/WindowEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"gain","I");
            (*env)->SetIntField(env,jevent,fid, e.active.gain);

            fid = (*env)->GetFieldID(env,subClass,"type","I");
            (*env)->SetIntField(env,jevent,fid, e.active.state);

            return jevent;

        } else if ( (type == SDL_KEYDOWN) || (type == SDL_KEYUP) ) {
            //printf( "Key up or down event\n" );

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/KeyboardEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"state","I");
            (*env)->SetIntField(env,jevent,fid, e.key.state);

            fid = (*env)->GetFieldID(env,subClass,"scanCode","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.scancode);

            fid = (*env)->GetFieldID(env,subClass,"symbol","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.sym);

            fid = (*env)->GetFieldID(env,subClass,"modifiers","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.mod);

            fid = (*env)->GetFieldID(env,subClass,"c","C");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.unicode);

            return jevent;

        } else if ( (type == SDL_MOUSEBUTTONDOWN) || (type == SDL_MOUSEBUTTONUP) ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/MouseButtonEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"which","I");
            (*env)->SetIntField(env,jevent,fid, e.button.which);

            fid = (*env)->GetFieldID(env,subClass,"button","I");
            (*env)->SetIntField(env,jevent,fid, e.button.button);

            fid = (*env)->GetFieldID(env,subClass,"state","I");
            (*env)->SetIntField(env,jevent,fid, e.button.state);

            fid = (*env)->GetFieldID(env,subClass,"x","I");
            (*env)->SetIntField(env,jevent,fid, e.button.x);

            fid = (*env)->GetFieldID(env,subClass,"y","I");
            (*env)->SetIntField(env,jevent,fid, e.button.y);

            return jevent;


        } else if ( type == SDL_MOUSEMOTION ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/MouseMotionEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"state","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.state);

            fid = (*env)->GetFieldID(env,subClass,"x","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.x);

            fid = (*env)->GetFieldID(env,subClass,"y","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.y);

            fid = (*env)->GetFieldID(env,subClass,"relativeX","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.xrel);

            fid = (*env)->GetFieldID(env,subClass,"relativeY","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.yrel);

            return jevent;

        } else if ( type == SDL_VIDEORESIZE ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/ResizeEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"width","I");
            (*env)->SetIntField(env,jevent,fid, e.resize.w);

            fid = (*env)->GetFieldID(env,subClass,"height","I");
            (*env)->SetIntField(env,jevent,fid, e.resize.h);

            return jevent;
        }
    }
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Events_events_1enableUnicode
  (JNIEnv *env, jclass jevents, jboolean value )
{
    SDL_EnableUNICODE( value );
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Events_events_1keyboardRepeat
  (JNIEnv *env, jclass jevents, jint delay, jint repeat )
{
	SDL_EnableKeyRepeat( delay, repeat );
}


