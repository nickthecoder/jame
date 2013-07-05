package uk.co.nickthecoder.jame;

public class Sound
{
    private long pSound;

    private int latestMixChannel = -1;

    public Sound( String filename ) throws JameException
    {
        Jame.checkStatus(this.sound_load(filename));
    }

    private native int sound_load( String filename );

    public void free()
    {
        if (this.pSound != 0) {
            this.sound_free(this.pSound);
            this.pSound = 0;
        }
    }

    private native void sound_free( long pSound );

    public void finalise()
    {
        this.free();
    }

    /**
     * If already playing, then nothing happens, otherwise the sound is played in the normal manner.
     */
    public void playOnce()
    {
        if ( ! this.isPlaying() ) {
            this.play();
        }
    }
    
    public void play()
    {
        this.latestMixChannel = this.sound_play(this.pSound);
        Audio.mixChannels.get(this.latestMixChannel).sound = this;
    }

    private native int sound_play( long pSound );

    /**
     * @return true if the sound is currently playing.
     */
    public boolean isPlaying()
    {
        if (this.latestMixChannel < 0) {
            return false;
        }
        try {
            return Audio.mixChannels.get(this.latestMixChannel).isPlaying(this);
        } catch (Exception e) {
            return false;
        }
    }
}
