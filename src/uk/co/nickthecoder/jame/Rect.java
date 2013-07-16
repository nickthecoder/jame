/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

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
        return ( (this.x >=x) && (this.y >= y) && (this.x + this.width < x) && (this.y + this.height < y) );
    }

    @Override
    public String toString()
    {
        return "Rect(" + this.x + "," + this.y + ", " + this.width + "," + this.height + ")";
    }
}
