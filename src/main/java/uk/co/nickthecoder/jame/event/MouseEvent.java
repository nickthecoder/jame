/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

/**
 * Holds the common data from {@link MouseMotionEvent}, {@link MouseButtonEvent} and {@link MouseWheelEvent}.
 * 
 */
public class MouseEvent extends EventForWindow
{
    /**
     * The input device identifier (this is called "which" in SDL).
     */
    public int mouseID;

    /**
     * The X coordinates of the mouse at press/release time
     */
    public int x;

    /**
     * The Y coordinates of the mouse at press/release time
     */
    public int y;

    @Override
    public void postConstruct()
    {
        if (window != null) {
            window.onMouseEvent(this);
        }
    }
}
