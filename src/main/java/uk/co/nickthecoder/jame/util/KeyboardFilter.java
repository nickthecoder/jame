package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKeySet;

public class KeyboardFilter implements EventFilter<KeyboardEvent>
{
    /**
     * Accepts regular key presses, with NO modifier keys held down.
     */
    public static final KeyboardFilter regularPress = new KeyboardFilter().pressed().modifiers(ModifierKeySet.NONE);

    /**
     * Accepts key presses, with either of the shift keys held down.
     */
    public static final KeyboardFilter shiftPress = new KeyboardFilter().pressed().modifiers(ModifierKeySet.SHIFT);

    /**
     * Accepts key presses, with either of the control keys held down.
     */
    public static final KeyboardFilter ctrlPress = new KeyboardFilter().pressed().modifiers(ModifierKeySet.CTRL);

    /**
     * Accepts key presses, with either of the alt keys held down.
     */
    public static final KeyboardFilter altPress = new KeyboardFilter().pressed().modifiers(ModifierKeySet.ALT);

    /**
     * Accepts key presses, with either of the meta keys held down.
     */
    public static final KeyboardFilter metaPress = new KeyboardFilter().pressed().modifiers(ModifierKeySet.META);

    private Boolean expectedPress = true;

    private ModifierKeySet modifiers = null;
    
    private Integer scanCode = null;
        
    private Integer symbol = null;

    @Override
    public boolean accept(KeyboardEvent event)
    {
        if (event.isPressed() != expectedPress) {
            return false;
        }

        if ((modifiers != null) && (!event.modifier(modifiers))) {
            return false;
        }

        if ((this.scanCode != null) && (event.scanCode != this.scanCode) ) {
            return false;
        }
        
        if ((this.symbol != null) && (event.symbol != this.symbol) ) {
            return false;
        }
        return true;
    }

    public KeyboardFilter modifiers(ModifierKeySet modifiers)
    {
        this.modifiers = modifiers;
        return this;
    }

    public KeyboardFilter pressed()
    {
        this.expectedPress = true;
        return this;
    }

    public KeyboardFilter released()
    {
        this.expectedPress = false;
        return this;
    }

    public KeyboardFilter symbol( int symbol )
    {
        this.symbol = symbol;
        return this;
    } 

    public KeyboardFilter scanCode( int scanCode )
    {
        this.scanCode = scanCode;
        return this;
    } 

}
