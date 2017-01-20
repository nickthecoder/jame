package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.Keys;
import uk.co.nickthecoder.jame.event.ModifierKey;
import uk.co.nickthecoder.jame.event.ModifierKeyFilter;
import uk.co.nickthecoder.jame.util.KeyboardFilter;

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

        add("Press A (any mods)", new KeyboardFilter().pressed().symbol(Keys.a));
        add("Press A (no mods)", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.NONE).symbol(Keys.a));

        add("Press shift+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT).symbol(Keys.a));
        add("Press ctrl+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.CTRL).symbol(Keys.a));
        add("Press alt+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.ALT).symbol(Keys.a));
        
        add("Press left shift+A", new KeyboardFilter().pressed().modifier(ModifierKey.LSHIFT).symbol(Keys.a));
        add("Press right shift+A", new KeyboardFilter().pressed().modifier(ModifierKey.RSHIFT).symbol(Keys.a));
        
        add("Press left alt+A", new KeyboardFilter().pressed().modifier(ModifierKey.LALT).symbol(Keys.a));
        add("Press right alt+A", new KeyboardFilter().pressed().modifier(ModifierKey.RALT).symbol(Keys.a));

        add("Press left ctrl+A", new KeyboardFilter().pressed().modifier(ModifierKey.LCTRL).symbol(Keys.a));
        add("Press right ctrl+A", new KeyboardFilter().pressed().modifier(ModifierKey.RCTRL).symbol(Keys.a));
        
        add("Press ctrl+shift+A", new KeyboardFilter().pressed().modifiers(ModifierKeyFilter.SHIFT.and(ModifierKeyFilter.CTRL)).symbol(Keys.a));

        nextColumn();
        add("Press A (any mods - include repeats)", new KeyboardFilter().pressed().includeRepeats().symbol(Keys.a));
        add("Release A (any mods)", new KeyboardFilter().released().symbol(Keys.a));
        add("Release A (no mods)", new KeyboardFilter().released().modifiers(ModifierKeyFilter.NONE).symbol(Keys.a));
        add("Release shift+A", new KeyboardFilter().released().modifiers(ModifierKeyFilter.SHIFT).symbol(Keys.a));
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
