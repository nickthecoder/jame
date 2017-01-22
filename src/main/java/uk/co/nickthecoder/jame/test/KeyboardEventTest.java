package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.KeyboardEvent;
import uk.co.nickthecoder.jame.event.ModifierKey;
import uk.co.nickthecoder.jame.event.ScanCode;
import uk.co.nickthecoder.jame.event.Symbol;
import uk.co.nickthecoder.jame.util.KeyboardFilter;
import uk.co.nickthecoder.jame.util.ModifierKeyFilter;

public class KeyboardEventTest extends AbstractTest
{
    TestController controller;

    public List<Indicator> indicators;

    int x = 150;
    int y = 30;

    public KeyboardEventTest(TestController controller)
    {
        this.controller = controller;

        indicators = new ArrayList<Indicator>();

        add("Press A (any mods)", new KeyboardFilter().pressed().symbol(Symbol.a));
        add("Press A (no mods)", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.NONE).symbol(Symbol.a));

        add("Press shift+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT).symbol(Symbol.a));
        add("Press ctrl+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.CTRL).symbol(Symbol.a));
        add("Press alt+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.ALT).symbol(Symbol.a));
        
        add("Press left shift+A", new KeyboardFilter().pressed().modifier(ModifierKey.LSHIFT).symbol(Symbol.a));
        add("Press right shift+A", new KeyboardFilter().pressed().modifier(ModifierKey.RSHIFT).symbol(Symbol.a));
        
        add("Press left alt+A", new KeyboardFilter().pressed().modifier(ModifierKey.LALT).symbol(Symbol.a));
        add("Press right alt+A", new KeyboardFilter().pressed().modifier(ModifierKey.RALT).symbol(Symbol.a));

        add("Press left ctrl+A", new KeyboardFilter().pressed().modifier(ModifierKey.LCTRL).symbol(Symbol.a));
        add("Press right ctrl+A", new KeyboardFilter().pressed().modifier(ModifierKey.RCTRL).symbol(Symbol.a));
        
        add("Press ctrl+shift+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT.and(ModifierKeyFilter.CTRL)).symbol(Symbol.a));

        add("Press shift+A without Num Lock", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT.forbid(ModifierKey.NUM_LOCK)).symbol(Symbol.a));
        add("Press shift+A without Caps Lock", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT.forbid(ModifierKey.CAPS_LOCK)).symbol(Symbol.a));

        nextColumn();
        add("Press A (any mods - include repeats)", new KeyboardFilter().pressed().includeRepeats().symbol(Symbol.a));
        add("Release A (any mods)", new KeyboardFilter().released().symbol(Symbol.a));
        add("Release A (no mods)", new KeyboardFilter().released().modifiers(ModifierKeyFilter.NONE).symbol(Symbol.a));
        add("Release shift+A", new KeyboardFilter().released().modifiers(ModifierKeyFilter.SHIFT).symbol(Symbol.a));
        
        // Note, these are different physical keys on my UK keyboard.
        add("Backslash Scan Code", new KeyboardFilter().pressed().scanCode(ScanCode.BACKSLASH));
        add("Backslash Symbol", new KeyboardFilter().pressed().symbol(Symbol.BACKSLASH));

        // These are the other versions of the two items above.
        add("Non US Backslash Scan Code", new KeyboardFilter().pressed().scanCode(ScanCode.NONUSBACKSLASH));
        add("Hash Symbol", new KeyboardFilter().pressed().symbol(Symbol.HASH));
    }

    private void nextColumn()
    {
        this.x += 320;
        this.y = 30;        
    }
    private void add(String label, KeyboardFilter filter)
    {
        Indicator indicator = new Indicator(label, filter, this.x, this.y);
        this.y += 30;
        if (this.y >= 450) {
            nextColumn();
        }
        this.indicators.add(indicator);

    }

    @Override
    public void begin(TestController controller)
    {
    }

    public void event(TestController controller, Event event) throws JameException
    {
        if ( event instanceof KeyboardEvent) {
            System.out.println( event );
        }
        
        for (Indicator indicator : indicators) {
            indicator.event(event);
        }
        super.event(controller, event);
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();
        for (Indicator indicator : indicators) {
            indicator.display();
        }
        controller.renderer.present();
    }

    public class Indicator
    {
        private static final double MAX_SCALE = 1.6;
        
        SizedTexture texture;
        int x;
        int y;
        boolean activated;
        double scale = 1;
        KeyboardFilter filter;
        Rect srcRect;

        public Indicator(String label, KeyboardFilter filter, int x, int y)
        {
            this.filter = filter;
            this.x = x;
            this.y = y;

            Surface surface = controller.smallFont.renderBlended(label, RGBA.WHITE);
            this.texture = new SizedTexture(controller.renderer, surface);

            srcRect = new Rect(0, 0, texture.width, texture.height);
        }

        public void event(Event event)
        {
            if (filter.acceptEvent(event)) {
                this.activated = true;
                return;
            }
        }

        public void display()
        {
            this.scale += activated ? 0.1 : -0.1;

            if (this.scale < 1) {
                this.scale = 1;
            }
            if (this.scale > MAX_SCALE) {
                this.scale = MAX_SCALE;
                this.activated = false; 
            }

            int width = (int) (texture.width * this.scale);
            int height = (int) (texture.height * this.scale);
            Rect dstRect = new Rect(x - width / 2, y - height / 2, width, height);
            controller.renderer.copy(texture, srcRect, dstRect, 0, width / 2, height / 2, false, false);
        }
    }
}
