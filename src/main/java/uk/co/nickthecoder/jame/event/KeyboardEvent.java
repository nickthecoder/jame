/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Window;

public class KeyboardEvent extends Event implements Keys
{
    /**
     * Either {@link Event#STATE_PRESSED} or {@link Event#STATE_RELEASED}.
     */
    public int state;

    /**
     * Identifies a physical key. A US keyboard will give the same scanCode as non-US keyboard, despite being labelled
     * differently. This is useful in games, because you may want to use the keys labelled W,A,S and D, whereas on
     * a non-US keyboard, the keys at these locations may not be labelled as WASD, but will return the same scanCode.
     * <p>
     * Use this when you care about the position of a key on the keyboard, as opposed to what the label is on that key.
     * <p>
     */
    public int scanCode;

    /**
     * Which virtual key has been pressed or released. This is "virtual" because the value will depend on keyboard
     * layout.
     * For example, the "Y" key on a US keyboard will be a "Z" symbol if you switch to using a German keyboard
     * layout (because on a German keyboard, "Z" is between "T" and "U").
     * <p>
     * The set of symbols is held as static fields in the {@link Keys} class (which this class extends).
     * </p>
     * <p>
     * Use this when you care about the label of the key, rather than the position of a key on the keyboard.
     * <p>
     */
    public int symbol;

    /**
     * Which combination of Shift, Ctrl, Alt and "Flag" key were held down when this KeyboardEvent was fired.
     * See {@link #modifier(ModifierKey)}.
     */
    public int modifiers;

    /**
     * Maps the key's symbol to a Key. Lazily evaluated.
     */
    private Key key;

    /**
     * The unique id for the window with keyboard focus, or 0, if there is none.
     */
    public int windowID;

    @Override
    public void postConstruct()
    {
        Window window = getWindow();
        if ( window != null) {
            window.onKeyboardEvent(this);
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

    /**
     * Tests if the given ModifierKey is pressed. Note if modifier is NONE, then this returns true only if NO modifiers
     * are pressed.
     * 
     * @param modifier
     * @return true if the modifier key is pressed, false otherwise.
     */
    public boolean modifier(ModifierKey modifier)
    {
        return modifier.pressed(this.modifiers);
    }

    /**
     * Checks if any of a set of modifier keys are pressed.
     * This is particularly handy to test for either LSHIFT or RSHIFT etc.
     * 
     * @param modifiers
     * @return True if any of the ModifierKeys in the ModifierKeySet are down.
     */
    public boolean modifier(ModifierKey.ModifierKeySet modifiers)
    {
        return modifiers.pressed(this.modifiers);
    }

    /**
     * 
     * @return A {@link Key} enum corresponding to {@link #symbol}.
     */
    public Key getKey()
    {
        if (this.key == null) {
            this.key = Key.findKey(symbol);
        }
        return this.key;
    }

    /**
     * 
     * @return The Window with keyboard focus, or null if no window has focus.
     */
    public Window getWindow()
    {
        return Window.getWindowById(windowID);
    }

    @Override
    public String toString()
    {
        return "KeyboardEvent{ state=" + this.state + ", scanCode=" + this.scanCode + ", symbol=" + this.symbol
            + ", modifiers="
            + this.modifiers + " }";
    }
}
