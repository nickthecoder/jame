/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * Holds a colour value using channels : red, green, blue and alpha. Each channel should be in the
 * range 0..255, but this is not enforced.
 * <p>
 * RGBA are immutable, they cannot be changed once they have been created. Therefore you can pass
 * them to any 3rd party methods with no fear that the 3rd party will alter your colour.
 */
public final class RGBA
{
    public static final RGBA WHITE = new RGBA(255, 255, 255, 255);
    public static final RGBA BLACK = new RGBA(0, 0, 0, 255);
    public static final RGBA TRANSPARENT = new RGBA(0, 0, 0, 0);

    /**
     * Red (0..255)
     */
    public final int r;

    /**
     * Green (0..255)
     */
    public final int g;

    /**
     * Blue (0..255)
     */
    public final int b;

    /**
     * Alpha (0 = fully transparent, 255 = fully opaque).
     */
    public final int a;

    /**
     * Parses colors in the form #rrggbbaa #rrggbb and #rgb
     * 
     * @throws JameException
     */
    public static RGBA parse( String str )
        throws JameException
    {
        return parse(str, true, true);
    }

    /**
     * Parses colours in the form #rrggbbaa, #rrggbb and #rgb
     * 
     * @param str
     *        The string to be parsed.
     * @param allowNull
     *        If true, then str can be null or an empty string, and the result will be null.
     * @param includeAlpha
     *        Allow an alpha channel, if one is not specified, it is assumed to be fully opaque
     *        (255).
     * @return The RGBA
     * @throws JameException
     *         If the format of the string isn't valid.
     */
    public static RGBA parse( String str, boolean allowNull, boolean includeAlpha )
        throws JameException
    {
        if (allowNull && ((str == null) || ("".equals(str)))) {
            return null;
        }

        str = str.trim().toUpperCase();

        if (str.startsWith("#")) {
            if (str.length() == 9) {
                long color = Long.parseLong(str.substring(1), 16);
                if (!includeAlpha) {
                    if ((color & 0x000000ff) != 255) {
                        throw new JameException("Cannot include an alpha value");
                    }
                }
                return new RGBA((int) ((color & 0xff000000) >> 24),
                    (int) ((color & 0x00ff0000) >> 16),
                    (int) ((color & 0x0000ff00) >> 8), (int) (color & 0x000000ff));
            } else if (str.length() == 7) {
                int color = Integer.parseInt(str.substring(1), 16);
                return new RGBA((color & 0xff0000) >> 16, (color & 0x00ff00) >> 8, color & 0x0000ff);
            } else if (str.length() == 4) {
                int color = Integer.parseInt(str.substring(1), 16);
                return new RGBA(((color & 0xf00) >> 8) * 17, ((color & 0x0f0) >> 4) * 17,
                    (color & 0xf) * 17);
            }
        }
        throw new JameException("Color parse failed : " + str);
    }

    /**
     * A copy constructor. As RGBA is now immutable, there isn't a good reason for this any more!
     * 
     * @param other
     */
    public RGBA( RGBA other )
    {
        this(other.r, other.g, other.b, other.a);
    }

    /**
     * Creates a fully opaque colour.
     * 
     * @param red
     *        0 to 255
     * @param green
     *        0 to 255
     * @param blue
     *        0 to 255
     */
    public RGBA( int red, int green, int blue )
    {
        this(red, green, blue, 255);
    }

    /**
     * Creates a colour including an alpha value
     * 
     * @param red
     *        0 to 255
     * @param green
     *        0 to 255
     * @param blue
     *        0 to 255
     * @param alpha
     *        0 to 255
     */
    public RGBA( int red, int green, int blue, int alpha )
    {
        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = alpha;
    }

    /**
     * Creates a string representation of this colour, ignoring its alpha value.
     * 
     * @return A String in the form #rrggbb
     */
    public String getRGBCode()
    {
        return "#" +
            (this.r < 16 ? "0" : "") + Integer.toHexString(this.r) +
            (this.g < 16 ? "0" : "") + Integer.toHexString(this.g) +
            (this.b < 16 ? "0" : "") + Integer.toHexString(this.b);
    }

    /**
     * Creates a string representation of the colour, inclusing its alpha value.
     * 
     * @return A String in the form #rrggbbaa
     */
    public String getRGBACode()
    {
        return this.getRGBCode() + (this.a < 16 ? "0" : "") + Integer.toHexString(this.a);
    }

    /**
     * See {@link #getRGBACode()}
     */
    @Override
    public String toString()
    {
        return this.getRGBACode();
    }

    /**
     * Tests if two colours are equal. Note that two colours with an alpha of zero are both 100%
     * transparent, but are NOT considered equal if their red, green or blue values differ.
     */
    @Override
    public boolean equals( Object obj )
    {
        if (obj instanceof RGBA) {
            RGBA other = (RGBA) obj;
            return (this.a == other.a) && (this.r == other.r) &&
                (this.g == other.g) && (this.b == other.b);
        }
        return false;
    }

}
