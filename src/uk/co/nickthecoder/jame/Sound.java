package uk.co.nickthecoder.jame;

public class Sound
{
    private long pSound;

    public Sound( String filename ) throws JameException
    {
        Jame.checkStatus( this.sound_load( filename ) );
    }

    private native int sound_load( String filename );

    public void free()
    {
        if ( this.pSound != 0 ) {
            this.sound_free( this.pSound );
            this.pSound = 0;
        }
    }

    private native void sound_free( long pSound );

    public void finalise()
    {
        this.free();
    }

    public void play()
    {
        this.sound_play( this.pSound );
    }

    private native int sound_play( long pSound );

}
