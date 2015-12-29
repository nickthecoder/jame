/*******************************************************************************
 * Copyright (c) 2014 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

public class WindowEvent extends Event
{
    public static final int TYPE_MOUSE_FOCUS = 1;
    public static final int TYPE_INPUT_FOCUS = 2;
    public static final int TYPE_RESTORE = 3;

    public int type;

    public int gain;

    public WindowEvent()
    {
    }

    public boolean gained()
    {
        return this.gain == 1;
    }

    public int getType()
    {
        return this.type;
    }

    public boolean gainedInputFocus()
    {
        return (this.type == TYPE_INPUT_FOCUS) && (this.gain == 1);
    }

    public boolean lostInputFocus()
    {
        return (this.type == TYPE_INPUT_FOCUS) && (this.gain == 0);
    }

    public boolean gainedMouseFocus()
    {
        return (this.type == TYPE_MOUSE_FOCUS) && (this.gain == 1);
    }

    public boolean lostMouseFocus()
    {
        return (this.type == TYPE_MOUSE_FOCUS) && (this.gain == 0);
    }

    public boolean restored()
    {
        return (this.type == TYPE_RESTORE) && (this.gain == 1);
    }

    public boolean iconified()
    {
        return (this.type == TYPE_RESTORE) && (this.gain == 0);
    }

}
