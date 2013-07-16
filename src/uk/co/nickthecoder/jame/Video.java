/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

public class Video
{
    static Surface screen;
    
    /**
     * Create the video surface in system memory
     */
    public static final int SWSURFACE = 0;
 
    /**
     * Create the video surface in video memory
     */
    public static final int HWSURFACE = 1;
        
    /**
     * Enables the use of asynchronous updates of the display surface.
     * This will usually slow down blitting on single CPU machines, but may provide a speed increase on SMP systems. 
     */
    public static final int ASYNCBLIT = 4;

    /**
     * Normally, if a video surface of the requested bits-per-pixel (bpp) is not available,
     * SDL will emulate one with a shadow surface. Passing SDL_ANYFORMAT prevents this and causes SDL
     *  to use the video surface, regardless of its pixel depth. 
     */
    public static final int ANYFORMAT = 0x10000000;

    /**
     * Enable hardware double buffering; only valid with SDL_HWSURFACE.
     * Calling SDL_Flip will flip the buffers and update the screen. All drawing will take place on the
     * surface that is not displayed at the moment. If double buffering could not be enabled then SDL_Flip
     *  will just perform a SDL_UpdateRect on the entire screen.
     */
    public static final int DOUBLEBUF = 0x40000000;
        
    /**
     * SDL will attempt to use a fullscreen mode. If a hardware resolution change is not possible
     * (for whatever reason), the next higher resolution will be used and the display window centered on a black background.
     */
    public static final int FULLSCREEN = 0x80000000;
        
    /**
     * Initialise the SDL Video subsystem
     */
    public static void init()
        throws JameException    
    {
        Jame.initSubsystem( Jame.Subsystem.VIDEO );
    }
    /**
     * Set up a video mode with the specified width, height and bitsperpixel. 
     * @param width The desired width in pixels of the video mode to set 
     * @param height The desired height in pixels of the video mode to set. As of SDL 1.2.10, 
     *               if width and height are both 0, SDL_SetVideoMode will use the width and height of the current video mode
     *               (or the desktop mode, if no mode has been set). 
     * @param bpp The desired bits per pixel of the video mode to set. If bitsperpixel is 0, it is treated as the current display
     *            bits per pixel. A bitsperpixel of 24 uses the packed representation of 3 bytes per pixel. For the more common
     *            4 bytes per pixel mode, please use a bitsperpixel of 32. Somewhat oddly, both 15 and 16 bits per pixel modes will
     *            request a 2 bytes per pixel mode, but with different pixel formats.
     * @param flags The possible values for the flags parameter are the same used by the SDL_Surface structure.
     *              OR'd combinations of the following values are valid : SWSURFACE, HWSURFACE, ASYNCBLIT, ANYFORMAT, DOUBLEBUF, FULLSCREEN
     * @return The requested framebuffer Surface.               
     */
    public static Surface setMode( int width, int height, int bpp, int flags )
        throws JameException    
    {
        screen = new Surface();
        Jame.checkStatus( video_setMode( screen, width, height, bpp, flags ) );
        return screen;
    }
    private static native int video_setMode( Surface screen, int width, int height, int bpp, int flags );

    
    /**
     * Sets the video mode using defaults of 32 bits per pixel and flags of HWSURFACE | DOUBLEBUF
     * @param width The desired width in pixels of the video mode to set 
     * @param height The desired height in pixels of the video mode to set. As of SDL 1.2.10, 
     *               if width and height are both 0, SDL_SetVideoMode will use the width and height of the current video mode
     *               (or the desktop mode, if no mode has been set). 
     * @return The requested framebuffer Surface.
     */
    public static Surface setMode( int width, int height )
        throws JameException    
    { 
        Surface result = Video.setMode( width, height, 32, Video.HWSURFACE | Video.DOUBLEBUF );
        Jame.checkNull( result );
        return result;
    }
    
    /**
     * Sets the window title (aka window caption).
     * This must should not be called after setMode.
     * @param title
     */
    public static void setWindowTitle( String title )
    {
        video_setWindowTitle( title );
    }
    private static native void video_setWindowTitle( String title );

    /**
     * Opens the given bitmap (bmp) file and uses it as the window's icon. 
     * This must not be called after setMode.
     * It uses SDL_LoadBMP, so you must use a bmp file (not png etc).
     * 
     * @param filename The name of the .bmp file to open. Note this cannot be a png file.
     */
    public static void setWindowIcon( String filename )
    {
        video_setWindowIcon( filename );
    }
    private static native void video_setWindowIcon( String filename );

    public static void showMousePointer( boolean value )
    {
        video_showMousePointer( value ? 1 : 0 );
    }
    private static native void video_showMousePointer( int value );
    
}
