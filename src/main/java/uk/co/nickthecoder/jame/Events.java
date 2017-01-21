/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.QuitEvent;
import uk.co.nickthecoder.jame.event.StopPropagation;
import uk.co.nickthecoder.jame.event.EventForWindow;

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
     * Within your game loop, call poll repeatedly until it returns null. See the subclasses of {@link Event} for all of
     * the possible events that are generated.
     * <p>
     * All events other than {@link QuitEvent}, will be passed to {@link Window#onEvent(EventForWindow)}), and if the
     * event is handled (by calling {@link Event#stopPropagation()}), then it will NOT be returned from this poll
     * method.
     * </p>
     * 
     * @return <code>null</code> if there are no more events.
     */
    public static Event poll()
    {
        while (true) {
            Event event = native_poll();
            if (event != null) {
                try {
                    event.postConstruct();
                } catch (StopPropagation sp) {
                    // The event has already been handled, so prevent it from being handled again, by not returning it.
                    continue;
                }
            }
            return event;
        }
    }

    private static native Event native_poll();

    /**
     * When typing, if you hold down a key long enough, you will get it to auto-repeat, that is, it
     * will fire multiple KeyboardEvents.
     * 
     * @param delay
     *            The time in milliseconds you need to hold the key down for, in order to start generating
     *            these extra keyboard events.
     * @param interval
     *            This is the time in milliseconds between each of the additional KeyboardEvents.
     */
    public static void keyboardRepeat(int delay, int interval)
    {
        native_keyboardRepeat(delay, interval);
    }

    private static native void native_keyboardRepeat(int delay, int interval);
}
