package uk.co.nickthecoder.jame.event;

import uk.co.nickthecoder.jame.Window;

public abstract class EventForWindow extends Event
{
    public int windowID;

    public Window window;
    
    @Override
    public void postConstruct()
    {
        window = getWindow();
        if ( window != null ) {
            window.onEvent( this );
        }
    }
    
    public Window getWindow()
    {
        return Window.getWindowById(windowID);
    }

}
