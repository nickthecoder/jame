/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * You can play multiple sounds simultaneously, each sound being played is allocated to a
 * MixChannel. There are a limited number of MixChannels, which you can specify using
 * {@link Audio#setMixChannels(int)}.
 * 
 * Many games need not concern themselves with the details of which MixChannel is being used for
 * each sound. It does become useful if you want to fade a given sound out, or want to stop a give
 * sound. To do this, play the sound as usual, using {@link Sound#play()}, and then find out which
 * MixChannel was used to play it : {@link Sound#getMixChannel()}. Later, you can the choose to
 * fade, or stop the sound. First, check that the sound is still being played on that channel :
 * {@link #isPlaying(Sound)}, and then use {@link #stop}, or {@link #fadeOut(int)}.
 */
public final class MixChannel
{
    private int mixChannelNumber;

    /**
     * The latest Sound to be played on this MixChannel. The sound may have finished playing.
     */
    Sound sound = null;

    /**
     * Mix channels are created internally when the audio subsystem is initialise, and when the
     * number of MixChannels changes. ({@link Audio#setMixChannels(int)}).
     * 
     * @param number
     *        The index of this MixChannel within the global array held by {@link Audio}.
     */
    MixChannel( int number )
    {
        this.mixChannelNumber = number;
    }

    /**
     * @return The sound that was last played on this MixChannel.
     */
    public Sound getLatestSound()
    {
        return this.sound;
    }

    /**
     * Tests whether the given Sound is currently playing on the MixChannel.
     * 
     * @param sound
     * @return true if the latest sound was the one specified, and it is still playing.
     */
    public boolean isPlaying( Sound sound )
    {
        if (sound != this.sound) {
            return false;
        }
        return isPlaying();
    }

    /**
     * Is this MixChannel in use?
     * 
     * @return true is a sound is currently playing on this MixChannel.
     */
    public boolean isPlaying()
    {
        return Audio.isPlaying(this.mixChannelNumber);
    }

    /**
     * Each MixChannel is assigned a unique number (which is an index into an array).
     * 
     * @return This MixChannels index number.
     */
    public int getMixChannelNumber()
    {
        return this.mixChannelNumber;
    }

    /**
     * If this MixChannel is playing, then it is stopped abruptly. See also {@link #fadeOut(int)}.
     */
    public void stop()
    {
        Audio.stop(this.mixChannelNumber);
    }

    /**
     * If this MixChannel is playing, then fade it out over the specified time.
     * 
     * @param milliseconds
     *        The number of milliseconds before the sound on this MixChannel will be silent.
     */
    public void fadeOut( int milliseconds )
    {
        Audio.fadeOut(this.mixChannelNumber, milliseconds);
    }
}
