/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;


public enum ModifierKey
{
    NONE(0x0000),
    LSHIFT(0x0001),
    RSHIFT(0x0002),
    LCTRL(0x0040),
    RCTRL(0x0080),
    LALT(0x0100),
    RALT(0x0200),
    LMETA(0x0400),
    RMETA(0x0800),
    NUM(0x1000),
    CAPS(0x2000),
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

    public ModifierKeySet or(ModifierKey other)
    {
        return new ModifierKeySet(this, other);
    }
}
