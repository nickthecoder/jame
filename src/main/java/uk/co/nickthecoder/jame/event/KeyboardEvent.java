/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Events;
import uk.co.nickthecoder.jame.Window;
import uk.co.nickthecoder.jame.util.KeyboardFilter;

/**
 * Fired when a key is pressed, released, and also fired with "fake" presses for keyboard auto-repeat features.
 * Consider using {@link KeyboardFilter} to test if a given key combination has been pressed/released, as it is much
 * easier than trying to test the modifiers manually.
 */
public class KeyboardEvent extends EventForWindow
{
    public boolean pressed;

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
     * Which combination of Shift, Ctrl, and Alt key were held down when this KeyboardEvent was fired.
     * Consider using {@link ModifierKeyFilter} to test if the desired modifier keys are pressed.
     * You could also use {@link KeyboardFilter} to check the modifiers, scan code and symbol in one go.
     */
    public int modifiers;

    /**
     * False if this is a regular key down event, true if it is a result of holding down the key, and artificial
     * events are fired every {@link Events#DEFAULT_REPEAT_INTERVAL}ms after the key is held down for
     * {@link Events#DEFAULT_REPEAT_DELAY}.
     * <p>
     * Use {@link Events#keyboardRepeat(int, int)} to change the repeat timings.
     */
    public boolean repeated;

    /**
     * Maps the key's symbol to a Key. Lazily evaluated.
     */
    public Symbol keySymbol;

    public ScanCode keyScanCode;

    /**
     * The unique id for the window with keyboard focus, or 0, if there is none.
     */
    public int windowID;

    @Override
    public void postConstruct()
    {
        super.postConstruct();
        keyScanCode = ScanCode.findKey(scanCode);
        if (keyScanCode == null) {
            keyScanCode = ScanCode.NONE;
        }
        this.keySymbol = Symbol.findKey(symbol);
        if (keySymbol == null) {
            keySymbol = Symbol.NONE;
        }
    }

    /**
     * @return The Window with keyboard focus, or null if no window has focus.
     */
    @Override
    public Window getWindow()
    {
        return Window.getWindowById(windowID);
    }

    @Override
    public String toString()
    {
        return "KeyboardEvent{ " + (this.pressed ? "Pressed" : "Released") +
            ", scanCode=" + keyScanCode + "(" + scanCode + ") symbol=" + keySymbol + "(" + symbol + ")" +
            ", modifiers=" + this.modifiers + " repeated=" + this.repeated + " }";
    }
}
