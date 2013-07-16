/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import java.util.ArrayList;

public class Audio
{
	
	public enum SampleFormat {
		U8( 0x0008 ),
		S8( 0x8008 ),
		U16LSB( 0x0010 ),
		S16LSB( 0x8010 ),
		U16MSB( 0x1010 ),
		S16MSB( 0x9010 );

		/** The same as U16LSB */
		public static final SampleFormat U16 = U16LSB;

		/** The same as S16LSB */
		public static final SampleFormat S16 = S16LSB;
		
		public final int code;
		
		SampleFormat( int code )
		{
			this.code = code;
		}
		
		public String toString()
		{
			return super.toString() + " (" + this.code + ")";
		}
	}
    
	static ArrayList<MixChannel> mixChannels = null;
	
    public static void init()
        throws JameException
    {
        Jame.initSubsystem( Jame.Subsystem.AUDIO );
    }

    public static void open()
    {
        open( 22050, SampleFormat.S16, 2, 2048 );
    }

    public static void open( int frequency, SampleFormat format, int channels, int chunkSize )
    {
        audio_open( frequency, format.code, channels, chunkSize );
        setMixChannels( 8 );
    }

    private static native int audio_open( int frequency, int format, int channels, int chunkSize );

    public static void setMixChannels( int required )
    {
        int actual = audio_setMixChannels( required );
        ArrayList<MixChannel> previous = mixChannels;
        
        mixChannels = new ArrayList<MixChannel>( actual );
        for ( int i = 0; i < actual; i ++ ) {
            if ( (previous != null) && ( i < previous.size() ) ) {
                mixChannels.add( previous.get( i ) );
            } else {
                MixChannel mixChannel = new MixChannel( i );
                mixChannels.add( mixChannel );
            }
        }
    }
    
    private static native int audio_setMixChannels( int mixChannels );

    public static boolean isPlaying( int mixChannelNumber ) 
    {
        if ( (mixChannelNumber < 0) || ( mixChannelNumber > mixChannels.size()) ) {
            return false;
        }
        return audio_isPlaying( mixChannelNumber ) == 1;
    }
    private static native int audio_isPlaying( int mixChannelNumber );
    
}
