/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

public class Jame
{
    static {
        System.loadLibrary( "jame" );
    }

    enum Subsystem {
    	TIMER( 0x00000001 ),
    	AUDIO( 0x00000010 ),
    	VIDEO( 0x00000020 ),
    	CDROM( 0x00000100 ),
    	JOYSTICK( 0x00000200 );

    	public final int code;
    	
    	Subsystem( int code )
    	{
    		this.code = code;
    	}
    }
    
    private static boolean initialised = false;

    static void init()
    		throws JameRuntimeException
    {
        if ( !initialised ) {
            Jame.checkRuntimeStatus( jame_init() );
            initialised = true;
        }
    }

    private static native int jame_init();

    static void initSubsystem( Subsystem subsystem )
    		throws JameRuntimeException
    {
        init();
        Jame.checkRuntimeStatus( jame_initSubsystem( subsystem.code ) );
    }

    private static native int jame_initSubsystem( int subsystem );

    static void checkStatus( int returnValue )
            throws JameException
    {
        if ( returnValue != 0 ) {
            String message;
            try {
                message = jame_getError();
            } catch ( Exception e ) {
                throw new JameException();
            }
            throw new JameException( message );
        }
    }

    static void checkRuntimeStatus( int returnValue )
    {
        if ( returnValue != 0 ) {
            String message;
            try {
                message = jame_getError();
            } catch ( Exception e ) {
                throw new JameRuntimeException();
            }
            throw new JameRuntimeException( message );
        }
    }

    static void checkNull( Object obj )
            throws JameException
    {
        if ( obj == null ) {
            String message;
            try {
                message = jame_getError();
            } catch ( Exception e ) {
                throw new JameException();
            }
            throw new JameException( message );
        }
    }

    static void checkNullRuntime( Object obj )
            throws JameRuntimeException
    {
        if ( obj == null ) {
            String message;
            try {
                message = jame_getError();
            } catch ( Exception e ) {
                throw new JameRuntimeException();
            }
            throw new JameRuntimeException( message );
        }
    }

    private static native String jame_getError();

}
