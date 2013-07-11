package uk.co.nickthecoder.jame;

public final class RGBA
{
    public final int r;
    public final int g;
    public final int b;
    public final int a;

    /**
     * Parses colors in the form #rrggbbaa #rrggbb and #rgb
     *
     * @param str
     * @return
     * @throws JameException
     */
    public static RGBA parse( String str )
        throws JameException
    {
        str = str.trim().toUpperCase();

        if ( str.startsWith( "#" ) ) {
            if ( str.length() == 9 ) {
                long color = Long.parseLong( str.substring( 1 ), 16 );
                return new RGBA( (int) ((color & 0xff000000) >> 24), (int) ((color & 0x00ff0000 ) >> 16),
                    (int) ((color & 0x0000ff00) >> 8), (int) (color & 0x000000ff) );
            } else if ( str.length() == 7 ) {
                int color = Integer.parseInt( str.substring( 1 ), 16 );
                return new RGBA( ( color & 0xff0000 ) >> 16, ( color & 0x00ff00 ) >> 8, color & 0x0000ff );
            } else if ( str.length() == 4 ) {
                int color = Integer.parseInt( str.substring( 1 ), 16 );
                return new RGBA( ( ( color & 0xf00 ) >> 8 ) * 17, ( ( color & 0x0f0 ) >> 4 ) * 17, ( color & 0xf ) * 17 );
            }
        }
        throw new JameException( "Color parse failed : " + str );
    }

    public RGBA( RGBA other )
    {
        this( other.r, other.g, other.b, other.a );
    }

    public RGBA( int red, int green, int blue )
    {
        this( red, green, blue, 255 );
    }

    public RGBA( int red, int green, int blue, int alpha )
    {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    public String getRGBCode()
    {
        return "#" + ( this.r < 16 ? "0" : "" ) + Integer.toHexString( this.r ) + ( this.g < 16 ? "0" : "" )
                + Integer.toHexString( this.g ) + ( this.b < 16 ? "0" : "" ) + Integer.toHexString( this.b );
    }

    public String getRGBACode()
    {
        return this.getRGBCode() + ( this.a < 16 ? "0" : "" ) + Integer.toHexString( this.a );
    }

    @Override
    public String toString()
    {
        return this.getRGBACode();
    }

}
