/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.util.ModifierKeyFilter;

/**
 * Modifier keys, the common ones are SHIFT, CTRL, ALT, but also include number lock, caps lock.
 * <p>
 * Note that the "GUI" key, sometimes called the "windows key", it NOT included in this list, because it cannot be used
 * in a cross platform manner (Gnome3 reserves it for iteself, and SDL is not informed when it is pressed).
 * </p>
 */
public enum ModifierKey
{
    NONE(0x0000),
    LSHIFT(0x0001),
    RSHIFT(0x0002),
    LCTRL(0x0040),
    RCTRL(0x0080),
    LALT(0x0100),
    RALT(0x0200),
    NUM_LOCK(0x1000),
    CAPS_LOCK(0x2000),
    MODE(0x4000);

    public final int code;

    ModifierKey(int code)
    {
        this.code = code;
    }

    public boolean pressed(int flags)
    {
        if (this.code == 0) {
            return flags == 0;
        } else {

            return (this.code & flags) != 0;

        }
    }

    public ModifierKeyFilter or(ModifierKey other)
    {
        return new ModifierKeyFilter(this, other);
    }
}
