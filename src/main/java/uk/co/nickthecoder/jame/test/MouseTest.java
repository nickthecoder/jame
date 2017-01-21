package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Texture;
import uk.co.nickthecoder.jame.event.Event;
import uk.co.nickthecoder.jame.event.MouseButtonEvent;
import uk.co.nickthecoder.jame.event.MouseMotionEvent;
import uk.co.nickthecoder.jame.event.MouseWheelEvent;

/**
 * Draws sprites, and allows then to be dragged with the mouse. Use the scroll wheel(s) to move all the sprites.
 * Uses bounding rectangle and then pixel based detection to know when the mouse is over the visible part of a sprite.
 * 
 * Tests {@link MouseButtonEvent}, {@link MouseMotionEvent} and {@link MouseWheelEvent}, as well as
 * {@link Texture#getPixel(Renderer, int, int)}.
 */
public class MouseTest extends AbstractSpriteTest
{

    public MouseTest(int count, SizedTexture... textures)
    {
        super(count, textures);
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        controller.renderer.setClip(null);
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        for (Sprite sprite : sprites) {
            sprite.draw(controller.renderer);
        }

        controller.renderer.present();
    }

    public Sprite dragging = null;
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
                for (Sprite sprite : sprites) {
                    if (sprite.contains(mbe.x, mbe.y)) {
                        dragging = sprite;
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

        } else if (event instanceof MouseWheelEvent) {
            System.out.println(event);
            MouseWheelEvent mwe = (MouseWheelEvent) event;

            for (Sprite sprite : sprites) {
                sprite.moveBy(mwe.x, mwe.y);
            }

        } else {
            super.event(controller, event);
        }
    }

}
