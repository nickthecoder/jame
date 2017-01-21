package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.TrueTypeFont;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKeyFilter;
import uk.co.nickthecoder.jame.event.ScanCode;
import uk.co.nickthecoder.jame.event.TextEditingEvent;
import uk.co.nickthecoder.jame.event.TextInputEvent;

/**
 * Displays some editable text 
 * @author nick
 *
 */
public class TextEditingTest extends AbstractTest
{
    public Renderer renderer;

    public TrueTypeFont font;

    public String text;

    public int position;

    public Texture texture;

    public TextEditingTest(Renderer renderer, TrueTypeFont font)
    {
        this.renderer = renderer;
        this.font = font;
    }

    @Override
    public void begin(TestController controller) throws JameException
    {
        text = "Type!";
        position = text.length() -1;
        updateTexture();
    }

    @Override
    public void event(TestController controller, Event event) throws JameException
    {
        if (event instanceof TextEditingEvent) {
            System.out.println( event );
            TextEditingEvent tee = (TextEditingEvent) event;
            // TODO Add TextEditingEvent handler
            // I can't work out how to type foreign languages in Gnome3, and without that, I can't test this code.
            // When I change my input source to Japanese Kana, and then follow the instructions here:
            // http://www-archive.mozilla.org/projects/intl/input-method-spec.html
            // nothing happens.
            
        } else if (event instanceof TextInputEvent) {
            System.out.println( event );
            TextInputEvent tie = (TextInputEvent) event;
            // TODO Add TextInputEvent handler

        } else if (event instanceof KeyboardEvent) {

            KeyboardEvent ke = (KeyboardEvent) event;
            if (ke.pressed) {
                System.out.println( ke );

                if (ke.keyScanCode == ScanCode.LEFT) {
                    position--;
                    if (position < 0) {
                        position = 0;
                    }
                    updateTexture();
                    
                } else if (ke.keyScanCode == ScanCode.RIGHT) {
                    position++;
                    if (position > text.length()) {
                        position = text.length();
                    }
                    updateTexture();
                    
                } else if (ke.keyScanCode == ScanCode.BACKSPACE) {
                    if (position > 0) {
                        position--;
                        text = text.substring(0,position) + text.substring(position+1);
                        updateTexture();
                    }
                    
                } else if (ke.keyScanCode == ScanCode.DELETE) {
                    if (position < text.length()-1) {
                        text = text.substring(0,position) + text.substring(position+1);
                        updateTexture();
                    }
                    
                } else {
                    char c = (char) ke.symbol;
                    if ( ke.keySymbol.printable) {
                        if ( ModifierKeyFilter.SHIFT.accept(ke.modifiers)) {
                            c = Character.toUpperCase(c);
                        }
                        text = text.substring(0, position) + c + text.substring(position);
                        position ++;
                        updateTexture();
                    }
                }
                
            }
        }
        
        super.event(controller, event);
    }

    public boolean isPrintableChar(char c)
    {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (!Character.isISOControl(c)) &&
            c != java.awt.event.KeyEvent.CHAR_UNDEFINED &&
            block != null &&
            block != Character.UnicodeBlock.SPECIALS;
    }

    public void updateTexture()
    {
        Surface surface = font.renderBlended(text.substring(0,position) + "|" + text.substring(position), RGBA.WHITE);
        if (texture != null) {
            texture.destroy();
        }
        texture = new Texture(renderer, surface);
        surface.free();
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        if (texture != null) {
            controller.renderer.copy(texture, 50, 50);
        }

        controller.renderer.present();
    }

}
