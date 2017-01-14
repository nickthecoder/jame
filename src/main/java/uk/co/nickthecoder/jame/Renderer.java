package uk.co.nickthecoder.jame;

public class Renderer
{
    /**
     * The renderer is a software fallback
     */
    public static final int SOFTWARE = 0;

    /**
     * The renderer uses hardware acceleration
     */
    public static final int ACCELERATED = 0;

    /**
     * {@link #present()} is synchronized with the refresh rate
     */
    public static final int PRESENTVSYNC = 0;

    /**
     * the renderer supports rendering to texture
     */
    public static final int TARGETTEXTURE = 0;

    /**
     * C pointer to the SDL_Renderer structure.
     */
    private long pRenderer;

    public Renderer(Window window)
    {
        this(window, 0);
    }

    /**
     * 
     * @param window
     *            The window to be rendered
     * @param flags
     *            Any of : {@link #ACCELERATED}, {@link #SOFTWARE}, {@link #PRESENTVSYNC}, {@link #TARGETTEXTURE}.
     */
    public Renderer(Window window, int flags)
    {
        pRenderer = renderer_create(window.getPointer(), flags);
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

    public void present()
    {
        renderer_present(pRenderer);
    }

    private native void renderer_present(long pPointer);

    public void setClip(Rect rect)
    {
        Jame.checkRuntimeStatus(renderer_setClip(pRenderer, rect.x, rect.y, rect.width, rect.height));
    }

    private native int renderer_setClip(long pRenderer, int x, int y, int width, int height);

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

    private native int renderer_copy(long pRenderer, long pTexture, int sx, int sy, int sw, int sh, int dx, int dy,
        int dw, int dh);

    public void setDrawBlendMode(BlendMode blendMode)
    {
        Jame.checkRuntimeStatus(renderer_setDrawBlendMode(pRenderer, blendMode.ordinal()));
    }

    private native int renderer_setDrawBlendMode(long pRenderer, int blendMode);

    public BlendMode getDrawBlendMode()
    {
        return BlendMode.values()[renderer_getDrawBlendMode(pRenderer)];
    }

    private native int renderer_getDrawBlendMode(long pRenderer);

}
