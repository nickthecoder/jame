/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0 which accompanies this
 * distribution, and is available at http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame;

import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.EventForWindow;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKey;
import uk.co.nickthecoder.jame.event.QuitEvent;
import uk.co.nickthecoder.jame.event.StopPropagation;

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
                } finally {
                    if (event instanceof KeyboardEvent) {
                        rememberModifiers((KeyboardEvent) event);
                    }
                }
            }
            return event;
        }
    }

    /**
     * Used by {@link #rememberModifiers(KeyboardEvent)}.
     */
    private static int modifiers;

    /**
     * Returns which modifier keys are down (or locked in the case of Caps Lock and Number Lock).
     * 
     * @return A bitwise OR of all modifiers that are currently pressed/locked.
     */
    public static int getKeyboardModifiers()
    {
        return modifiers;
    }

    /**
     * Modifier keys (shift, ctrl, alt) are sent as part of KeyboardEvents, however, they are NOT sent with
     * MouseButtonEvents. This is annoying because, when creating a GUI, it is often useful to have the mouse button
     * behave differently depending on the state of the modifier keys. Therefore Jame intercepts all KeyboardEvents,
     * remembers which modifier keys are down, and includes them in future MouseButtonEvents.
     * 
     * @param ke
     */
    private static void rememberModifiers(KeyboardEvent ke)
    {
        modifiers = ke.modifiers;
        ModifierKey mk = ke.scanCode.modifierKey; 
        if ( mk != null ) {
            
            if (mk.isLock) {
                // Toggle the value on when it is pressed
                if (ke.pressed) {
                    modifiers ^= mk.code;
                }
            } else {
                if (ke.pressed) {
                    // Add the modifier
                    modifiers |= mk.code;
                } else {
                    // Remove the modifier
                    modifiers &= ~mk.code;
                }
            }
        }
    }

    private static native Event native_poll();
    
    public static native void startTextInput();

    public static native void stopTextInput();

}
