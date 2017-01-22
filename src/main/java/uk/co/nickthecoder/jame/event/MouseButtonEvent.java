/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;


/**
 * 
 * Note that mouse wheel events are now sent as {@link MouseWheelEvent}s, so there
 */
public class MouseButtonEvent extends MouseEvent implements WithModifiers
{
    /**
     * The mouse button (currently in the range 1..5). Note that the mouse wheel are NOT button events any more,
     * so 4 and 5 are NOT mouse wheel events.
     */
    public int button;

    /**
     * true if the button was pressed, false if it was released.
     */
    public boolean pressed;

    /**
     * A typed ({@link MouseButton}) version of the integer {@link #button}.
     */
    public MouseButton mouseButton;

 

    @Override
    public void postConstruct()
    {
        super.postConstruct();
        this.mouseButton = MouseButton.findButton(button);
    }

    public boolean isPressed()
    {
        return this.pressed;
    }

    public boolean isReleased()
    {
        return !this.pressed;
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
        return "MouseButtonEven{ windowID=" + windowID + " mouseID=" + mouseID +
            ", button=" + mouseButton + ", pressed=" + pressed +
            ", x=" + x + ", y=" + y + " modifiers=" + modifiers + " }";
    }

}
