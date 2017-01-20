/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Window;

public class MouseButtonEvent extends MouseEvent
{
    public static final int BUTTON_LEFT = 1;
    public static final int BUTTON_MIDDLE = 2;
    public static final int BUTTON_RIGHT = 3;
    public static final int BUTTON_WHEEL_UP = 4;
    public static final int BUTTON_WHEEL_DOWN = 5;
    public static final int BUTTON_WHEEL_LEFT = 6;
    public static final int BUTTON_WHEEL_RIGHT = 7;

    /**
     * The input device index
     */
    public int which;

    /**
     * The mouse button index (BUTTON_LEFT, BUTTON_MIDDLE, BUTTON_RIGHT, BUTTON_WHEELUP, BUTTON_WHEELDOWN)
     */
    public int button;

    /**
     * SDL_PRESSED or SDL_RELEASED. You can also use isPressed and/or isReleased for brevity
     */
    public int state;

    private MouseButton mouseButton;

    @Override
    public void postConstruct()
    {
        Window window = getWindow();
        if (window != null) {
            window.onMouseButtonEvent(this);
        }
    }

    public boolean isPressed()
    {
        return this.state == STATE_PRESSED;
    }

    public boolean isReleased()
    {
        return this.state == STATE_RELEASED;
    }

    public MouseButton getMouseButton()
    {
        if (this.mouseButton == null) {
            this.mouseButton = MouseButton.findButton(this.button);
        }
        return this.mouseButton;
    }

    @Override
    public String toString()
    {
        return "MouseButtonEven{ which=" + which + ", button=" + button + ", state=" + state + ", x=" + x + ", y=" + y
            + " }";
    }
}
