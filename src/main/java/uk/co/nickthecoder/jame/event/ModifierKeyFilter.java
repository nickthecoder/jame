/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.util.AbstractEventFilter;

/**
 * Accepts or rejects the keyboard modifiers given to it. A simple example, would test just for the left shift key.
 * It will accept if the left shift key is pressed, and no other modifier keys are pressed. More often, we want to
 * allow either contol key to be pressed for it to be accepted.
 * Things get a little more tricky when we want more than one type of modifier. For example, accept only if either
 * shift key is pressed AND either control key.
 * <p>
 * Implementation notes.
 * </p>
 * <p>
 * We have a single "forbiddenMask". If the modifiers presented match up with any of the forbiddenMask's bits, then it
 * is rejected. So, in the case of "either shift AND and either ctrl", then the forbiddenMask will be "left alt" +
 * "right alt".
 * </p>
 * <p>
 * We also have a list of required masks. In our example, the first will be left shift + right shift, and the second
 * will be left ctrl + right ctrl.
 * </p>
 * 
 */
public final class ModifierKeyFilter extends AbstractEventFilter<KeyboardEvent>
{
    public static final ModifierKeyFilter NONE = new ModifierKeyFilter();
    public static final ModifierKeyFilter SHIFT = new ModifierKeyFilter(ModifierKey.LSHIFT, ModifierKey.RSHIFT);
    public static final ModifierKeyFilter CTRL = new ModifierKeyFilter(ModifierKey.LCTRL, ModifierKey.RCTRL);
    public static final ModifierKeyFilter ALT = new ModifierKeyFilter(ModifierKey.LALT, ModifierKey.RALT);

    private List<Integer> requiredMasks = new ArrayList<Integer>();

    private int forbiddenMask;

    /**
     * Accepts if ANY of the modifier keys listed are pressed.
     * For example <code>ModifierKeyFilter( ModifierKey.LSHIFT, ModifierKey.RSHIFT )</code> will accept when either of
     * the keys are down.
     * 
     * @param modifierKeys
     */
    public ModifierKeyFilter(ModifierKey... modifierKeys)
    {
        // By default, we only care about shift, ctrl and alt keys. We ignore CAPS LOCK, NUMBER LOCK.
        forbiddenMask = 0xfff;

        int mask = 0;
        for (ModifierKey key : modifierKeys) {
            mask = mask |= key.code;
        }
        if (mask != 0) {
            this.requiredMasks.add(mask);
        }
        this.forbiddenMask &= ~mask;
    }

    public ModifierKeyFilter and(ModifierKeyFilter... filters)
    {
        ModifierKeyFilter result = new ModifierKeyFilter();
        result.forbiddenMask = 0;

        for (ModifierKeyFilter filter : filters) {
            result.forbiddenMask |= filter.forbiddenMask;
        }
        result.forbiddenMask |= this.forbiddenMask;


        for (int mask : this.requiredMasks) {
            result.requiredMasks.add(mask);
            result.forbiddenMask &= ~mask;
        }

        for (ModifierKeyFilter filter : filters) {
            for (int mask : filter.requiredMasks) {
                result.forbiddenMask &= ~mask;
                result.requiredMasks.add(mask);
            }
        }

        return result;
    }

    /*
     * public ModifierKeyFilter forbid(ModifierKey... modifierKeys)
     * {
     * for (ModifierKey key : modifierKeys) {
     * this.forbiddenMask |= key.code;
     * }
     * return this;
     * }
     */

    public boolean accept(KeyboardEvent ke)
    {
        return accept(ke.modifiers);
    }

    /**
     * 
     * @param flags
     * @return
     */
    public boolean accept(int flags)
    {
        if ((flags & forbiddenMask) != 0) {
            return false;
        }
        for (int mask : requiredMasks) {
            if ((flags & mask) == 0) {
                return false;
            }
        }
        return true;
    }

    public String toString()
    {
        StringBuffer result = new StringBuffer();

        result.append("ModifierKeyFilter( forbiddenMask:" + this.forbiddenMask);
        for (int mask : requiredMasks) {
            result.append(" requiredMask:" + mask);
        }
        result.append(")");

        return result.toString();
    }
}
