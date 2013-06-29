package uk.co.nickthecoder.jame;

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
    }

    private static native int audio_open( int frequency, int format, int channels, int chunkSize );

}
