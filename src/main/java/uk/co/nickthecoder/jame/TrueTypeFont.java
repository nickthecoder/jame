/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * TrueTypeFont allows text to be drawn onto Surfaces.
 * <p>
 * Each instance of this class has a font typeface and a point size. Therefore if you want to use
 * the same font typeface at different font sizes, you need multiple TrueTypeFont instances.
 * <p>
 * Rendering text to the display is a two part process. First you render the text using
 * {@link #renderBlended(String, RGBA)}, {@link #renderShaded(String, RGBA, RGBA)} or
 * {@link #renderSolid(String, RGBA)} and then you blit the resulting surface to its final
 * destination. Don't forget to free the temporary surface after you have finished with it.
 * <p>
 * To avoid problems with zero width surfaces, if the rendered text is blank, then the result is a
 * transparent Surface 1 pixel wide and the appropriate height for the font.
 */
public class TrueTypeFont
{
    static {
        trueTypeFont_init();
    }

    private native static void trueTypeFont_init();

    private long pFont;

    /**
     * Creates a TrueTypeFont instance from a ".ttf" file and a point size.
     * 
     * @param filename
     *        The filename of the ".ttf" file.
     * @param pointSize
     *        The size of the font in points.
     * @throws JameException
     */
    public TrueTypeFont( String filename, int pointSize ) throws JameException
    {
        Jame.checkStatus(this.trueTypeFont_open(filename, pointSize));
    }

    private native int trueTypeFont_open( String filename, int pointSize );

    /**
     * Releases the resources used by this TrueTypeFont. This will automatically be called when this
     * object is garbage collected, but is good practice to close it sooner if possible. Once
     * closed, do not attempt to reuse this object.
     */
    public void close()
    {
        if (this.pFont != 0) {
            this.trueTypeFont_close(this.pFont);
        }
    }

    private native void trueTypeFont_close( long pFont );

    /**
     * Uses {@link #close()} to release the resources.
     */
    protected void finalise()
    {
        this.close();
    }

    /**
     * Get the maximum pixel height of all glyphs of the loaded font. You may use this height for
     * rendering text as close together vertically as possible, though adding at least one pixel
     * height to it will space it so they can't touch. Remember that this class doesn't handle
     * multiline printing, so you are responsible for line spacing, see the getLineHeight as well.
     */
    public int getHeight()
    {
        return trueTypeFont_getHeight(this.pFont);
    }

    private native int trueTypeFont_getHeight( long pFont );

    /**
     * Get the maximum pixel ascent of all glyphs of the loaded font. This can also be interpreted
     * as the distance from the top of the font to the baseline.
     */
    public int getAscent()
    {
        return trueTypeFont_getAscent(this.pFont);
    }

    private native int trueTypeFont_getAscent( long pFont );

    /**
     * Get the maximum pixel descent of all glyphs of the loaded font. This can also be interpreted
     * as the distance from the baseline to the bottom of the font.
     */
    public int getDescent()
    {
        return trueTypeFont_getDescent(this.pFont);
    }

    private native int trueTypeFont_getDescent( long pFont );

    /**
     * Get the recommended pixel height of a rendered line of text of the loaded font. This is
     * usually larger than getHeight().
     */
    public int getLineHeight()
    {
        return trueTypeFont_getLineHeight(this.pFont);
    }

    private native int trueTypeFont_getLineHeight( long pFont );

    /**
     * Calculate the resulting surface width of the text rendered using font. No actual rendering is
     * done, however correct kerning is done to get the actual width. As with all methods in the
     * class, this does NOT handle multi-line text, it does not expect to see line breaks in the
     * text.
     * 
     * @param text
     *        A single line of text
     * @return The width of the text if it were rendered.
     */
    public int sizeText( String text )
    {
        return trueTypeFont_sizeText(this.pFont, text);
    }

    private native int trueTypeFont_sizeText( long pFont, String text );

    /**
     * Renders text to a new Surface without any anti-aliasing. The results will look poor.
     * 
     * @throws JameRuntimeException
     */
    public Surface renderSolid( String text, RGBA color ) throws JameRuntimeException
    {
        if (text.equals("")) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus(this.trueTypeFont_renderSolid(this.pFont, surface, text, color.r,
            color.g, color.b));
        return surface;
    }

    private native int trueTypeFont_renderSolid( long pFont, Surface surface, String text, int red,
        int green, int blue );

    /**
     * Renders text to a new Surface using the best quality anti-aliasing.
     * 
     * @throws JameRuntimeException
     */
    public Surface renderBlended( String text, RGBA color ) throws JameRuntimeException
    {
        if (text.equals("")) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus(this.trueTypeFont_renderBlended(this.pFont, surface, text, color.r,
            color.g, color.b));
        return surface;
    }

    private native int trueTypeFont_renderBlended( long pFont, Surface surface, String text,
        int red, int green, int blue );

    /**
     * Renders text to a new Surface. The quality will be better than
     * {@link #renderSolid(String, RGBA)}, but not as good as {@link #renderBlended(String, RGBA)}.
     * 
     * @throws JameRuntimeException
     */
    public Surface renderShaded( String text, RGBA fg, RGBA bg ) throws JameRuntimeException
    {
        if (text.equals("")) {
            return this.createEmpty();
        }
        Surface surface = new Surface();
        Jame.checkRuntimeStatus(this.trueTypeFont_renderShaded(this.pFont, surface, text, fg.r,
            fg.g, fg.b, bg.r, bg.g, bg.b));
        return surface;
    }

    private native int trueTypeFont_renderShaded( long pFont, Surface surface, String text,
        int fgr, int fgg, int fgb, int bgr, int bgg, int bgb );

    private Surface createEmpty() throws JameRuntimeException
    {
        Surface surface = new Surface();
        Jame.checkRuntimeStatus(this.trueTypeFont_renderSolid(this.pFont, surface, "X", 0, 0, 0));
        Surface result = new Surface(1, surface.getHeight(), true);
        surface.free();

        return result;
    }
}
