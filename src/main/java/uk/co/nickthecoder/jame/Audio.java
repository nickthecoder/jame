/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import java.util.ArrayList;

/**
 * To play sounds, you first need to initialise Audio by calling {@link #init()}.
 * <p>
 * You can then optionally set the number of mixer channels using {@link #setMixChannels(int)}. This
 * is the maximum number of sounds that can be played simultaneously. The default is 8.
 * <p>
 * To load and play sounds, see {@link Sound}.
 * 
 */
public class Audio
{
    /**
     * Defines the low-level storage mechanism for a sample within a Sound.
     */
    public enum SampleFormat
    {
        U8(0x0008),
        S8(0x8008),
        U16LSB(0x0010),
        S16LSB(0x8010),
        U16MSB(0x1010),
        S16MSB(0x9010);

        /** The same as U16LSB */
        public static final SampleFormat U16 = U16LSB;

        /** The same as S16LSB */
        public static final SampleFormat S16 = S16LSB;

        public final int code;

        SampleFormat( int code )
        {
            this.code = code;
        }

        @Override
        public String toString()
        {
            return super.toString() + " (" + this.code + ")";
        }
    }

    static ArrayList<MixChannel> mixChannels = null;

    /**
     * Initialises the audio subsystem. This loads Jame's JNI shared object / dynamic linked
     * library. This must be called before any other calls to Audio or Sound. After init, you still
     * need to open the sound device using either of the {@link #open} methods.
     * 
     * @throws JameException
     */
    public static void init()
        throws JameException
    {
        Jame.initSubsystem(Jame.Subsystem.AUDIO);
    }

    /**
     * Opens the audio device using default values : Frequency : 22050. Sample format : S16. Channels
     * : 2 (stereo). ChunkSize : 2048
     */
    public static void open()
    {
        open(22050, SampleFormat.S16, 2, 2048);
    }

    /**
     * Opens the audio device with complete control over the details. In most cases the default
     * values suitable, so use {@link #open()} instead.
     * 
     * @param frequency
     *        The frequency of samples in (Hz).
     * @param format
     *        The storage type of each sample.
     * @param channels
     *        The number of channels (1 for mono, 2 for stereo).
     * @param chunkSize
     *        The size of the buffer. A small value may lower the latency, but will be more CPU
     *        intensive.
     */
    public static void open( int frequency, SampleFormat format, int channels, int chunkSize )
    {
        native_open(frequency, format.code, channels, chunkSize);
        setMixChannels(8);
    }

    private static native int native_open( int frequency, int format, int channels, int chunkSize );

    /**
     * Sets the maximum number of sounds that can be played simultaneously. The default is 8.
     * A large count will be more CPU intensive.
     * @param count The required number of mix channels.
     */
    public static void setMixChannels( int count )
    {
        int actual = native_setMixChannels(count);
        ArrayList<MixChannel> previous = mixChannels;

        mixChannels = new ArrayList<MixChannel>(actual);
        for (int i = 0; i < actual; i++) {
            if ((previous != null) && (i < previous.size())) {
                mixChannels.add(previous.get(i));
            } else {
                MixChannel mixChannel = new MixChannel(i);
                mixChannels.add(mixChannel);
            }
        }
    }

    private static native int native_setMixChannels( int mixChannels );

    /**
     * Used internally from other Jame classes to call the JNI method,
     * with simple bounds checking. 
     */
    static boolean isPlaying( int mixChannelNumber )
    {
        if ((mixChannelNumber < 0) || (mixChannelNumber > mixChannels.size())) {
            return false;
        }
        return native_isPlaying(mixChannelNumber) == 1;
    }

    private static native int native_isPlaying( int mixChannelNumber );

    /**
     * Used internally from other Jame classes to call the JNI method. 
     */
    static void stop( int mixChannelNumber )
    {
        native_stop(mixChannelNumber);
    }

    private static native void native_stop( int mixChannelNumber );

    /**
     * Used internally from other Jame classes to call the JNI method. 
     */
    static void fadeOut( int mixChannelNumber, int milliseconds )
    {
        native_fadeOut(mixChannelNumber, milliseconds);
    }

    private static native void native_fadeOut( int mixChannelNumber, int milliseconds );

    /**
     * Audio only contains static methods, so don't publish a constructor.
     */
    private Audio()
    {
    }
}
