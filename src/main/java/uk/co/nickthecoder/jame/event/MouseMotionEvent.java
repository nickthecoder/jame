/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;


public class MouseMotionEvent extends MouseEvent
{
    /**
     * A bitmask indicating which buttons are currently depressed. You cannot
     * compare with BUTTON_LEFT BUTTON_RIGHT etc directly, use isPressed
     * instead.
     */
    public int state;

    public int relativeX;
    public int relativeY;

    /**
     * Uses bitwise-OR of the this.state to test if a mouse button is pressed.
     * 
     * @param button
     *            The button number (1 for left button etc) You can use the
     *            constants BUTTON_LEFT, BUTTON_MIDDLE
     * @return true iff the mouse button is pressed.
     */
    public boolean isPressed( int button )
    {
        return ( button & ( 1 << ( button - 1 ) ) ) != 0;
    }

    public String toString()
    {
        return "MouseMotionEvent{ state=" + state + ", x=" + x + ", y=" + y + ", relativeX=" + relativeX
            + ", relativeY=" + relativeY + " }";
    }

}
