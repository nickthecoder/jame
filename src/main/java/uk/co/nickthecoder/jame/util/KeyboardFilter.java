package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKey;
import uk.co.nickthecoder.jame.event.ModifierKeyFilter;
import uk.co.nickthecoder.jame.event.ScanCode;
import uk.co.nickthecoder.jame.event.Symbol;

public class KeyboardFilter extends AbstractEventFilter<KeyboardEvent>
{
    /**
     * Accepts regular key presses, with NO modifier keys held down.
     */
    public static final KeyboardFilter regularPress = new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.NONE);

    /**
     * Accepts key presses, with either of the shift keys held down.
     */
    public static final KeyboardFilter shiftPress = new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT);

    /**
     * Accepts key presses, with either of the control keys held down.
     */
    public static final KeyboardFilter ctrlPress = new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.CTRL);

    /**
     * Accepts key presses, with either of the alt keys held down.
     */
    public static final KeyboardFilter altPress = new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.ALT);

    private Boolean expectedPress = null;

    private ModifierKeyFilter modifierFilter = null;

    private Integer scanCode = null;

    private Integer symbol = null;

    private boolean includeRepeats = false;

    @Override
    public boolean accept(KeyboardEvent event)
    {
        if ((expectedPress != null) && (event.pressed != expectedPress)) {
            return false;
        }

        if ((modifierFilter != null) && (!modifierFilter.accept(event.modifiers))) {
            return false;
        }

        if ((this.scanCode != null) && (event.scanCode != this.scanCode)) {
            return false;
        }

        if ((this.symbol != null) && (event.symbol != this.symbol)) {
            return false;
        }

        if ((!this.includeRepeats) && (event.repeated)) {
            return false;
        }

        return true;
    }

    
    public KeyboardFilter modifier(ModifierKey modifier)
    {
        return modifiers(new ModifierKeyFilter(modifier));
    }

    public KeyboardFilter modifiers(ModifierKeyFilter modifierFilter)
    {
        this.modifierFilter = modifierFilter;
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

    public KeyboardFilter symbol(int symbol)
    {
        this.symbol = symbol;
        return this;
    }
    
    public KeyboardFilter symbol(Symbol symbol)
    {
        this.symbol = symbol.value;
        return this;
    }

    public KeyboardFilter scanCode(ScanCode scanCode)
    {
        return scanCode(scanCode.value);
    }
    
    public KeyboardFilter scanCode(int scanCode)
    {
        this.scanCode = scanCode;
        return this;
    }

    public KeyboardFilter includeRepeats()
    {
        this.includeRepeats = true;
        return this;
    }
    
    public String toString()
    {
        return "KeyboardFilter symbol=" + symbol + " scanCode=" + scanCode + " expectedPress=" + expectedPress +
            " modifierKeyFilter=" + modifierFilter;
    }
}
