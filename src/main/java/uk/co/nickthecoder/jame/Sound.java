/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

public class Sound
{
    private long pSound;

    private int latestMixChannel = -1;

    public Sound( String filename ) throws JameException
    {
        Jame.checkStatus(this.native_load(filename));
    }

    private native int native_load( String filename );

    public void free()
    {
        if (this.pSound != 0) {
            this.native_free(this.pSound);
            this.pSound = 0;
        }
    }

    private native void native_free( long pSound );

    public void finalise()
    {
        this.free();
    }

    /**
     * If already playing, then nothing happens, otherwise the sound is played in the normal manner.
     */
    public boolean playOnce()
    {
        if (!this.isPlaying()) {
            return this.play();
        }
        return false;
    }

    public boolean play()
    {
        int result = this.native_play(this.pSound);
        if (result >= 0) {
            this.latestMixChannel = result;
            Audio.mixChannels.get(this.latestMixChannel).sound = this;
            return true;
        }
        return false;
    }

    private native int native_play( long pSound );

    public MixChannel getMixChannel()
    {
        if (this.latestMixChannel < 0) {
            return null;
        } else {
            MixChannel result = Audio.mixChannels.get(this.latestMixChannel);
            if (result.sound == this) {
                return result;
            } else {
                this.latestMixChannel = -1;
                return null;
            }
        }
    }

    /**
     * @return true if the sound is currently playing.
     */
    public boolean isPlaying()
    {
        MixChannel mixChannel = getMixChannel();
        if (mixChannel == null) {
            return false;
        } else {
            return Audio.isPlaying(mixChannel.getMixChannelNumber());
        }
    }

    public void stop()
    {
        MixChannel mixChannel = getMixChannel();
        if (mixChannel != null) {
            mixChannel.stop();
        }
    }

    public void fadeOut( int milliseconds )
    {
        MixChannel mixChannel = getMixChannel();
        if (mixChannel != null) {
            mixChannel.fadeOut(milliseconds);
        }
    }

}
