/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import java.io.File;

/**
 * Surface is the core class for all things related to video. The display is a
 * surface, created using :
 * {@link uk.co.nickthecoder.jame.Video#setMode(int, int)}. Your game's bitmaps
 * are loaded using : {@link #Surface(String)}.
 * <p>
 * The only drawing primitives currently implemented by Jame are :
 * <code>setPixel</code>, <code>blit</code>, <code>fill</code> and rendering of
 * {@link uk.co.nickthecoder.jame.TrueTypeFont}s. There no methods to draw
 * lines, circles etc.
 * <p>
 * Before creating a Surface, you must initialise the Video subsystem :
 * {@link uk.co.nickthecoder.jame.Video#init()}.
 * <p>
 * See {@link uk.co.nickthecoder.jame} for example code of how initialise and
 * use Surfaces.
 */
public final class Surface {

	private static int totalCreated = 0;

	private static int totalExisting = 0;

	private static int totalFreedByGC = 0;

	public enum BlendMode {
		NONE(-1), COMPOSITE(0), RGB_ADD(0x1), RGB_SUB(0x2), RGB_MULT(0x3), RGB_MIN(
				0x4), RGB_MAX(0x5),

		RGBA_ADD(0x6), RGBA_SUB(0x7), RGBA_MULT(0x8), RGBA_MIN(0x9), RGBA_MAX(
				0x10);

		public final int code;

		BlendMode(int code) {
			this.code = code;
		}
	}

	private static final int SDL_SRCALPHA = 0x00010000;

	/**
	 * A C pointer to the SDL_Surface object.
	 */
	private long pSurface;

	private int width;

	private int height;

	private boolean alphaEnabled = true;

	private int alpha = 255;

	private int flags;

	private boolean hasAlpha;

	public static int totalCreated() {
		return totalCreated;
	}

	public static int totalExisting() {
		return totalExisting;
	}

	public static int totalFreedByGC() {
		return totalFreedByGC;
	}

	/**
	 * For internal use only. We need to create blank Surface objects, which can
	 * then be filled in by the JNI calls such as surface_setMode.
	 */
	Surface() {
		totalCreated += 1;
		totalExisting += 1;
	}

	/**
	 * Loads a Surface from disk.
	 * 
	 * For efficient blitting, convert all loaded surfaces, so that they have
	 * the same format as the display's Surface :
	 * 
	 * <code>
	 * <pre>
	 * Surface loadedSurface = new Surface( filename );
	 * Surface efficientSurface = loadedSurface.convert();
	 * loadedSurface.free();
	 * </pre>
	 * </code>
	 * 
	 * @param filename
	 * @throws JameException
	 */
	public Surface(String filename) throws JameException {
		this();
		Jame.checkStatus(this.surface_load(filename));
	}

	public Surface(File filename) throws JameException {
		this(filename.getPath());
	}

	private native int surface_load(String filename);

	/**
	 * Creates a blank surface of a given size.
	 * 
	 * @param width
	 * @param height
	 * @param alpha
	 *            If false, then then each pixel only has red, green and blue
	 *            values. If it is true, then the pixels will also have an alpha
	 *            value, which determines how transparent the pixel is.
	 * @throws JameRuntimeException
	 */
	public Surface(int width, int height, boolean alpha)
			throws JameRuntimeException {
		this(width, height, alpha, false);
		this.hasAlpha = alpha;
	}

	/**
	 * Creates a blank surface of a given size.
	 * 
	 * @param width
	 * @param height
	 * @param alpha
	 *            If false, then then each pixel only has red, green and blue
	 *            values. If it is true, then the pixels will also have an alpha
	 *            value, which determines how transparent the pixel is.
	 * @param hardware
	 *            Whether the Surface should be stored on the GPU's memory, or
	 *            the computer's main memory. If 'hardware' is false, then the
	 *            Surface will be stored in the computer's main memory. If
	 *            'hardware' is true, then the surface <i>may</i> be stored on
	 *            the GPU, but there is no guarantee.
	 *            <p>
	 *            If in doubt use 'false'.
	 * @throws JameRuntimeException
	 */
	public Surface(int width, int height, boolean alpha, boolean hardware)
			throws JameRuntimeException {
		this();
		Jame.checkRuntimeStatus(this.surface_create(width, height, alpha,
				hardware));
		this.hasAlpha = alpha;
	}

