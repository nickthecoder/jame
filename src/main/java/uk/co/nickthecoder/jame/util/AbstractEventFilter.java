package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.Event;

public abstract class AbstractEventFilter<T extends Event> implements EventFilter<T>
{

    @SuppressWarnings("unchecked")
    public boolean acceptEvent(Event event)
    {
        try {
            return accept((T) event);
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public abstract boolean accept(T event);

}
