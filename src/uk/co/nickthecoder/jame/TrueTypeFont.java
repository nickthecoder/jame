package uk.co.nickthecoder.jame;

public class TrueTypeFont
{
    static {
        trueTypeFont_init();
    }

    private native static void trueTypeFont_init();

    private long pFont;

    public TrueTypeFont( String filename, int pointSize ) throws JameException
    {
        Jame.checkStatus( this.trueTypeFont_open( filename, pointSize ) );
    }

    private native int trueTypeFont_open( String filename, int pointSize );

    public void close()
    {
        if ( this.pFont != 0 ) {
            this.trueTypeFont_close( this.pFont );
        }
    }

    private native void trueTypeFont_close( long pFont );

    public void finalise()
    {
        this.close();
    }

    public Surface renderSolid( String text, RGBA color )
        throws JameRuntimeException
    {
        if ( text.equals( "" ) ) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus( this.trueTypeFont_renderSolid( this.pFont, surface, text, color.r, color.g, color.b ) );
        return surface;
    }

    private native int trueTypeFont_renderSolid( long pFont, Surface surface, String text, int red, int green, int blue );

    public Surface renderBlended( String text, RGBA color )
        throws JameRuntimeException
    {
        if ( text.equals( "" ) ) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus( this.trueTypeFont_renderBlended( this.pFont, surface, text, color.r, color.g, color.b ) );
        return surface;
    }

    private native int trueTypeFont_renderBlended( long pFont, Surface surface, String text, int red, int green,
        int blue );

    public Surface renderShaded( String text, RGBA fg, RGBA bg )
        throws JameRuntimeException
    {
        if ( text.equals( "" ) ) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus( this
            .trueTypeFont_renderShaded( this.pFont, surface, text, fg.r, fg.g, fg.b, bg.r, bg.g, bg.b ) );
        return surface;
    }

    private native int trueTypeFont_renderShaded( long pFont, Surface surface, String text, int fgr, int fgg, int fgb,
        int bgr, int bgg, int bgb );

    private Surface createEmpty()
        throws JameRuntimeException
    {
        Surface surface = new Surface();
        Jame.checkRuntimeStatus( this.trueTypeFont_renderSolid( this.pFont, surface, "X", 0, 0, 0 ) );
        Surface result = new Surface( 1, surface.getHeight(), true );
        surface.free();

        return result;
    }
}
