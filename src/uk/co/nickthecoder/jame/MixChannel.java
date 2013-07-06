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
        return sound;
    }
    
    public boolean isPlaying( Sound sound )
    {
        if ( sound != this.sound ) {
            return false;
        }
        return isPlaying();
    }
    
    public boolean isPlaying()
    {
        return Audio.isPlaying( this.mixChannelNumber );
    }
    
}
