package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Surface;

public class ClippingDemo extends AbstractSpriteDemo
{

    public ClippingDemo(int count, Surface... surfaces)
    {
        super(count, surfaces);
    }

    public ClippingDemo(int count, SizedTexture... textures)
    {
        super(count, textures);
    }

    private void displayClippedSprites(Rect clip)
    {

        if (textures == null) {

            for (Sprite sprite : sprites) {

                // We don't have clipping on surfaces, so we need to do it manually.
                Rect srcRect = new Rect(0, 0, sprite.getWidth(), sprite.getHeight());
                Rect dstRect = clip.intersection(new Rect(sprite.x, sprite.y, sprite.getWidth(), sprite.getHeight()));
                if ((dstRect.width > 0) && (dstRect.height > 0)) {

                    int left = dstRect.x - sprite.x;
                    srcRect.x += left;
                    srcRect.width -= left;
                    if (srcRect.width > dstRect.width) {
                        srcRect.width = dstRect.width;
                    }

                    int top = dstRect.y - sprite.y;
                    srcRect.y += top;
                    srcRect.height -= top;
                    if (srcRect.height > dstRect.height) {
                        srcRect.height = dstRect.height;
                    }

                    sprite.surface.blit(srcRect, controller.alphaSurface, dstRect.x, dstRect.y, Surface.BlendMode.COMPOSITE);
                }
                // TODO Add rectangle outlines or line drawing to Surface.

            }

        } else {
            // Much easier to clip using textures isn't it ;-)
            controller.renderer.setClip(clip);
            for (Sprite sprite : sprites) {
                sprite.draw(controller.renderer);
            }
            controller.renderer.setClip(null);

            controller.renderer.setDrawColor(RGBA.WHITE);
            controller.renderer.drawRect(clip);
        }

    }

    @Override
    public void displaySprites() throws JameException
    {
        displayClippedSprites(new Rect(250, 240, 100, 80));
        displayClippedSprites(new Rect(-50, 140, 200, 40));
        displayClippedSprites(new Rect(150, -40, 40, 200));
    }

    public void end(DemoController controller) throws JameException
    {
        controller.renderer.setClip(null);
        super.end(controller);
    }
}
