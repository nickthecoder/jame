package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.ModifierKey;

public class KeyboardEvent extends Event
{
    public int state;
    public int scanCode;
    public int symbol;
    public int modifiers;

    /**
     * The character that was just typed. Only populated during a key down
     * event, and only if Events.enableKeyTranslation has been set to true.
     */
    public char c;

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
     * @return
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

    @Override
    public String toString()
    {
        return "KeyboardEvent{ state=" + this.state + ", scanCode=" + this.scanCode + ", symbol=" + this.symbol + ", modifiers="
            + this.modifiers + " }";
    }
}
