/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * A Surface is a bitmap image. Before creating any Surfaces, you must
 * initialise the Video subsystem : Video.init().
 *
 */
public class Surface
{
	public enum BlendMode
	{
		NONE( -1 ),
	    COMPOSITE( 0 ),
	    RGB_ADD( 0x1 ),
	    RGB_SUB( 0x2 ),
	    RGB_MULT( 0x3 ),
	    RGB_MIN( 0x4 ),
	    RGB_MAX( 0x5 ),

	    RGBA_ADD( 0x6 ),
	    RGBA_SUB( 0x7 ),
	    RGBA_MULT( 0x8 ),
	    RGBA_MIN( 0x9 ),
	    RGBA_MAX( 0x10 );

	    public final int code;
	    BlendMode( int code )
	    {
	    	this.code = code;
	    }
	}

	
    private static final int SDL_SRCALPHA = 0x00010000;

    private long pSurface;

    private int width;

    private int height;

    private boolean hasAlpha;

    /**
     * We need to create blank Surface objects, which can then be filled in the
     * JNI calls such as surface_setMode.
     */
    Surface()
    {
    }


    public Surface( String filename )
		throws JameException
    {
        Jame.checkStatus( this.surface_load( filename ) );
    }

    private native int surface_load( String filename );

    public Surface( int width, int height, boolean alpha )
    		throws JameRuntimeException
    {
        this( width, height, alpha, true );
        this.hasAlpha = alpha;
    }

