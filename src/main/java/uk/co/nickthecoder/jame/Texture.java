package uk.co.nickthecoder.jame;

public class Texture
{
    /**
     * Use by the <code>access</code> parmeter of the constructor :
     * {@link Texture#Texture(Renderer, int, int, int, int)}.
     * <p>
     * Use {@link #STATIC}, when the Texture rarely changes. Use {@link #STREAMING}, when the Texture changes often. Use
     * {@link #TARGET} when you want to render ONTO the Texture.
     * </p>
     */
    public enum Access
    {
        STATIC, STREAMING, TARGET
    };

    /**
     * C pointer to the SDL_Texture structure.
     */
    private long pTexture;

    private int width;

    private int height;

    /**
     * Creates a texture with suitable as a rendering target (using {@link Access#TARGET}).
     * The pixel format is chosen automatically. At the moment it is hard-coded to {@link PixelFormat#RGBA8888},
     * but will be based on the pixel format of the {@link Window} that was last opened.
     * TODO automate pixel format selection.
     * 
     * @param renderer
     * @param width
     * @param height
     */
    public Texture(Renderer renderer, int width, int height)
    {
        this(renderer, width, height, PixelFormat.RGBA8888, Access.TARGET);
    }

    public Texture(Renderer renderer, int width, int height, PixelFormat pixelFormat, Access access)
    {
        pTexture = texture_create(renderer.getPointer(), pixelFormat.value, access.ordinal(), width, height);
        this.width = width;
        this.height = height;
    }

    private native long texture_create(long pRenderer, int format, int access, int width, int height);

    public Texture(Renderer renderer, Surface surface)
    {
        pTexture = texture_createFromSurface(renderer.getPointer(), surface.getPointer());
        this.width = surface.getWidth();
        this.height = surface.getHeight();
    }

    private native long texture_createFromSurface(long pRenderer, long pSurface);

    protected void finalize()
    {
        destroy();
    }

    /**
     * Destroys the underlying SDL_Texture. The garbage collector will call this automatically, but you may choose
     * to call this yourself, to free graphic card resource as soon as possible. Do not attempt to use the Texture
     * after it has been destroyed.
     */
    public void destroy()
    {
        if (pTexture != 0) {
            texture_destroy(pTexture);
            pTexture = 0;
        }
    }

    private native void texture_destroy(long pTexture);

    /**
     * 
     * @return The C pointer to the SDL_Texture structure.
     */
    long getPointer()
    {
        return pTexture;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setBlendMode(BlendMode blendMode)
    {
        Jame.checkRuntimeStatus(renderer_setBlendMode(pTexture, blendMode.ordinal()));
    }

    private native int renderer_setBlendMode(long pTexture, int blendMode);

    public BlendMode getBlendMode()
    {
        int result = renderer_getBlendMode(pTexture);
        if (result < 0) {
            Jame.checkRuntimeStatus(-result);
        }
        return BlendMode.values()[result];
    }

    private native int renderer_getBlendMode(long pTexture);

}
