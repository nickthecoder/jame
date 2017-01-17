package uk.co.nickthecoder.jame;

public class Renderer
{
    /**
     * The renderer is a software fallback
     */
    public static final int SOFTWARE = 1;

    /**
     * The renderer uses hardware acceleration
     */
    public static final int ACCELERATED = 2;

    /**
     * {@link #present()} is synchronized with the refresh rate
     */
    public static final int PRESENTVSYNC = 4;

    /**
     * the renderer supports rendering to texture
     */
    public static final int TARGETTEXTURE = 8;

    /**
     * C pointer to the SDL_Renderer structure.
     */
    private long pRenderer;

    public Renderer(Window window)
    {
        this(window, false);
    }

    public Renderer(Window window, boolean sync)
    {
        this(window, ACCELERATED | (sync ? PRESENTVSYNC : 0));
    }

    /**
     * 
     * @param window
     *            The window to be rendered
     * @param flags
     *            Combination of : {@link #SOFTWARE}, {@link #ACCELERATED}, {@link #PRESENTVSYNC}.
     *            To combine flags, either add them or better still, use bitwise or.
     */
    public Renderer(Window window, int flags)
    {
        pRenderer = renderer_create(window.getPointer(), flags);
        window.renderer = this;
    }

    private native long renderer_create(long pWindow, int flags);

    @Override
    protected void finalize()
    {
        destroy();
    }

    /**
     * Destroys the underlying SDL_Renderer. Any attempt to use this Renderer after if has been destroyed will cause an
     * error. This method will automatically be called by the garbage collector, but you may choose to call destroy
     * yourself, to free resources early.
     */
    public void destroy()
    {
        if (pRenderer != 0) {
            renderer_destroy(pRenderer);
            pRenderer = 0;
        }
    }

    private native void renderer_destroy(long pRenderer);

    long getPointer()
    {
        return pRenderer;
    }

    public int getFlags()
    {
        int flags = renderer_getFlags(pRenderer);
        if (flags < 0) {
            Jame.checkRuntimeStatus(flags);
        }
        return flags;
    }

    private native int renderer_getFlags(long pRenderer);

    public void present()
    {
        renderer_present(pRenderer);
    }

    private native void renderer_present(long pPointer);

    private Rect clip = null;

    public void setClip(Rect rect)
    {
        if (rect == null) {
            Jame.checkRuntimeStatus(renderer_clearClip(pRenderer));
        } else {
            Jame.checkRuntimeStatus(renderer_setClip(pRenderer, rect.x, rect.y, rect.width, rect.height));
        }
        clip = rect;
    }

    private native int renderer_setClip(long pRenderer, int x, int y, int width, int height);

    private native int renderer_clearClip(long pRenderer);

    public void setViewport(Rect rect)
    {
        Jame.checkRuntimeStatus(renderer_setViewport(pRenderer, rect.x, rect.y, rect.width, rect.height));
    }

    private native int renderer_setViewport(long pRenderer, int x, int y, int width, int height);

    public Rect getViewport()
    {
        Rect result = new Rect(0, 0, 0, 0);
        renderer_getViewport(pRenderer, result);
        return result;
    }

    private native void renderer_getViewport(long pRenderer, Rect pRect);

    /**
     * 
     * @return The clipping rectangle, or null, if there is not clipping.
     */
    public Rect getClip()
    {
        return clip;
    }

    public void setLogicalSize(int width, int height)
    {
        renderer_setLogicalSize(pRenderer, width, height);
    }

    private native int renderer_setLogicalSize(long pRenderer, int width, int height);

    public void setDrawColor(RGBA color)
    {
        Jame.checkRuntimeStatus(renderer_setDrawColor(pRenderer, color.r, color.g, color.b, color.a));
    }

    private native int renderer_setDrawColor(long pRenderer, int r, int g, int b, int a);

    public RGBA getDrawColor(RGBA color)
    {
        RGBA result = new RGBA(0, 0, 0, 0);
        Jame.checkRuntimeStatus(renderer_getDrawColor(pRenderer, result));

        return result;
    }

    private native int renderer_getDrawColor(long pRenderer, RGBA result);

    public void clear()
    {
        Jame.checkRuntimeStatus(renderer_clear(pRenderer));
    }

    private native int renderer_clear(long pRenderer);

