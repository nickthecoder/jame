package uk.co.nickthecoder.jame.util;

import uk.co.nickthecoder.jame.event.Event;

/**
 * Accepts an event if all of the child EventFilters accept the event.
 */
public class AndEventFilter implements EventFilter
{
    private EventFilter filters[];

    public AndEventFilter(EventFilter... filters)
    {
        this.filters = filters;
    }

    @Override
    public boolean acceptEvent(Event event)
    {
        for (EventFilter filter : filters) {
            if (!filter.acceptEvent(event)) {
                return false;
            }
        }
        return true;
    }
}
