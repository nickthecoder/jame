/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

public final class Video
{
    static Surface display;

    /**
     * Create the video surface in system memory
     */
    public static final int SWSURFACE = 0;

    /**
     * Create the video surface in video memory
     */
    public static final int HWSURFACE = 1;

    /**
     * Enables the use of asynchronous updates of the display surface. This will usually slow down
     * blitting on single CPU machines, but may provide a speed increase on SMP systems.
     */
    public static final int ASYNCBLIT = 4;

    /**
     * Normally, if a video surface of the requested bits-per-pixel (bpp) is not available, SDL will
     * emulate one with a shadow surface. Passing SDL_ANYFORMAT prevents this and causes SDL to use
     * the video surface, regardless of its pixel depth.
     */
    public static final int ANYFORMAT = 0x10000000;

    /**
     * Enable hardware double buffering; only valid with SDL_HWSURFACE. Calling SDL_Flip will flip
     * the buffers and update the screen. All drawing will take place on the buffer that is not
     * displayed at the moment.
     */
    public static final int DOUBLEBUF = 0x40000000;

    /**
     * SDL will attempt to use a fullscreen mode. If a hardware resolution change is not possible
     * (for whatever reason), the next higher resolution will be used and the display window
     * centered on a black background.
     */
    public static final int FULLSCREEN = 0x80000000;

    /**
     * Allow the window to be resized by the user. Will cause a ResizeEvent to be fired, during which Video.setMode should
     * be called with the new width and height to actually make the window change size.
     */
    public static final int RESIZABLE = 0x00000010;

    /**
     * Initialise the SDL Video subsystem
     */
    public static void init()
        throws JameException
    {
        Jame.initSubsystem(Jame.Subsystem.VIDEO);
    }

    /**
     * Set up a video mode with the specified width, height and bitsperpixel.
     * 
     * @param width
     *        The desired width in pixels of the video mode to set
     * @param height
     *        The desired height in pixels of the video mode to set. As of SDL 1.2.10, if width and
     *        height are both 0, SDL_SetVideoMode will use the width and height of the current video
     *        mode (or the desktop mode, if no mode has been set).
     * @param bpp
     *        The desired bits per pixel of the video mode to set. If bitsperpixel is 0, it is
     *        treated as the current display bits per pixel. A bitsperpixel of 24 uses the packed
     *        representation of 3 bytes per pixel. For the more common 4 bytes per pixel mode,
     *        please use a bitsperpixel of 32. Somewhat oddly, both 15 and 16 bits per pixel modes
     *        will request a 2 bytes per pixel mode, but with different pixel formats.
     * @param flags
     *        The possible values for the flags parameter are the same used by the SDL_Surface
     *        structure. OR'd combinations of the following values are valid : SWSURFACE, HWSURFACE,
     *        ASYNCBLIT, ANYFORMAT, DOUBLEBUF, FULLSCREEN
     * @return The requested framebuffer Surface.
     */
    public static Surface setMode( int width, int height, int bpp, int flags )
        throws JameException
    {
        display = new Surface();
        Jame.checkStatus(video_setMode(display, width, height, bpp, flags));
        return display;
    }

    private static native int video_setMode( Surface display, int width, int height, int bpp,
        int flags );

    /**
     * Sets the video mode using defaults of 32 bits per pixel and flags of SWSURFACE | DOUBLEBUF
     * The display surface can be retrieved any time after <code>setMode</code>, using :
     * {@link #getDisplaySurface()}.
     * 
     * @param width
     *        The desired width in pixels of the video mode to set
     * @param height
     *        The desired height in pixels of the video mode to set. As of SDL 1.2.10, if width and
     *        height are both 0, SDL_SetVideoMode will use the width and height of the current video
     *        mode (or the desktop mode, if no mode has been set).
     * @return The requested framebuffer Surface.
     */
    public static Surface setMode( int width, int height )
        throws JameException
    {
        Surface result = Video.setMode(width, height, 32, Video.SWSURFACE | Video.DOUBLEBUF);
        Jame.checkNull(result);
        return result;
    }

    /**
     * The frame buffer is the Surface that is visible to the end user, visible within the
     * application's window.
     * 
     * @return The Surface created from {@link #setMode(int, int)}
     */
    public static Surface getDisplaySurface()
    {
        return display;
    }

    int harwareOrSoftwareSurfaces()
    {
        if (display == null) {
            return Video.SWSURFACE;
        } else {
            return display.isHardwareSurface() ? Video.HWSURFACE : Video.SWSURFACE;
        }
    }

    /**
     * When double buffering, the {@link #getDisplaySurface() display Surface} has two image
     * buffers, one that is visible on the screen, but cannot be drawn on, and the other (called the
     * back buffer) can be drawn on, but is not visible. <code>flip</code> changes them over; the
     * visible buffer becomes drawable, and the back buffer becomes visible.
     * <p>
     * A typical game loop will fill the display surface with a background colour, or background
     * image, then draw all of the game's objects and then flip.
     * <p>
     * Using double buffering ensures that the user never sees a partially finished image.
     */
    public static void flip()
    {
        display.flip();
    }

    /**
     * Sets the window title (aka window caption). This must should not be called after setMode.
     * 
     * @param title
     */
    public static void setWindowTitle( String title )
    {
        video_setWindowTitle(title);
    }

    private static native void video_setWindowTitle( String title );

    /**
     * Opens the given bitmap (bmp) file and uses it as the window's icon. It uses SDL_LoadBMP, so
     * you must use a bmp file (not png etc).
     * 
     * According to the SDL1.2 documentation, this must be 32x32 pixels for Windows, which is alas
     * very low res by today's standards.
     * 
     * Also according to the SDL1.2 documentation, this must NOT be called after setMode (which is
     * why we are limited to bmp images, as the library which loads pngs requires that setMode has
     * already been called). However, under Linux, there doesn't seem to be any problem calling this
     * after setMode.
     * 
     * @param filename
     *        The name of the .bmp file to open. Note this cannot be a png file.
     */
    public static void setWindowIcon( String filename )
    {
        video_setWindowIcon(filename);
    }

    private static native void video_setWindowIcon( String filename );

    public static void showMousePointer( boolean value )
    {
        video_showMousePointer(value ? 1 : 0);
    }

    private static native void video_showMousePointer( int value );

    /**
     * Video only has static methods, and cannot be instantiated.
     */
    private Video()
    {
    }
}
