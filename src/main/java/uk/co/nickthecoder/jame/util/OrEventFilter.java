package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.Event;

/**
 * Accepts an event if any of its child EventFilters accepts it.
 */
public class OrEventFilter implements EventFilter
{
    private EventFilter filters[];

    public OrEventFilter(EventFilter... filters)
    {
        this.filters = filters;
    }

    @Override
    public boolean acceptEvent(Event event)
    {
        for (EventFilter filter : filters) {
            if (filter.acceptEvent(event)) {
                return true;
            }
        }
        return false;
    }

}