    public Surface( int width, int height, boolean alpha, boolean hardware )
    		throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_create( width, height, alpha, hardware ) );
        this.hasAlpha = alpha;
    }

    private native int surface_create( int width, int height, boolean alpha, boolean hardware );


    public boolean hasAlphaChannel()
    {
        return this.hasAlpha;
    }

    public Surface copy()
        throws JameRuntimeException
    {
        return this.convert();
    }

    public void free()
    {
        this.surface_free( this.pSurface );
        this.pSurface = 0;
    }

    private native int surface_free( long pSurface );

    @Override
    public void finalize()
    {
        if ( this.pSurface != 0 ) {
            this.free();
        }
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    /**
     * Used on RGB surfaces (not RGBA surfaces) to set the alpha value used during blitting.
     * This is a per-surface value, i.e. the same alpha value is used for all pixels.
     * 
     * Internally, this calls SDL's SDL_SetAlpha( surface, 0, 255 ) when alpha is 255,
     * and SDL_SetAlpha( surface, SDL_SRCALPHA, alpha ) for all other values of alpha.
     * 
     * @param alpha The alpha value from 0 (transparent) to 255 (opaque)
     * 
     * @throws JameRuntimeException
     */
    public void setPerSurfaceAlpha( int alpha )
        throws JameRuntimeException
    {
    	if ( this.hasAlpha ) {
    		throw new JameRuntimeException( "Cannot setPerSurfaceAlpha on RGBA surfaces" );
    	} else {
    		Jame.checkRuntimeStatus( this.surface_setAlpha( this.pSurface, alpha == 255 ? 0 : SDL_SRCALPHA, alpha ) );
    	}
    }

    /**
     * Used on RGBA surfaces (not RGB surfaces) to turn on/off the alpha channel while blitting
     * 
     * Internally, this calls SDL's setAlpha( 0, 255 ) when value is false, and setAlpha( SDL_SRC_ALPHA, 255 ) when value is true.
     * 
     * @param value
     * @throws JameRuntimeException
     */
    public void setAlphaEnabled( boolean value )
        throws JameRuntimeException
    {
    	if ( this.hasAlpha ) {
            Jame.checkRuntimeStatus( this.surface_setAlpha( this.pSurface, value ? SDL_SRCALPHA : 0, 255 ) );
    	} else {
    		throw new JameRuntimeException( "Cannot setAlphaEnabaled on RGB surfaces, only RGBA surfaces" );
    	}
    }
    
    /**
     * Exposes SDL's setAlpha, but IMHO, it is clearer to use setPerSurfaceAlpha for RGB surfaces, and
     * setAlphaEnabled for RGBA surfaces.
     * 
     * @param srcAlpha
     * @param alpha
     */
    public void setAlpha( boolean srcAlpha, int alpha )
    	throws JameRuntimeException
    {
    	Jame.checkRuntimeStatus( this.surface_setAlpha( this.pSurface, srcAlpha ? SDL_SRCALPHA : 0, alpha ) );
    }

    private native int surface_setAlpha( long pSurface, int flags, int alpha );


    /**
     * Only works with 32 bit surfaces.
     *
     * @param x
     * @param y
     * @return The value of a single pixel, stored in a single int
     */
    public int getPixelColor( int x, int y )
    {
        return this.surface_getPixelColor( this.pSurface, x, y );
    }

    private native int surface_getPixelColor( long pSurface, int x, int y );

    /**
     * Only works with 32 bit surfaces.
     *
     * @param x
     * @param y
     * @return The value of a single pixel, as an RGBA object.
     */
    public RGBA getPixelRGBA( int x, int y )
    {
        RGBA result = new RGBA( 0, 0, 0 );
        this.surface_getPixelRGBA( this.pSurface, result, x, y );
        return result;
    }

    private native void surface_getPixelRGBA( long pSurface, RGBA result, int x, int y );

    public void setPixel( int x, int y, int value )
    {
        this.surface_setPixel( this.pSurface, x, y, value );
    }
    
    private native void surface_setPixel( long pSurface, int x, int y, int value );
    
    public void setPixel( int x, int y, RGBA color )
    {
        this.surface_setPixel( this.pSurface, x, y, color.r, color.g, color.b, color.a );
    }
    
    private native void surface_setPixel( long pSurface, int x, int y, int r, int g, int b, int a );
    
    public void fill( int color )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_fill( this.pSurface, 0, 0, this.width, this.height, color ) );
    }

    public void fill( Rect rect, int color )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_fill( this.pSurface, rect.x, rect.y, rect.width, rect.height, color ) );
    }

    private native int surface_fill( long pSurface, int x, int y, int width, int height, int color );

    public void fill( RGBA color )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_fill2( this.pSurface, 0, 0, this.width, this.height, color.r, color.g, color.b,
            color.a ) );
    }

    public void fill( Rect rect, RGBA color )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_fill2( this.pSurface, rect.x, rect.y, rect.width, rect.height, color.r, color.g,
            color.b, color.a ) );
    }

    private native int surface_fill2( long pSurface, int x, int y, int width, int height, int red, int green, int blur,
        int alpha );

    public void flip()
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_flip( this.pSurface ) );
    }

    private native int surface_flip( long pSurface );

    public void blit( Surface dest )
        throws JameRuntimeException
    {
        this.blit( dest, 0, 0, BlendMode.NONE );
    }

    public void blit( Surface dest, BlendMode blendMode )
        throws JameRuntimeException
    {
        this.blit( dest, 0, 0, blendMode );
    }

    public void blit( Surface dest, int x, int y )
        throws JameRuntimeException
    {
        this.blit( dest, x, y, BlendMode.NONE );
    }

    public void blit( Surface dest, int x, int y, BlendMode blendMode )
        throws JameRuntimeException
    {
        if ( blendMode == BlendMode.NONE ) {
            Jame.checkRuntimeStatus( this.surface_blit( this.pSurface, dest.pSurface, x, y ) );
        } else {
            Jame.checkRuntimeStatus( this.surface_blit3( this.pSurface, 0, 0, this.getWidth(), this.getHeight(),
                dest.pSurface, x, y, this.getWidth(), this.getHeight(), blendMode.code ) );
        }
    }

    private native int surface_blit( long pSrc, long pDest, int x, int y );

    public void blit( Rect srcRect, Surface dest, Rect destRect )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_blit2( this.pSurface, srcRect.x, srcRect.y, srcRect.width, srcRect.height,
            dest.pSurface, destRect.x, destRect.y, destRect.width, destRect.height ) );
    }

    public void blit( Rect srcRect, Surface dest, int x, int y )
        throws JameRuntimeException
    {
        Jame.checkRuntimeStatus( this.surface_blit2( this.pSurface, srcRect.x, srcRect.y, srcRect.width, srcRect.height,
            dest.pSurface, x, y, srcRect.width, srcRect.height ) );
    }

    public void blit( Rect srcRect, Surface dest, int x, int y, BlendMode blendMode )
        throws JameRuntimeException
    {
        if ( blendMode == BlendMode.NONE ) {
            Jame.checkRuntimeStatus( this.surface_blit2( this.pSurface, srcRect.x, srcRect.y, srcRect.width, srcRect.height,
                dest.pSurface, x, y, srcRect.width, srcRect.height ) );
        } else {
            Jame.checkRuntimeStatus( this.surface_blit3( this.pSurface, srcRect.x, srcRect.y, srcRect.width, srcRect.height,
                dest.pSurface, x, y, srcRect.width, srcRect.height, blendMode.code ) );
        }
    }

    private native int surface_blit2( long pSrc, int sx, int sy, int swidth, int sheight, long pDest, int dx, int dy,
        int dwidth, int dheight );


    private native int surface_blit3( long pSrc, int sx, int sy, int swidth, int sheight, long pDest, int dx, int dy,
        int dwidth, int dheight, int flags );

    public Surface convert()
        throws JameRuntimeException
    {
        Surface result = new Surface();
        Jame.checkRuntimeStatus( result.surface_displayFormat( this.pSurface ) );
        return result;
    }

    private native int surface_displayFormat( long pSrc );

    public Surface zoom( double zoomX, double zoomY, boolean smooth )
        throws JameRuntimeException
    {
        Surface result = new Surface();

        Jame.checkRuntimeStatus( this.surface_zoom( result, this.pSurface, zoomX, zoomY, smooth ) );

        return result;
    }

    private native int surface_zoom( Surface dest, long pSrc, double zoomX, double zoomY, boolean smooth );

    public Surface rotoZoom( double angle, double zoom, boolean smooth )
        throws JameRuntimeException
    {
        Surface result = new Surface();

        Jame.checkRuntimeStatus( this.surface_rotoZoom( result, this.pSurface, angle, zoom, smooth ) );

        return result;

    }

    private native int surface_rotoZoom( Surface dest, long pSrc, double angle, double zoom, boolean smooth );

    public boolean overlaps( Surface other, int dx, int dy, int alphaThreshold )
    {
        return this.surface_overlaps( this.pSurface, other.pSurface, dx, dy, alphaThreshold );
    }

    private native boolean surface_overlaps( long pA, long pB, int dx, int dy, int threshold );

    public String toString()
    {
    	return "Surface (" + getWidth() + "," + getHeight() + ")";
    }
}