    public void copy(Texture texture, int x, int y)
    {
        int width = texture.getWidth();
        int height = texture.getHeight();

        Jame.checkRuntimeStatus(
            renderer_copy(pRenderer, texture.getPointer(),
                0, 0, width, height,
                x, y, width, height));
    }

    public void copy(Texture texture, Rect srcRect, int x, int y)
    {
        Jame.checkRuntimeStatus(
            renderer_copy(pRenderer, texture.getPointer(),
                srcRect.x, srcRect.y, srcRect.width, srcRect.height,
                x, y, srcRect.width, srcRect.height));
    }

    public void copy(Texture texture, Rect srcRect, Rect destRect)
    {
        Jame.checkRuntimeStatus(
            renderer_copy(pRenderer, texture.getPointer(),
                srcRect.x, srcRect.y, srcRect.width, srcRect.height,
                destRect.x, destRect.y, destRect.width, destRect.height));
    }

    private native int renderer_copy(long pRenderer, long pTexture,
        int srcX, int srcY, int srcWidth, int srcHeight,
        int dstX, int dstY, int dstWidht, int dstHeight);

    public void copyEx(Texture texture, Rect srcRect, Rect dstRect,
        double angle, int ox, int oy, boolean flipX, boolean flipY)
    {
        Jame.checkRuntimeStatus(renderer_copyEx(pRenderer, texture.getPointer(),
            srcRect.x, srcRect.y, srcRect.width, srcRect.height,
            dstRect.x, dstRect.y, dstRect.width, dstRect.height,
            angle, ox, oy, (flipX ? 1 : 0) | (flipY ? 2 : 0)));
    }

    private native int renderer_copyEx(long pRenderer, long pTexture,
        int srcX, int srcY, int srcWidth, int srcHeight,
        int dstX, int dstY, int dstWidth, int dstHeight,
        double angle, int centerX, int centerY, int flip);

    public void setDrawBlendMode(BlendMode blendMode)
    {
        Jame.checkRuntimeStatus(renderer_setDrawBlendMode(pRenderer, blendMode.ordinal()));
    }

    private native int renderer_setDrawBlendMode(long pRenderer, int blendMode);

    public BlendMode getDrawBlendMode()
    {
        int value = renderer_getDrawBlendMode(pRenderer);
        if (value < 0) {
            Jame.checkRuntimeStatus(value);
        }
        return BlendMode.values()[value];
    }

    private native int renderer_getDrawBlendMode(long pRenderer);

    private Texture target = null;

    public void setTarget(Texture texture)
    {
        long pTexture = texture == null ? 0 : texture.getPointer();
        Jame.checkRuntimeStatus(renderer_setTarget(pRenderer, pTexture));
        target = texture;
    }

    private native int renderer_setTarget(long pRenderer, long pTexture);

    /**
     * @return The {@link Texture} passed to {@link #setTarget(Texture)}, or null setTaget hasn't been called.
     */
    public Texture getTarget()
    {
        return target;
    }

    /**
     * Draws the outline of a rectangle.
     * 
     * @param rect
     */
    public void drawRect(Rect rect)
    {
        Jame.checkRuntimeStatus(renderer_drawRect(pRenderer, rect.x, rect.y, rect.width, rect.height));
    }

    private native int renderer_drawRect(long pRenderer, int x, int y, int width, int height);

    /**
     * Draws a filled rectangle
     * 
     * @param rect
     */
    public void fillRect(Rect rect)
    {
        Jame.checkRuntimeStatus(renderer_fillRect(pRenderer, rect.x, rect.y, rect.width, rect.height));
    }

    private native int renderer_fillRect(long pRenderer, int x, int y, int width, int height);

    /**
     * Draws a line between two points
     * 
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2)
    {
        Jame.checkRuntimeStatus(renderer_drawLine(pRenderer, x1, y1, x2, y2));
    }

    private native int renderer_drawLine(long pRenderer, int x1, int y1, int x2, int y2);

    public String toString()
    {
        StringBuffer result = new StringBuffer();
        result.append("Renderer ");

        int flags = getFlags();
        if ((flags & SOFTWARE) != 0)
            result.append(" SOFTWARE");
        if ((flags & ACCELERATED) != 0)
            result.append(" ACCELERATED");
        if ((flags & PRESENTVSYNC) != 0)
            result.append(" PRESENTVSYNC");
        if ((flags & TARGETTEXTURE) != 0)
            result.append(" TARGETTEXTURE");

        return result.toString();
    }
}
