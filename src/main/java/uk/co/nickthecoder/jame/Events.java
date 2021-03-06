/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

public class Events
{
    /**
     * The number of milliseconds needed to hold a key down before additional fake onKeyDown events
     * are generated.
     */
    public static final int DEFAULT_REPEAT_DELAY = 500;

    /**
     * The time between fake onKeyDown events when a key is held down.
     */
    public static final int DEFAULT_REPEAT_INTERVAL = 30;

    /**
     * Within your game loop, call poll repeatedly until it returns null. See the subclasses of
     * {@link Event} for all of the possible events that are generated.
     * 
     * @return <code>null</code> if there are no more events.
     */
    public static Event poll()
    {
        Event event = events_poll();
        return event;
    }

    private static native Event events_poll();

    /**
     * Enables or disables the translation from low-level keyboard symbols and the more meaningful
     * character values. When enabled, keyboard events will set the {@link KeyboardEvent#c
     * KeyboardEvent.c} attribute to the character that has just been typed, taking into account the
     * modifiers. For example, if the shift key is down, and the "a" key is pressed, then
     * KeyboardEvent.c will contain 'A'. With key translations disabled, KeyboardEvent.c is not set.
     */
    public static void enableKeyTranslation( boolean enable )
    {
        events_enableUnicode(enable);
    }

    private static native void events_enableUnicode( boolean enable );

    /**
     * When typing, if you hold down a key long enough, you will get it to auto-repeat, that is, it
     * will fire multiple KeyboardEvents.
     * 
     * @param delay
     *        The time in milliseconds you need to hold the key down for, in order to start generating
     *        these extra keyboard events.
     * @param interval
     *        This is the time in milliseconds between each of the additional KeyboardEvents.
     */
    public static void keyboardRepeat( int delay, int interval )
    {
        events_keyboardRepeat(delay, interval);
    }

    private static native void events_keyboardRepeat( int delay, int interval );
}
