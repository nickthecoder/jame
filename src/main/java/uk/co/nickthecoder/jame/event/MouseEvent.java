/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Events;

/**
 * Holds the common data from {@link MouseMotionEvent}, {@link MouseButtonEvent} and {@link MouseWheelEvent}.
 * 
 */
public class MouseEvent extends EventForWindow implements WithModifiers
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

    /**
     * The keyboard modifiers that are down/locked when this mouse button event occurred.
     * This is the bitwise or of {@link ModifierKey#code} for each modifier key pressed/locked.
     * <p>
     * Note, this is implemented by Jame, there is no such field in SDL.
     * </p>
     */
    public int modifiers;
    
    @Override
    public void postConstruct()
    {
        super.postConstruct();
        this.modifiers = Events.getKeyboardModifiers();
    }
    
    @Override
    public int getModifiers()
    {
        return modifiers;
    }
}
