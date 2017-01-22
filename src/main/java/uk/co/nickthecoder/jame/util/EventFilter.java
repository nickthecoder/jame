package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.Event;

public interface EventFilter
{
    public boolean acceptEvent( Event event );
}
