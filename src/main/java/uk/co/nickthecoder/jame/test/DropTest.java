package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.TrueTypeFont;
import uk.co.nickthecoder.jame.event.DropFileEvent;
import uk.co.nickthecoder.jame.event.Event;

/**
 * Uses {@link DropFileEvent} to show the filename of files dropped onto the window.
 */
public class DropTest extends AbstractTest
{
    public TrueTypeFont font;

    public Texture texture;

    public DropTest( TrueTypeFont font )
    {
        this.texture = null;
        this.font = font;
    }
    
    @Override
    public void begin(TestController controller) throws JameException
    {
    }

    public void event(TestController controller, Event event) throws JameException
    {
        if ( event instanceof DropFileEvent ) {
            System.out.println( event );
            DropFileEvent de = (DropFileEvent) event;
            Surface surface = font.renderBlended(de.filename, RGBA.WHITE);
            texture = new Texture( controller.renderer, surface);
            event.stopPropagation();
        }
        super.event(controller, event);
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        if ( texture != null ) {
            controller.renderer.copy(texture, 50, 50);
        }
        
        controller.renderer.present();
    }

}
