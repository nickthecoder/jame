/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

class MixChannel
{
    private int mixChannelNumber;

    public Sound sound = null;

    public MixChannel( int number )
    {
        this.mixChannelNumber = number;
    }

    public Sound getLatestSound()
    {
        return this.sound;
    }

    public boolean isPlaying( Sound sound )
    {
        if (sound != this.sound) {
            return false;
        }
        return isPlaying();
    }

    public boolean isPlaying()
    {
        return Audio.isPlaying(this.mixChannelNumber);
    }

    public int getMixChannelNumber()
    {
        return this.mixChannelNumber;
    }

    public void stop()
    {
        Audio.stop(this.mixChannelNumber);
    }

    public void fadeOut( int milliseconds )
    {
        Audio.fadeOut(this.mixChannelNumber, milliseconds);
    }
}
