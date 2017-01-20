package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.Symbol;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

public abstract class AbstractTest implements Test
{


    @Override
    public void event(TestController controller, Event event) throws JameException
    {
        if (event instanceof KeyboardEvent ) {
            keyboardEvent( controller, (KeyboardEvent) event );
        }
    }
    
    public void showInfo(TestController controller) throws JameException
    {
        controller.showInfo(controller);
    }

    protected void keyboardEvent(TestController controller, KeyboardEvent ke) throws JameException
    {
        if ( (ke.isPressed()) && (ke.getSymbol() == Symbol.ESCAPE ) ) {
            this.end( controller );
        }
    }
    
    @Override
    public void end(TestController controller) throws JameException
    {
        controller.ended(this);
    }

}
