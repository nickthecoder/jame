package uk.co.nickthecoder.jame.event;

import java.util.EnumSet;

public final class ModifierKeySet
{
    public static final ModifierKeySet NONE = new ModifierKeySet();
    public static final ModifierKeySet SHIFT = ModifierKey.LSHIFT.or(ModifierKey.RSHIFT);
    public static final ModifierKeySet CTRL = ModifierKey.LCTRL.or(ModifierKey.RCTRL);
    public static final ModifierKeySet ALT = ModifierKey.LALT.or(ModifierKey.RALT);
    public static final ModifierKeySet META = ModifierKey.LMETA.or(ModifierKey.RMETA);

    private final int code;

    public ModifierKeySet(ModifierKey... modifierKeys)
    {
        int code = 0;
        for (ModifierKey key : modifierKeys) {
            code = code |= key.code;
        }
        this.code = code;
    }

    /**
     * @deprecated
     * @param modifierKeys
     */
    @Deprecated
    public ModifierKeySet(EnumSet<ModifierKey> modifierKeys)
    {
        int code = 0;
        for (ModifierKey key : modifierKeys) {
            code |= key.code;
        }
        this.code = code;
    }

    public int getCode()
    {
        return this.code;
    }

    /**
     * 
     * @param flags
     * @return true iff any of the modifiers listed in the constructor are set within 'flags'
     */
    public boolean pressed(int flags)
    {
        if (this.code == 0) {
            return true;
        }
        return (this.code & flags) != 0;
    }

    /**
     * Are other modifier keys pressed, which were NOT listed in the constructor?
     * Use this when you want to check that other modifiers are NOT pressed.
     * 
     * @param flags
     * @param includeLockKeys
     *            Should the state of the caps lock/number lock affect the results?
     * @return true iff 'flags' contains no modifier keys other than those listed in the constructor.
     */
    public boolean others(int flags, boolean ignoreLockKeys)
    {
        if (ignoreLockKeys) {
            flags = flags & 0xfff;
        }
        return ((~this.code) & flags) != 0;
    }

    /**
     * Are other modifier keys pressed, which were NOT listed in the constructor?
     * Use this when you want to check that other modifiers are NOT pressed.
     * 
     * @param flags
     * @return true iff 'flags' contains no modifier keys other than those listed in the constructor.
     */
    public boolean others(int flags)
    {
        return others(flags, true);
    }

    /**
     * Are any of the modifier keys listed in the construct present within 'flags', and if so, ensure that
     * other modifier keys are NOT present within 'flags'.
     * <p>
     * Note, Only shift, ctrl, alt and meta keys are considered when ruling out based on which other modifiers are also
     * pressed. It does NOT include caps lock, number lock
     * </p>
     * 
     * @param flags
     * @return true iff 'flags' contains one or more of the modifiers listed in the constructor AND 'flags' does not
     *         container any modifiers not listed in the constructor.
     */
    public boolean onlyPressed(int flags)
    {
        return pressed(flags) && !others(flags, true);
    }

    public boolean onlyPressed(int flags, boolean ignoreLockKeys)
    {
        return pressed(flags) && !others(flags, ignoreLockKeys);
    }
}