	private native int surface_create(int width, int height, boolean alpha,
			boolean hardware);

	/**
	 * @return true if the Surface has an alpha channel.
	 */
	public boolean hasAlphaChannel() {
		return this.hasAlpha;
	}

	/**
	 * @return Returns true if the surface is stored on the GPU.
	 */
	public boolean isHardwareSurface() {
		return (this.flags & Video.HWSURFACE) != 0;
	}

	/**
	 * Creates a copy of the Surface. The new Surface's pixel format will be
	 * compatable with the display's Surface, so it might be different from the
	 * source surface's.
	 * 
	 * @throws JameRuntimeException
	 */
	public Surface copy() throws JameRuntimeException {
		ensureNotFreed();

		Surface result = new Surface(getWidth(), getHeight(), hasAlphaChannel());
		if (hasAlphaChannel()) {
			/*
			 * The following SHOULD work (and would be more efficient), but I
			 * sometimes get nasty crashes whenever I use this approach.
			 * Othertimes, the blit just doesn't work. I tried tracking down the
			 * bug, but my C debugging skills were no match for it! I have a
			 * hunch the problem is in my version of SDL, but I can't be sure.
			 */
			/*
			 * boolean alphaEn = getAlphaEnabled(); setAlphaEnabled(false);
			 * blit(result); setAlphaEnabled(alphaEn);
			 */
			blit(result, 0, 0, BlendMode.COMPOSITE);
		} else {
			blit(result);
		}
		return result;
	}

	/**
	 * Frees the memory associated with this Surface. Any attempt to use this
	 * surface after it has been free will throw an exception. Although free
	 * will be called when the Surface is garbage collected, it is good practice
	 * to free the Surface as soon as possible.
	 */
	public void free() {
		if (this.pSurface != 0) {
			totalExisting -= 1;
			this.surface_free(this.pSurface);
			this.pSurface = 0;
		}
	}

	private native int surface_free(long pSurface);

	/**
	 * Frees the SDL_Surface
	 */
	@Override
	protected void finalize() {
		if (this.pSurface != 0) {
			totalFreedByGC += 1;
			this.free();
		}
	}

	private final void ensureNotFreed() {
		if (this.pSurface == 0) {
			throw new JameRuntimeException("Surface has been freed");
		}
	}

	/**
	 * @return The width in pixels of the Surface's bitmap image.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * @return The height in pixels of the Surface's bitmap image.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Used on RGB surfaces (not RGBA surfaces) to set the alpha value used
	 * during blitting. This is a per-surface value, i.e. the same alpha value
	 * is used for all pixels.
	 * 
	 * Internally, this calls SDL's SDL_SetAlpha( surface, 0, 255 ) when alpha
	 * is 255, and SDL_SetAlpha( surface, SDL_SRCALPHA, alpha ) for all other
	 * values of alpha.
	 * 
	 * @param alpha
	 *            The alpha value from 0 (transparent) to 255 (opaque)
	 * 
	 * @throws JameRuntimeException
	 *             When the surface has an alpha channel.
	 */
	public void setPerSurfaceAlpha(int alpha) throws JameRuntimeException {
		if (this.hasAlpha) {
			throw new JameRuntimeException(
					"Cannot setPerSurfaceAlpha on RGBA surfaces");
		} else {
			// Jame.checkRuntimeStatus(this.surface_setAlpha(this.pSurface,
			// alpha == 255 ? 0 : SDL_SRCALPHA, alpha));
			setAlpha(alpha != 255, alpha);
		}
	}

