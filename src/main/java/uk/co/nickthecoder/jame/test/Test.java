package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.event.Event;

public interface Test
{
    public void begin(TestController controller) throws JameException;

    public void display(TestController controller) throws JameException;

    public void showInfo(TestController controller) throws JameException;

    public void event(TestController controller, Event event) throws JameException;

    public void end(TestController controller) throws JameException;
}
