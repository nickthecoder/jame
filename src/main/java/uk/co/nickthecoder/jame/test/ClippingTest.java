package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;

public class ClippingTest extends AbstractTest
{
    Surface surface;
    Texture texture;

    int width;
    int height;

    int x = 40;
    int y = 0;
    int dx = 1;
    int dy = 1;

    public ClippingTest(Surface surface)
    {
        this.surface = surface;
        this.width = surface.getWidth();
        this.height = surface.getHeight();
    }

    public ClippingTest(Surface surface, Texture texture)
    {
        this(surface);
        this.texture = texture;
    }

    @Override
    public void begin(TestController controller) throws JameException
    {

    }

    private void displayClipped(TestController controller, Rect clip)
    {

        if (texture == null) {
            // We don't have clipping on surfaces, so we need to do it manually.
            Rect srcRect = new Rect(0, 0, width, height);
            Rect dstRect = clip.intersection(new Rect(x, y, width, height));
            if ((dstRect.width > 0) && (dstRect.height > 0)) {

                int left = dstRect.x - x;
                srcRect.x += left;
                srcRect.width -= left;
                if (srcRect.width > dstRect.width) {
                    srcRect.width = dstRect.width;
                }

                int top = dstRect.y - y;
                srcRect.y += top;
                srcRect.height -= top;
                if (srcRect.height > dstRect.height) {
                    srcRect.height = dstRect.height;
                }

                surface.blit(srcRect, controller.alphaSurface, dstRect.x, dstRect.y, Surface.BlendMode.COMPOSITE);
            }

            // TODO Add rectangle outlines or line drawing to Surface.
            
            
        } else {
            // Much easier to clip using textures isn't it ;-)
            controller.renderer.setClip(clip);
            controller.renderer.copy(texture, x, y);

            controller.renderer.setDrawColor(RGBA.WHITE);
            controller.renderer.drawRect(clip);
        }

    }

    @Override
    public void display(TestController controller) throws JameException
    {
        if (texture == null) {
            controller.alphaSurface.fill(RGBA.BLACK);
        } else {
            controller.renderer.setClip(null);
            controller.renderer.setDrawColor(RGBA.BLACK);
            controller.renderer.clear();
        }

        displayClipped(controller, new Rect(250, 240, 100, 80));
        displayClipped(controller, new Rect(-50, 140, 200, 40));
        displayClipped(controller, new Rect(150, -40, 40, 200));

        x += dx;
        y += dy;

        if (x > width) {
            dx = -1;
        }
        if (x < 0) {
            dx = 1;
        }
        if (y > height) {
            dy = -1;
        }
        if (y < 0) {
            dy = 1;
        }
        if (texture == null) {
            controller.showSurface(true);
        } else {
            controller.renderer.present();
        }
    }

    public void end(TestController controller) throws JameException
    {
        controller.renderer.setClip(null);
        super.end(controller);
    }
}
