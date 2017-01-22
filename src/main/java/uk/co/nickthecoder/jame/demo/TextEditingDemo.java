package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.Events;
import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.TrueTypeFont;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ScanCode;
import uk.co.nickthecoder.jame.event.TextEditingEvent;
import uk.co.nickthecoder.jame.event.TextInputEvent;
import uk.co.nickthecoder.jame.util.ModifierKeyFilter;

/**
 * Displays a line of editable text.
 * You can use this as the basis for a text input field in your game.
 * Move the caret with the arrow keys, HOME and END.
 * Make a selection by holding down SHIFT while using the arrow keys, HOME or END.
 * <p>
 * Currently, the mouse cannot be used to move the caret, or make selections. Also, it would be nice for SHIFT+CTRL+
 * arrow keys to change the selection a word at a time. Copy and paste are not supported.
 * </p>
 */
public class TextEditingDemo extends AbstractDemo
{
    public static final RGBA CARET_COLOR = new RGBA(255, 50, 50);

    public static final RGBA SELECTION_COLOR = new RGBA(90, 90, 200);

    public Renderer renderer;

    public TrueTypeFont font;

    public String text;

    public int position;

    public Texture texture;

    public int flashCounter = 0;

    public int selectStart;

    public int selectEnd;

    public TextEditingDemo(Renderer renderer, TrueTypeFont font)
    {
        this.renderer = renderer;
        this.font = font;
    }

    @Override
    public void begin(DemoController controller) throws JameException
    {
        text = "Type!";
        position = text.length() - 1;
        selectStart = -1;
        selectEnd = -1;
        updateTexture();

        // Call this whenever the focus is given to a text input field.
        Events.stopTextInput();
        Events.startTextInput();
    }

    @Override
    public void end(DemoController controller) throws JameException
    {
        // Your code should call this when focus is away from the text input field.
        Events.stopTextInput();
        super.end(controller);
    }

    @Override
    public void event(DemoController controller, Event event) throws JameException
    {
        if (event instanceof TextEditingEvent) {
            // TODO Add TextEditingEvent handler
            // I can't work out how to type foreign languages in Gnome3, and without that, I can't test this code.
            // When I change my input source to Japanese Kana, and then follow the instructions here:
            // http://www-archive.mozilla.org/projects/intl/input-method-spec.html
            // nothing happens.

            TextEditingEvent tee = (TextEditingEvent) event;
            System.out.println("*** " + tee);

        } else if (event instanceof TextInputEvent) {

            TextInputEvent tie = (TextInputEvent) event;
            System.out.println(tie);

            if (selectStart != selectEnd) {
                deleteSelection();
            }

            text = text.substring(0, position) + tie.text + text.substring(position);
            position += tie.text.length();
            clearSelection();
            updateTexture();

        } else if (event instanceof KeyboardEvent) {

            KeyboardEvent ke = (KeyboardEvent) event;
            if (ke.pressed) {
                System.out.println(ke);

                if (ke.keyScanCode == ScanCode.LEFT) {
                    if (ModifierKeyFilter.SHIFT.accept(ke)) {
                        changeSelection(-1);
                    } else {
                        clearSelection();
                    }
                    position--;
                    if (position < 0) {
                        position = 0;
                    }
                    updateTexture();

                } else if (ke.keyScanCode == ScanCode.RIGHT) {
                    if (ModifierKeyFilter.SHIFT.accept(ke)) {
                        changeSelection(1);
                    } else {
                        clearSelection();
                    }
                    position++;
                    if (position > text.length()) {
                        position = text.length();
                    }
                    updateTexture();

                } else if (ke.keyScanCode == ScanCode.HOME) {
                    if (ModifierKeyFilter.SHIFT.accept(ke)) {
                        changeSelection(-text.length());
                    } else {
                        clearSelection();
                    }
                    position = 0;
                    updateTexture();

                } else if (ke.keyScanCode == ScanCode.END) {
                    if (ModifierKeyFilter.SHIFT.accept(ke)) {
                        changeSelection(text.length());
                    } else {
                        clearSelection();
                    }
                    position = text.length();
                    updateTexture();

                } else if (ke.keyScanCode == ScanCode.BACKSPACE) {
                    if (selectStart != selectEnd) {
                        deleteSelection();
                    } else {
                        if (position > 0) {
                            position--;
                            text = text.substring(0, position) + text.substring(position + 1);
                            updateTexture();
                        }
                    }

                } else if (ke.keyScanCode == ScanCode.DELETE) {
                    if (selectStart != selectEnd) {
                        deleteSelection();
                    } else {
                        if (position < text.length() - 1) {
                            text = text.substring(0, position) + text.substring(position + 1);
                            updateTexture();
                        }
                    }

                }
            }
        }

        super.event(controller, event);
    }

    public void changeSelection(int delta)
    {
        if (selectStart == -1) {
            selectStart = position;
            selectEnd = position;
        }
        selectEnd += delta;
        if (selectEnd > text.length()) {
            selectEnd = text.length();
        }
        if (selectEnd < 0) {
            selectEnd = 0;
        }
    }

    public void deleteSelection()
    {
        int start = selectStart < selectEnd ? selectStart : selectEnd;
        int end = selectStart > selectEnd ? selectStart : selectEnd;

        text = text.substring(0, start) + text.substring(end);
        position = start;

        clearSelection();
        updateTexture();
    }

    public void clearSelection()
    {
        selectStart = -1;
        selectEnd = -1;
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
        Surface surface = font.renderBlended(text, RGBA.WHITE);
        if (texture != null) {
            texture.destroy();
        }

        texture = new Texture(renderer, surface);
        surface.free();
    }

    @Override
    public void display(DemoController controller) throws JameException
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        int x = 50;
        int y = 50;

        if (texture != null) {

            int caretX = font.sizeText(text.substring(0, position)) - 1;
            int caretHeight = font.getLineHeight();

            // Selection background
            if (selectStart >= 0) {
                int startX = selectStart == 0 ? 0 : font.sizeText(text.substring(0, selectStart));
                int endX = selectEnd == 0 ? 0 : font.sizeText(text.substring(0, selectEnd));
                renderer.setDrawColor(SELECTION_COLOR);
                renderer.fillRect(new Rect(x + startX, y, endX - startX, caretHeight));
            }

            // The text
            controller.renderer.copy(texture, x, y);

            // The caret
            flashCounter++;
            if (flashCounter > 20) {
                renderer.setDrawColor(CARET_COLOR);
                renderer.drawLine(x + caretX, y, x + caretX, y + caretHeight);
                if (flashCounter > 50) {
                    flashCounter = 0;
                }
            }
        }

        controller.renderer.present();
    }

}
