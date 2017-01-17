/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

/**
 * A defines a simple rectangle for use by Surface's blit methods.
 * 
 * Note that Rect is mutable (i.e. it can change), and Java passes objects by reference, therefore
 * you may want to pass copies of rectangles which must not be altered. None of Jame's methods
 * modify Rect arguments.
 * 
 */
public class Rect
{
    public int x;
    public int y;
    public int width;
    public int height;

    public Rect( int x, int y, int width, int height )
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect( Rect other )
    {
        this.x = other.x;
        this.y = other.y;
        this.width = other.width;
        this.height = other.height;
    }

    public boolean contains( int x, int y )
    {
        return ((x >= this.x) && (y >= this.y) && (x < this.x + this.width) && (y < this.y + this.height));
    }

    public Rect intersection( Rect other )
    {
        int left = Math.max(this.x, other.x);
        int right = Math.min(this.x + this.width, other.x + other.width);
        int top = Math.max(this.y, other.y);
        int bottom = Math.min(this.y + this.height, other.y + other.height);

        return new Rect(left, top, right - left, bottom - top);
    }

    @Override
    public String toString()
    {
        return "Rect(" + this.x + "," + this.y + ", " + this.width + "," + this.height + ")";
    }
}