	/**
	 * Used on RGBA surfaces (not RGB surfaces) to turn on/off the alpha channel
	 * while blitting
	 * 
	 * Internally, this calls SDL's setAlpha( 0, 255 ) when value is false, and
	 * setAlpha( SDL_SRC_ALPHA, 255 ) when value is true.
	 * 
	 * @param value
	 * @throws JameRuntimeException
	 *             When the surface does not have an alpha channel.
	 */
	public void setAlphaEnabled(boolean value) throws JameRuntimeException {
		if (this.hasAlpha) {
			Jame.checkRuntimeStatus(this.surface_setAlpha(this.pSurface,
					value ? SDL_SRCALPHA : 0, 255));
		} else {
			throw new JameRuntimeException(
					"Cannot setAlphaEnabaled on RGB surfaces, only RGBA surfaces");
		}
	}

	public int getPerSurfaceAlpha() {
		return this.alpha;
	}

	public boolean getAlphaEnabled() {
		return this.alphaEnabled;
	}

	/**
	 * Exposes SDL's setAlpha, but IMHO, it is clearer to use
	 * {@link #setPerSurfaceAlpha(int)} for RGB surfaces, and
	 * {@link #setAlphaEnabled(boolean)} for RGBA surfaces.
	 * 
	 * @param srcAlpha
	 * @param alpha
	 */
	public void setAlpha(boolean srcAlpha, int alpha)
			throws JameRuntimeException {
		ensureNotFreed();

		this.alphaEnabled = srcAlpha;
		this.alpha = alpha;
		Jame.checkRuntimeStatus(this.surface_setAlpha(this.pSurface,
				srcAlpha ? SDL_SRCALPHA : 0, alpha));
	}

	private native int surface_setAlpha(long pSurface, int flags, int alpha);

	/**
	 * Gets the value of a single pixel. Only works with 32 bit surfaces. The
	 * meaning of the result will depend on the Surface's pixel format i.e. the
	 * order, and the number of bits for each red/green/blue/alpha values will
	 * vary. An easier to understand (but slower) version is
	 * {@link #getPixelRGBA(int, int)}.
	 * 
	 * @param x
	 *            The x coordinate, the left edge of the surface is 0.
	 * @param y
	 *            The y coordinate, the top edge is the surface is 0.
	 * @return The value of a single pixel, stored in a single int
	 */
	public int getPixelColor(int x, int y) {
		ensureNotFreed();

		return this.surface_getPixelColor(this.pSurface, x, y);
	}

	private native int surface_getPixelColor(long pSurface, int x, int y);

	/**
	 * Gets the value of a single pixel. Only works with 32 bit surfaces.
	 * 
	 * @param x
	 *            The x coordinate, the left edge of the surface is 0.
	 * @param y
	 *            The y coordinate, the top edge is the surface is 0.
	 * @return The value of a single pixel, as an RGBA object.
	 */
	public RGBA getPixelRGBA(int x, int y) {
		ensureNotFreed();

		RGBA result = new RGBA(0, 0, 0);
		this.surface_getPixelRGBA(this.pSurface, result, x, y);
		return result;
	}

	private native void surface_getPixelRGBA(long pSurface, RGBA result, int x,
			int y);

	/**
	 * Sets the value of a single pixel.
	 * 
	 * @param x
	 *            The x coordinate, the left edge of the surface is 0.
	 * @param y
	 *            The y coordinate, the top edge is the surface is 0.
	 * @param value
	 *            The new value for the pixel. The meaning of this value will
	 *            depend on the Surface's pixel format. For an easier to
	 *            understand (but slower) version is
	 *            {@link #setPixel(int, int, RGBA)}.
	 */
	public void setPixel(int x, int y, int value) {
		ensureNotFreed();

		this.surface_setPixel(this.pSurface, x, y, value);
	}

	private native void surface_setPixel(long pSurface, int x, int y, int value);

