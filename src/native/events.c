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
#include <SDL_mouse.h>
#include "include/uk_co_nickthecoder_jame_Events.h"


JNIEXPORT jobject JNICALL Java_uk_co_nickthecoder_jame_Events_native_1poll
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
                        
            jfieldID fid;
            
            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            return jevent;
        
        } else if ( type == SDL_WINDOWEVENT ) {
             
            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/WindowEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;
            
            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            fid = (*env)->GetFieldID(env,subClass,"windowEventTypeInt","I");
            (*env)->SetIntField(env,jevent,fid, e.window.event);

            fid = (*env)->GetFieldID(env,subClass,"windowID","I");
            (*env)->SetIntField(env,jevent,fid, e.window.windowID);

            return jevent;
        
        } else if ( (type == SDL_KEYDOWN) || (type == SDL_KEYUP) ) {
            //printf( "Key up or down event\n" );

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/KeyboardEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;
            
            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            fid = (*env)->GetFieldID(env,subClass,"windowID","I");
            (*env)->SetIntField(env,jevent,fid, e.key.windowID);

            fid = (*env)->GetFieldID(env,subClass,"pressed","Z");
            (*env)->SetBooleanField(env,jevent,fid, e.key.state !=0);

            fid = (*env)->GetFieldID(env,subClass,"scanCode","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.scancode);

            fid = (*env)->GetFieldID(env,subClass,"symbol","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.sym);

            fid = (*env)->GetFieldID(env,subClass,"modifiers","I");
            (*env)->SetIntField(env,jevent,fid, e.key.keysym.mod);
            
            fid = (*env)->GetFieldID(env,subClass,"repeated","Z");
            (*env)->SetBooleanField(env,jevent,fid,e.key.repeat != 0);
            
            return jevent;

        } else if ( (type == SDL_MOUSEBUTTONDOWN) || (type == SDL_MOUSEBUTTONUP) ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/MouseButtonEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            fid = (*env)->GetFieldID(env,subClass,"windowID","I");
            (*env)->SetIntField(env,jevent,fid, e.button.windowID);

            fid = (*env)->GetFieldID(env,subClass,"mouseID","I");
            (*env)->SetIntField(env,jevent,fid, e.button.which);

            fid = (*env)->GetFieldID(env,subClass,"button","I");
            (*env)->SetIntField(env,jevent,fid, e.button.button);

            fid = (*env)->GetFieldID(env,subClass,"pressed","Z");
            (*env)->SetBooleanField(env,jevent,fid, e.button.state != 0);

            fid = (*env)->GetFieldID(env,subClass,"x","I");
            (*env)->SetIntField(env,jevent,fid, e.button.x);

            fid = (*env)->GetFieldID(env,subClass,"y","I");
            (*env)->SetIntField(env,jevent,fid, e.button.y);

            return jevent;
            
        } else if ( type == SDL_MOUSEWHEEL ) {
        
            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/MouseWheelEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            fid = (*env)->GetFieldID(env,subClass,"windowID","I");
            (*env)->SetIntField(env,jevent,fid, e.wheel.windowID);

            fid = (*env)->GetFieldID(env,subClass,"x","I");
            (*env)->SetIntField(env,jevent,fid, e.wheel.x);

            fid = (*env)->GetFieldID(env,subClass,"y","I");
            (*env)->SetIntField(env,jevent,fid, e.wheel.y);

            /*
            fid = (*env)->GetFieldID(env,subClass,"flipped","Z");
            (*env)->SetBooleanField(env,jevent,fid, e.wheel.direction != 0);
            */
            
            return jevent;

        } else if ( type == SDL_MOUSEMOTION ) {

            jclass subClass = (*env)->FindClass( env, "uk/co/nickthecoder/jame/event/MouseMotionEvent" );
            //printf( "Found class %p\n", subClass );
            jobject jevent = (*env)->AllocObject( env, subClass );
            //printf( "Created instance %p\n", jevent );

            jfieldID fid;

            fid = (*env)->GetFieldID(env,subClass,"timestamp","I");
            (*env)->SetIntField(env,jevent,fid, e.common.timestamp);

            fid = (*env)->GetFieldID(env,subClass,"windowID","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.windowID);

            fid = (*env)->GetFieldID(env,subClass,"x","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.x);

            fid = (*env)->GetFieldID(env,subClass,"y","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.y);

            fid = (*env)->GetFieldID(env,subClass,"relativeX","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.xrel);

            fid = (*env)->GetFieldID(env,subClass,"relativeY","I");
            (*env)->SetIntField(env,jevent,fid, e.motion.yrel);

            return jevent;

        }
        
    }
}

JNIEXPORT void JNICALL Java_uk_co_nickthecoder_jame_Events_native_1keyboardRepeat
  (JNIEnv *env, jclass jevents, jint delay, jint repeat )
{
	SDL_EnableKeyRepeat( delay, repeat );
}


