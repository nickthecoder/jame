/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Window;

/**
 * Holds the common stuff from MouseMotionEvent and MouseButtonEvent
 * 
 * @author nick
 * 
 */
public class MouseEvent extends Event
{
    /**
     * The X coordinates of the mouse at press/release time
     */
    public int x;

    /**
     * The Y coordinates of the mouse at press/release time
     */
    public int y;

    public int windowID;

    /**
     * The unique id for the window or 0.
     */
    public int windowId;

    public Window getWindow()
    {
        return Window.getWindowById(windowID);
    }

}
