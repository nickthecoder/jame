package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.Symbol;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

public abstract class AbstractDemo implements Demo
{


    @Override
    public void event(DemoController controller, Event event) throws JameException
    {
        if (event instanceof KeyboardEvent ) {
            keyboardEvent( controller, (KeyboardEvent) event );
        }
    }
    
    public void showInfo(DemoController controller) throws JameException
    {
        controller.showInfo(controller);
    }

    protected void keyboardEvent(DemoController controller, KeyboardEvent ke) throws JameException
    {
        if ( (ke.pressed) && (ke.symbol == Symbol.ESCAPE ) ) {
            this.end( controller );
        }
    }
    
    @Override
    public void end(DemoController controller) throws JameException
    {
        controller.ended(this);
    }

}
