package uk.co.nickthecoder.jame;

import uk.co.nickthecoder.jame.event.Event;

public class Events
{
    public static final int DEFAULT_REPEAT_DELAY = 500;
    public static final int DEFAULT_REPEAT_INTERVAL = 30;

    public static Event poll()
    {
        Event event = events_poll();
        return event;
    }

    private static native Event events_poll();

    /**
     * Enables or disables the processing of the 'c' field during key down
     * events. When enabled, KeyboardEvent.c is which character has just been
     * typed, which takes into account the modifiers. For example, if the shift
     * key is down, and the "a" key is pressed, then KeyboardEvent.c will
     * contain 'A'. With key translations disabled, KeyboardEvent.c is zero.
     */
    public static void enableKeyTranslation( boolean enable )
    {
        events_enableUnicode( enable );
    }

    private static native void events_enableUnicode( boolean enable );

    public static void keyboardRepeat( int delay, int interval )
    {
        events_keyboardRepeat( delay, interval );
    }

    private static native void events_keyboardRepeat( int delay, int interval );
}
