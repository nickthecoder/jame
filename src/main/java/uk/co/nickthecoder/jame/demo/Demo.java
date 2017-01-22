package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.event.Event;

public interface Demo
{
    public void begin(DemoController controller) throws JameException;

    public void display(DemoController controller) throws JameException;

    public void showInfo(DemoController controller) throws JameException;

    public void event(DemoController controller, Event event) throws JameException;

    public void end(DemoController controller) throws JameException;
}
