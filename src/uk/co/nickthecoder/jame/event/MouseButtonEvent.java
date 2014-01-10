/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

public class MouseButtonEvent extends MouseEvent
{
    public static final int BUTTON_LEFT = 1;
    public static final int BUTTON_MIDDLE = 2;
    public static final int BUTTON_RIGHT = 3;
    public static final int BUTTON_WHEELUP = 4;
    public static final int BUTTON_WHEELDOWN = 5;
    
    /**
     * The input device index
     */
    public int which;

    /**
     * The mouse button index (BUTTON_LEFT, BUTTON_MIDDLE, BUTTON_RIGHT,
     * BUTTON_WHEELUP, BUTTON_WHEELDOWN)
     */
    public int button;

    /**
     * SDL_PRESSED or SDL_RELEASED. You can also use isPressed and/or isReleased
     * for brevity
     */
    public int state;

    public boolean isPressed()
    {
        return this.state == STATE_PRESSED;
    }

    public boolean isReleased()
    {
        return this.state == STATE_RELEASED;
    }

    public String toString()
    {
        return "MouseButtonEven{ which=" + which + ", button=" + button + ", state=" + state + ", x=" + x + ", y=" + y
            + " }";
    }
}
