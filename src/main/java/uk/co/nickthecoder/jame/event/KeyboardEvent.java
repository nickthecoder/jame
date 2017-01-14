/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;


public class KeyboardEvent extends Event implements Keys
{
    public int state; // STATE_PRESSED or STATE_RELEASED
    public int scanCode; // Not very useful
    public int symbol; // This is the most useful value
    public int modifiers;

    /**
     * Maps the key's symbol to a Key. Lazily evaluated.
     */
    private Key key;
    
    
    public boolean isPressed()
    {
        return this.state == STATE_PRESSED;
    }

    public boolean isReleased()
    {
        return this.state == STATE_RELEASED;
    }

    /**
     * Tests if the given ModifierKey is pressed. Note if modifier is NONE, then this returns true only if NO modifiers are pressed.
     * @param modifier
     * @return true if the modifier key is pressed, false otherwise.
     */
    public boolean modifier( ModifierKey modifier )
    {
        return modifier.pressed( this.modifiers );
    }

    /**
     * Checks if any of a set of modifier keys are pressed.
     * This is particularly handy to test for either LSHIFT or RSHIFT etc.
     * 
     * @param modifiers
     * @return True if any of the ModifierKeys in the ModifierKeySet are down.
     */
    public boolean modifier( ModifierKey.ModifierKeySet modifiers )
    {
        return modifiers.pressed( this.modifiers );
    }
    
    public Key getKey()
    {
        if (this.key == null) {
            this.key = Key.findKey( symbol );
        }
        return this.key;
    }

    @Override
    public String toString()
    {
        return "KeyboardEvent{ state=" + this.state + ", scanCode=" + this.scanCode + ", symbol=" + this.symbol + ", modifiers="
            + this.modifiers + " }";
    }
}