	/**
	 * Sets the value of a single pixel.
	 * 
	 * @param x
	 *            The x coordinate, the left edge of the surface is 0.
	 * @param y
	 *            The y coordinate, the top edge is the surface is 0.
	 * @param color
	 *            The color (including alpha channel) for the pixel.
	 */
	public void setPixel(int x, int y, RGBA color) {
		ensureNotFreed();

		this.surface_setPixel(this.pSurface, x, y, color.r, color.g, color.b,
				color.a);
	}

	private native void surface_setPixel(long pSurface, int x, int y, int r,
			int g, int b, int a);

	/**
	 * Fills the entire surface with a given colour
	 * 
	 * @param color
	 * @throws JameRuntimeException
	 */
	public void fill(int color) throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill(this.pSurface, 0, 0,
				this.width, this.height, color));
	}

	/**
	 * Fills part of the surface with a given colour.
	 * 
	 * @param rect
	 *            The portion of the surface to fill
	 * @param color
	 * @throws JameRuntimeException
	 */
	public void fill(Rect rect, int color) throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill(this.pSurface, rect.x,
				rect.y, rect.width, rect.height, color));
	}

	private native int surface_fill(long pSurface, int x, int y, int width,
			int height, int color);

	/**
	 * Fills the entire surface with a given colour
	 * 
	 * @param color
	 * @throws JameRuntimeException
	 */
	public void fill(RGBA color) throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill2(this.pSurface, 0, 0,
				this.width, this.height, color.r, color.g, color.b, color.a));
	}

	/**
	 * Fills the entire surface with a given colour
	 * 
	 * @param red
	 *            0..255
	 * @param green
	 *            0..255
	 * @param blue
	 *            0..255
	 * @param alpha
	 *            0..255
	 * @throws JameRuntimeException
	 */
	public void fill(int red, int green, int blue, int alpha)
			throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill2(this.pSurface, 0, 0,
				this.width, this.height, red, green, blue, alpha));
	}

	/**
	 * Fills part of the surface with a given colour.
	 * 
	 * @param rect
	 *            The portion of the surface to fill
	 * @param color
	 * @throws JameRuntimeException
	 */
	public void fill(Rect rect, RGBA color) throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill2(this.pSurface, rect.x,
				rect.y, rect.width, rect.height, color.r, color.g, color.b,
				color.a));
	}

	/**
	 * Fills part of the surface with a given colour.
	 * 
	 * @param rect
	 *            The portion of the surface to fill
	 * @param red
	 *            0..255
	 * @param green
	 *            0..255
	 * @param blue
	 *            0..255
	 * @param alpha
	 *            0..255
	 * @throws JameRuntimeException
	 */
	public void fill(Rect rect, int red, int green, int blue, int alpha)
			throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_fill2(this.pSurface, rect.x,
				rect.y, rect.width, rect.height, red, green, blue, alpha));
	}

	private native int surface_fill2(long pSurface, int x, int y, int width,
			int height, int red, int green, int blur, int alpha);

	/**
	 * This should only be called on the Surface returned from
	 * {@link Video#setMode}. Switches the back buffer, and the visible buffer.
	 * 
	 * @throws JameRuntimeException
	 */
	void flip() throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_flip(this.pSurface));
	}

	private native int surface_flip(long pSurface);

	/**
	 * Blits <code>this</code> onto
	 * <code>dest</this>, aligning their top left corners.
	 * Uses {@link BlendMode#NONE}.
	 * 
	 * @throws JameRuntimeException
	 */
	public void blit(Surface dest) throws JameRuntimeException {
		this.blit(dest, 0, 0, BlendMode.NONE);
	}

	/**
	 * Blits <code>this</code> onto
	 * <code>dest</this>, aligning their top left corners.
	 * 
	 * @throws JameRuntimeException
	 */
	public void blit(Surface dest, BlendMode blendMode)
			throws JameRuntimeException {
		this.blit(dest, 0, 0, blendMode);
	}

	/**
	 * Blits <code>this</code> onto <code>dest</this>.
	 * The top left of <code>this</code> is lined up with <code>(x,y)</code> of
	 * <code>dest</code>. Uses {@link BlendMode#NONE}.
	 * 
	 * @throws JameRuntimeException
	 */
	public void blit(Surface dest, int x, int y) throws JameRuntimeException {
		this.blit(dest, x, y, BlendMode.NONE);
	}

	/**
	 * Blits <code>this</code> onto <code>dest</this>.
	 * The top left of <code>this</code> is lined up with <code>(x,y)</code> of
	 * <code>dest</code>.
	 * 
	 * @throws JameRuntimeException
	 */
	public void blit(Surface dest, int x, int y, BlendMode blendMode)
			throws JameRuntimeException {
		ensureNotFreed();

		if (blendMode == BlendMode.NONE) {
			Jame.checkRuntimeStatus(this.surface_blit(this.pSurface,
					dest.pSurface, x, y));
		} else {
			Jame.checkRuntimeStatus(this.surface_blit3(this.pSurface, 0, 0,
					this.getWidth(), this.getHeight(), dest.pSurface, x, y,
					this.getWidth(), this.getHeight(), blendMode.code));
		}
	}

	private native int surface_blit(long pSrc, long pDest, int x, int y);

	/**
	 * Blits <code>this</code> onto <code>dest</code>. The top left of srcRect
	 * is lined up with the top left of destRect. Only the pixels within srcRect
	 * are blitted, and only the pixels in destRect are altered. i.e. the
	 * clipping is defined by the area of overlap of the two rectangles.
	 * 
	 * Uses {@link BlendMode#NONE}.
	 */
	public void blit(Rect srcRect, Surface dest, Rect destRect)
			throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_blit2(this.pSurface, srcRect.x,
				srcRect.y, srcRect.width, srcRect.height, dest.pSurface,
				destRect.x, destRect.y, destRect.width, destRect.height));
	}

	/**
	 * Blits <code>this</code> onto <code>dest</code>. The top left of srcRect
	 * is lined up with (x,y) of destRect. Only the pixels within srcRect are
	 * blitted.
	 * 
	 * Uses {@link BlendMode#NONE}.
	 */
	public void blit(Rect srcRect, Surface dest, int x, int y)
			throws JameRuntimeException {
		ensureNotFreed();

		Jame.checkRuntimeStatus(this.surface_blit2(this.pSurface, srcRect.x,
				srcRect.y, srcRect.width, srcRect.height, dest.pSurface, x, y,
				srcRect.width, srcRect.height));
	}

	/**
	 * Blits <code>this</code> onto <code>dest</code>. The top left of srcRect
	 * is lined up with (x,y) of destRect. Only the pixels within srcRect are
	 * blitted.
	 * 
	 * Uses {@link BlendMode#NONE}.
	 */
	public void blit(Rect srcRect, Surface dest, int x, int y,
			BlendMode blendMode) throws JameRuntimeException {
		ensureNotFreed();

		if (blendMode == BlendMode.NONE) {
			Jame.checkRuntimeStatus(this.surface_blit2(this.pSurface,
					srcRect.x, srcRect.y, srcRect.width, srcRect.height,
					dest.pSurface, x, y, srcRect.width, srcRect.height));
		} else {
			Jame.checkRuntimeStatus(this.surface_blit3(this.pSurface,
					srcRect.x, srcRect.y, srcRect.width, srcRect.height,
					dest.pSurface, x, y, srcRect.width, srcRect.height,
					blendMode.code));
		}
	}

	private native int surface_blit2(long pSrc, int sx, int sy, int swidth,
			int sheight, long pDest, int dx, int dy, int dwidth, int dheight);

	private native int surface_blit3(long pSrc, int sx, int sy, int swidth,
			int sheight, long pDest, int dx, int dy, int dwidth, int dheight,
			int flags);

	/**
	 * Converts a Surface so that it uses a pixel format compatible with the
	 * display surface. Blitting is faster when the pixel formats are
	 * compatable, so it is highly recommended that you use convert on calls to
	 * <code new {@link #Surface(String)}</code>.
	 * 
	 * You will probably want to {@link #free()} this surface afterwards.
	 * 
	 * @return A new Surface object identical in size and appearance.
	 * @throws JameRuntimeException
	 */
	public Surface convert() throws JameRuntimeException {
		ensureNotFreed();

		Surface result = new Surface();
		Jame.checkRuntimeStatus(result.surface_displayFormat(this.pSurface));
		return result;
	}

	private native int surface_displayFormat(long pSrc);

	public Surface zoom(double zoomX, double zoomY, boolean smooth)
			throws JameRuntimeException {
		ensureNotFreed();

		Surface result = new Surface();

		Jame.checkRuntimeStatus(this.surface_zoom(result, this.pSurface, zoomX,
				zoomY, smooth));

		return result;
	}

	private native int surface_zoom(Surface dest, long pSrc, double zoomX,
			double zoomY, boolean smooth);

	/**
	 * Rotates and zooms a Surface, creating a new Surface. Due to the rotation,
	 * even if the scale is <= 1, the resulting surface can be larger than the
	 * source.
	 * 
	 * @param angle
	 *            Angle of rotate in degrees
	 * @param zoom
	 *            The scaling factor (1 => no zoom).
	 * @param smooth
	 *            Whether anti-aliasing is on or off.
	 * @return A new surface, rotated and zoomed.
	 * @throws JameRuntimeException
	 */
	public Surface rotoZoom(double angle, double zoom, boolean smooth)
			throws JameRuntimeException {
		ensureNotFreed();

		Surface result = new Surface();
		Jame.checkRuntimeStatus(this.surface_rotoZoom(result, this.pSurface,
				angle, zoom, smooth));

		return result;

	}

	private native int surface_rotoZoom(Surface dest, long pSrc, double angle,
			double zoom, boolean smooth);

	/**
	 * Pixel based collision detection. Determines if any of a surface's
	 * non-transparent pixels touch another surface's non-transparent pixels.
	 * <p>
	 * Both Surfaces must be 32bits per pixel with alpha channels.
	 * <p>
	 * When the surfaces's rectangles miss each other, the algorithm is Order(1)
	 * (i.e. fast), but when the rectangles overlap, it is O(n), where n is the
	 * area of overlap.
	 * 
	 * @param other
	 *            The other Surface taking part in the pixel collision test.
	 * @param dx
	 *            The X offset for this surface. 0 would have the left edges of
	 *            both surfaces lined up. A positive number: other is to the
	 *            left of this.
	 * @param dy
	 *            The Y offset for this surface. 0 would have the top edges of
	 *            bother surfaces lined up. A positive number: other is higher
	 *            than this.
	 * @param alphaThreshold
	 *            A measure of how opaque the pixels have to be when considering
	 *            if they touch. A value of 0 would skip even slightly
	 *            transparent pixels. A value of 255 would treat both Surfaces
	 *            as if they were both solidly filled. A value around 60 works
	 *            well. The reason for using an alphaTreshold is to avoid
	 *            anti-aliased edges of objects causing a collision with another
	 *            anti-aliased edge, but the game player doesn't see that as
	 *            touching, they are still more than a pixel away. However, if
	 *            you want to test for collisions with very transparent objects
	 *            - a soap bubble perhaps, then you might be better off with a
	 *            very low, or even zero alphaThreashold.
	 * @return true if one or more sufficiently opaque pixels touch.
	 */
	public boolean pixelOverlap(Surface other, int dx, int dy,
			int alphaThreshold) {
		ensureNotFreed();

		return this.surface_pixelOverlap(this.pSurface, other.pSurface, dx, dy,
				alphaThreshold);
	}

	private native boolean surface_pixelOverlap(long pA, long pB, int dx,
			int dy, int threshold);

	@Override
	public String toString() {
		return "Surface (" + getWidth() + "," + getHeight() + ") "
				+ this.hashCode() + " -> " + this.pSurface;
	}
}
