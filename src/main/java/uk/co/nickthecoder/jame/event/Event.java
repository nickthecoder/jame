/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

public class Event
{
    public static final int STATE_RELEASED = 0;

    public static final int STATE_PRESSED = 1;

    public int timestamp;

    /**
     * Used internally by Jame.
     * Called after the JNI code has created the object, to complete the construction of this instance.
     * This method was first created for {@link WindowEvent}s to populate their windowEventType.
     */
    public void postConstruct()
    {
        // Does nothing.
    }

    /**
     * In a complex system where many objects are offered the chance to handle an event,
     * it is handy to have some way to state that the event has been handled, and should
     * not be propagated to any other objects.
     * 
     * This does so by throwing a StopPropagation.
     * It is up to your own event loop to catch this exception, and do the appropriate thing.
     */
    public void stopPropagation()
    {
        throw new StopPropagation();
    }
}
