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
        pTexture = native_create(renderer.getPointer(), pixelFormat.value, access.ordinal(), width, height);
        this.width = width;
        this.height = height;
        this.pixelFormat = pixelFormat;
    }

    private native long native_create(long pRenderer, int format, int access, int width, int height);

    /**
     * Create a Texture, from Surface. Converts a software (RAM based) surface into a hardware (GPU) texture.
     * 
     * @param renderer
     * @param surface
     */
    public Texture(Renderer renderer, Surface surface)
    {
        pTexture = native_createFromSurface(renderer.getPointer(), surface.getPointer());
        this.width = surface.getWidth();
        this.height = surface.getHeight();
        this.pixelFormat = surface.getPixelFormat();
    }

    private native long native_createFromSurface(long pRenderer, long pSurface);

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
            native_destroy(pTexture);
            pTexture = 0;
        }
    }

    private native void native_destroy(long pTexture);

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
        Jame.checkRuntimeStatus(native_setBlendMode(pTexture, blendMode.ordinal()));
    }

    private native int native_setBlendMode(long pTexture, int blendMode);

    public BlendMode getBlendMode()
    {
        int result = native_getBlendMode(pTexture);
        if (result < 0) {
            Jame.checkRuntimeStatus(-result);
        }
        return BlendMode.values()[result];
    }

    private native int native_getBlendMode(long pTexture);

    public void setAlpha(int alpha)
    {
        Jame.checkRuntimeStatus(native_setAlpha(pTexture, alpha));
    }

    private native int native_setAlpha(long pTexture, int alpha);

    public int getAlpha()
    {
        return native_getAlpha(pTexture);
    }

    private native int native_getAlpha(long pTexture);

    private RGBA colorMod;

    public void setColorMod(RGBA color)
    {
        Jame.checkRuntimeStatus(native_setColorMod(pTexture, (byte) color.r, (byte) color.g, (byte) color.b));
        colorMod = color;
    }

    private native int native_setColorMod(long pTexture, byte r, byte g, byte b);

    public RGBA getColorMod()
    {
        return colorMod;
    }

    public void update(Surface surface)
    {
        Jame.checkRuntimeStatus(native_update(pTexture, surface.getPointer()));
    }

    private native int native_update(long pTexture, long pSurface);

    /**
     * Reads the pixel at a given point. Note, this may be SLOW, so don't read lots of pixels.
     * <p>
     * Note the renderer must not be currently rendering to a texture See {@link Renderer#setTarget(Texture)}, because
     * this methods set its own target, and then resets at the end.
     * 
     * @param renderer
     *            To read the pixels, it seems SDL2 needs to use a renderer. Grrr.
     * @param x
     * @param y
     * @return The pixel at the given point, or null if outside of the texture.
     */
    public RGBA getPixel(Renderer renderer, int x, int y)
    {
        if ((x < 0) || (y < 0)) {
            return null;
        }

        Texture temp = null;
        try {
            PixelFormat pixelFormat = Window.recommendedPixelFormat(true);
            // Copy the pixel to a temporary texture
            temp = new Texture(renderer, 1, 1, pixelFormat, Access.TARGET);
            renderer.setTarget(temp);
            renderer.setDrawColor(RGBA.TRANSPARENT);
            renderer.clear();
            renderer.copy(this, -x, -y);

            RGBA result = new RGBA(0, 0, 0);

            Jame.checkRuntimeStatus(native_getPixel(renderer.getPointer(), pixelFormat.value, result));
            return result;

        } finally {
            try {
                renderer.setTarget(null);
            } catch (Exception e) {
                // Do nothing
            }
            if (temp != null) {
                temp.destroy();
            }
        }
    }

    private native int native_getPixel(long pRenderer, int pixelFormat, RGBA result);

    public String toString()
    {
        return "Texture " + getPixelFormat();
    }
}
