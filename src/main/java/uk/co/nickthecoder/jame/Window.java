package uk.co.nickthecoder.jame;

import uk.co.nickthecoder.jame.Texture.Access;

/**
 * A wrapper around the SDL2 SDL_Window structure.
 * 
 */
public class Window
{
    /**
     * Initialise the SDL Video subsystem
     */
    public static void init()
        throws JameException
    {
        Jame.initSubsystem(Jame.Subsystem.VIDEO);
    }

    /** fullscreen window */
    public static final int FULLSCREEN = 0x1;

    /** fullscreen window at the current desktop resolution */
    public static final int FULLSCREEN_DESKTOP = FULLSCREEN | 0x1000;

    /**
     * window usable with OpenGL context
     */
    public static final int OPENGL = 0x02;

    /**
     * window is visible
     */
    public static final int SHOWN = 0x04;

    /**
     * window is not visible
     */
    public static final int HIDDEN = 0x08;

    /**
     * no window decoration
     */
    public static final int BORDERLESS = 0x10;

    /**
     * window can be resized
     */
    public static final int RESIZABLE = 0x20;

    /**
     * window is minimized
     */
    public static final int MINIMIZED = 0x40;

    /**
     * window is maximized
     */
    public static final int MAXIMIZED = 0x80;

    /**
     * window has grabbed input focus
     */
    public static final int INPUT_GRABBED = 0x100;

    /**
     * window has input focus
     */
    public static final int INPUT_FOCUS = 0x200;

    /**
     * window has mouse focus
     */
    public static final int MOUSE_FOCUS = 0x400;

    /**
     * window not created by SDL
     */
    public static final int FOREIGN = 0x800;

    /**
     * window should be created in high-DPI mode if supported (>= SDL 2.0.1)
     */
    public static final int ALLOW_HIGHDPI = 0x2000;

    public static final int POS_CENTERED = 0;
    public static final int POS_UNDEFINED = 0;

    /**
     * A C pointer to the SDL_Window object.
     */
    private long pWindow;

    private int width;

    private int height;

    /**
     * Set in Renderer's constructor.
     */
    Renderer renderer;

    private static PixelFormat recommendedPixelFormat = PixelFormat.RGBX8888;

    private static PixelFormat recommendedPixelFormatAlpha = PixelFormat.RGBA8888;

    /**
     * Returns a {@link PixelFormat} based on the pixel format of the window that was most recently opened
     * and/or when the last time {@link #setFullScreen(int)} was called.
     * <p>
     * If a window hasn't been created yet, or alpha==true and the heuristic failed to recommended format, then a
     * suitable default value format is returned. Presently the defaults are {@link PixelFormat#RGBA8888} and
     * {@link PixelFormat#RGBX8888}.
     * </p>
     * 
     * @param alpha
     *            Should the pixel format include an alpha channel?
     * @return The recommended PixelFormat.
     */
    public static final PixelFormat recommendedPixelFormat(boolean alpha)
    {
        return alpha ? recommendedPixelFormatAlpha : recommendedPixelFormat;
    }

    /**
     * @param x
     *            The x position of the window, or {@link #POS_CENTERED} or {@link #POS_UNDEFINED}.
     * @param y
     *            The y position of the window, or {@link #POS_CENTERED} or {@link #POS_UNDEFINED}.
     * @param flags
     *            One or more of : {@link #FULLSCREEN}, {@link #FULLSCREEN_DESKTOP}, {@link #OPENGL}, {@link #SHOWN},
     *            {@link #HIDDEN}, {@link #BORDERLESS}, {@link #RESIZABLE}, {@link #MINIMIZED}, {@link #MAXIMIZED},
     *            {@link #ALLOW_HIGHDPI}.
     */
    public Window(String title, int x, int y, int width, int height, int flags)
    {
        this.width = width;
        this.height = height;
        this.pWindow = window_create(title, x, y, width, height, flags);

        updateRecommendedPixelFormats();
    }

    public Window(String title, int width, int height, boolean centered, int flags)
    {
        this(title, (centered ? POS_CENTERED : POS_UNDEFINED), (centered ? POS_CENTERED : POS_UNDEFINED), width,
            height, flags);
    }

    private void updateRecommendedPixelFormats()
    {
        recommendedPixelFormat = getPixelFormat();
        try {
            recommendedPixelFormatAlpha = recommendedPixelFormat.withAlpha();
        } catch (Exception e) {
            // TODO Remove debug output
            System.err.println("Failed to get PixelFormat with alpha channel from " + recommendedPixelFormat);
            // Ignore, keep to the default.
        }
    }

