package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.MouseButtonEvent;
import uk.co.nickthecoder.jame.event.MouseMotionEvent;

/**
 * Draws sprites, and allows then to be dragged.
 * 
 * Tests {@link MouseButtonEvent} and {@link MouseMotionEvent}, as well as {@link Texture#getPixel(Renderer, int, int)}.
 */
public class MouseTest extends AbstractTest
{
    public static final Random random = new Random();

    public Renderer renderer;
    public SizedTexture[] textures;
    public List<Item> items;
    public int count;

    public MouseTest(int count, SizedTexture... textures)
    {
        this.count = count;
        this.textures = textures;
    }

    @Override
    public void begin(TestController controller) throws JameException
    {
        renderer = controller.renderer;
        items = new ArrayList<Item>(count);
        for (int i = 0; i < count; i++) {
            items.add(new Item(textures[random.nextInt(textures.length)]));
        }

    }

    @Override
    public void display(TestController controller) throws JameException
    {
        controller.renderer.setClip(null);
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        for (Item item : items) {
            item.draw(controller.renderer);
        }

        controller.renderer.present();
    }

    public Item dragging = null;
    public int dragX;
    public int dragY;

    @Override
    public void event(TestController controller, Event event) throws JameException
    {

        if (event instanceof MouseButtonEvent) {
            MouseButtonEvent mbe = (MouseButtonEvent) event;

            if ((dragging == null) && (mbe.isPressed()) && (mbe.button == 1)) {
                dragX = mbe.x;
                dragY = mbe.y;
                for (Item item : items) {
                    if (item.contains(mbe.x, mbe.y)) {
                        dragging = item;
                    }
                    // Note, we don't exit the loop, because we want to find the TOP most item.
                }
            }

            if ((dragging != null) && mbe.isReleased()) {
                dragging = null;
            }

        } else if (event instanceof MouseMotionEvent) {
            if (dragging != null) {
                MouseMotionEvent mme = (MouseMotionEvent) event;
                int dx = mme.x - dragX;
                int dy = mme.y - dragY;
                dragX = mme.x;
                dragY = mme.y;
                dragging.moveBy(dx, dy);
            }

        } else {
            super.event(controller, event);
        }
    }

    public class Item
    {
        public Texture texture;
        public Rect rect;

        public Item(SizedTexture texture)
        {
            this.texture = texture;
            rect = new Rect(random.nextInt(400), random.nextInt(300), texture.getWidth(), texture.getHeight());
        }

        public void draw(Renderer renderer)
        {
            renderer.copy(texture, rect.x, rect.y );
        }

        public boolean contains(int x, int y)
        {
            if (!rect.contains(x, y)) {
                return false;
            }

            // False if it is a transparent pixel
            RGBA color = texture.getPixel(renderer, x - rect.x, y - rect.y);
            return color.a > 5;
        }

        public void moveBy(int dx, int dy)
        {
            rect.x += dx;
            rect.y += dy;
        }
    }
}
