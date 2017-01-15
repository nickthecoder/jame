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
    
    private PixelFormat pixelFormat;

    public Texture(Renderer renderer, int width, int height, PixelFormat pixelFormat, Access access)
    {
        pTexture = texture_create(renderer.getPointer(), pixelFormat.value, access.ordinal(), width, height);
        this.width = width;
        this.height = height;
        this.pixelFormat = pixelFormat;
    }

    private native long texture_create(long pRenderer, int format, int access, int width, int height);

    public Texture(Renderer renderer, Surface surface)
    {
        pTexture = texture_createFromSurface(renderer.getPointer(), surface.getPointer());
        this.width = surface.getWidth();
        this.height = surface.getHeight();
        this.pixelFormat = surface.getPixelFormat();
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

    public PixelFormat getPixelFormat()
    {
        return pixelFormat;
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

    public void setAlpha(int alpha)
    {
        Jame.checkRuntimeStatus(texture_setAlpha(pTexture, alpha));
    }

    private native int texture_setAlpha(long pTexture, int alpha);

    public int getAlpha()
    {
        return texture_getAlpha(pTexture);
    }

    private native int texture_getAlpha(long pTexture);

    private RGBA colorMod;

    // TODO Check that the byte casts work, despite bytes being SIGNED in java.
    public void setColorMod(RGBA color)
    {
        Jame.checkRuntimeStatus(texture_setColorMod(pTexture, (byte) color.r, (byte) color.g, (byte) color.b));
        colorMod = color;
    }

    private native int texture_setColorMod(long pTexture, byte r, byte g, byte b);

    public RGBA getColorMod()
    {
        return colorMod;
    }

    public void update(Surface surface)
    {
        Jame.checkRuntimeStatus(texture_update(pTexture, surface.getPointer()));
    }

    private native int texture_update(long pTexture, long pSurface);
    
    
    
    
    public String toString()
    {
        return "Texture " + getPixelFormat();
    }
}