    /**
     * Creates a streaming texture with a suitable pixel format for this window.
     * 
     * @param width
     * @param height
     * @return
     */
    public Texture createTexture(int width, int height, boolean alpha)
    {
        return new Texture(this.renderer, width, height, recommendedPixelFormat(alpha), Access.STREAMING);
    }

    public Surface createSurface(int width, int height, boolean alpha)
    {
        return new Surface(width, height, recommendedPixelFormat(alpha));
    }

    /**
     * Used only by Jame itself.
     * 
     * @return The C pointer to the SDL_Window structure.
     */
    long getPointer()
    {
        return pWindow;
    }

    private native long window_create(String title, int x, int y, int w, int h, int flags);

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    private final void ensureNotDestroyed()
    {
        if (this.pWindow == 0) {
            throw new JameRuntimeException("Window has been destroyed");
        }
    }

    public void destroy()
    {
        window_destroy(pWindow);
        pWindow = 0;
    }

    /**
     * Destroys the underlying SDL window. As this will be called by the garbage collector, it is important to
     * ensure that this Window object is referenced by your code, so that it cannot be garbage collected before
     * you intend it to be.
     */
    @Override
    public void finalize()
    {
        this.destroy();
    }

    private native void window_destroy(long pWindow);

    public Rect getPosition()
    {
        Rect rect = new Rect(0, 0, 0, 0);
        window_getPosition(pWindow, rect);

        return rect;
    }

    private native void window_getPosition(long pWindow, Rect rect);

    /**
     * Hides a window. Use {@link #show()} to make it visible again.
     */
    public void hide()
    {
        window_hide(pWindow);
    }

    private native void window_hide(long pWindow);

    /**
     * Makes the window visible after it has been hidden with {@link #hide()}.
     */
    public void show()
    {
        window_show(pWindow);
    }

    private native void window_show(long pWindow);

    /**
     * Makes the window as big as possible. This is NOT the same as {@link #setFullScreen(int)}.
     */
    public void maximize()
    {
        window_maximize(pWindow);
    }

    private native void window_maximize(long pWindow);

    /**
     * Restore the window to normal size after it has been maximised, or minimized.
     */
    public void restore()
    {
        window_restore(pWindow);
    }

    private native void window_restore(long pWindow);

    public void raise()
    {
        window_raise(pWindow);
    }

    private native void window_raise(long pWindow);

    /**
     * Make the window fullscreen or windowed depending on the value of <code>flags</flags>.
     * {@link #FULLSCREEN} may change the screen mode, whereas {@link #FULLSCREEN_DESKTOP} will 'fake'
     * fullscreen by making the window cover the entire desktop (without changing screen mode).
     * 
     * @param flags
     *            One of {@link #FULLSCREEN}, {@link #FULLSCREEN_DESKTOP}, or 0 for windowed.
     */
    public void setFullScreen(int flags)
    {
        Jame.checkRuntimeStatus(window_fullScreen(pWindow, flags));
        updateRecommendedPixelFormats();
    }

    private native int window_fullScreen(long pWindow, int flags);

    /**
     * Sets the icon for the window using a {@link Surface}. The surface may be free thereafter.
     * SDL documentation does not state the allowed sizes - does windows still need a 16x16 icon?
     * 
     * @param surface
     *            The icon
     */
    public void setIcon(Surface surface)
    {
        window_setIcon(pWindow, surface.getPointer());
    }

    private native void window_setIcon(long pWindow, long pSurface);

    public void setInputFocus()
    {
        Jame.checkRuntimeStatus(window_setInputFocus(pWindow));
    }

    private native int window_setInputFocus(long pWindow);

    public void setPosition(int x, int y)
    {
        window_setPosition(pWindow, x, y);
    }

    private native void window_setPosition(long pWindow, int x, int y);

    public void setResizable(boolean value)
    {
        window_setResizable(pWindow, value);
    }

    private native void window_setResizable(long pWindow, boolean value);

    public void setSize(int width, int height)
    {
        window_setSize(pWindow, width, height);
    }

    private native void window_setSize(long pWindow, int width, int height);

    public void setTitle(String title)
    {
        window_setTitle(pWindow, title);
    }

    private native void window_setTitle(long pWindow, String title);

    public PixelFormat getPixelFormat()
    {
        return new PixelFormat(window_getPixelFormat(pWindow));
    }

    private native int window_getPixelFormat(long pWindow);

    public String toString()
    {
        return "Window(" + width + "," + height + ") " + getPixelFormat();
    }
}
