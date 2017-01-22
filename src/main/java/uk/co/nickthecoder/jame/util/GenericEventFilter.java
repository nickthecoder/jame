package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

/**
 * Filters events; an event can either be accepted of rejected.
 * Designed to help filter {@link KeyboardEvent}s, and is here are a generic super-interface to {@link KeyboardFilter}.
 * 
 * @param <T>
 *            The sub-class of Event which are to be filtered, e.g. {@link KeyboardEvent}.
 */
public interface GenericEventFilter<T extends Event> extends EventFilter
{
    public boolean accept(T event);
}
