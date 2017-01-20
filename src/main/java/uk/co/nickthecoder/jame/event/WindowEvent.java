/*******************************************************************************
 * Copyright (c) 2014 Nick Robinson All rights reserved. This program and the accompanying materials are made available under the terms of
 * the GNU Public License v3.0 which accompanies this distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Window;

public class WindowEvent extends Event
{

    public WindowEventType windowEventType;

    /**
     * Used internally by Jame. Populated by the JNI code in events.c, and then the java code uses it to set the
     * {@link #windowEventType}.
     */
    int windowEventTypeInt;

    /**
     * A unique identifier for this window.
     */
    public int windowID;
    /**
     * The meaning of this depends on the type of window event. See {@link #windowEventType}.
     */
    public int data1;

    /**
     * The meaning of this depends on the type of window event. See {@link #windowEventType}.
     */
    public int data2;

    public WindowEvent()
    {
    }

    /**
     * Sets {@link #windowEventType} and also fires {@link Window#onEvent(WindowEvent)}.
     */
    public void postConstruct()
    {
        windowEventType = WindowEventType.values()[windowEventTypeInt];

        Window window = this.getWindow();
        if (window != null) {
            window.onEvent(this);
        }
    }

    public WindowEventType getType()
    {
        return this.windowEventType;
    }

    public Window getWindow()
    {
        return Window.getWindowById(this.windowID);
    }

    public boolean gainedInputFocus()
    {
        return (this.windowEventType == WindowEventType.FOCUS_GAINED);
    }

    /**
     * @deprecated Replace with : {@link #getType()} == {@link WindowEventType#FOCUS_LOST}
     * @return
     */
    public boolean lostInputFocus()
    {
        return (this.windowEventType == WindowEventType.FOCUS_LOST);
    }

    /**
     * @deprecated Replace with : {@link #getType()} == {@link WindowEventType#FOCUS_GAINED}
     * @return
     */
    public boolean gainedMouseFocus()
    {
        return (this.windowEventType == WindowEventType.FOCUS_GAINED);
    }

    /**
     * @deprecated Replace with : {@link #getType()} == {@link WindowEventType#FOCUS_LOST}
     * @return
     */
    public boolean lostMouseFocus()
    {
        return (this.windowEventType == WindowEventType.FOCUS_LOST);
    }

    /**
     * @deprecated Replace with : {@link #getType()} == {@link WindowEventType#RESTORED}
     * @return
     */
    public boolean restored()
    {
        return (this.windowEventType == WindowEventType.RESTORED);
    }

    /**
     * @deprecated Replace with : {@link #getType()} == {@link WindowEventType#MINIMIZED}
     * @return
     */
    public boolean iconified()
    {
        return (this.windowEventType == WindowEventType.MINIMIZED);
    }
    
    public String toString()
    {
        return "WindowEvent " + windowEventType;
    }

}
