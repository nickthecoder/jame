package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Surface;

/**
 * Uses {@link Surface#fill(uk.co.nickthecoder.jame.Rect, RGBA)} to draw rectangles onto a surface.
 * The surface is then drawn to the window. This is as close to SDL version 1 as I can get.
 * <p>
 * Key '1' toggles between a surface with and without an alpha channel. However, Surface.fill doesn't do any blending,
 * so it won't look the same as {@link TextureFilledRectanglesDemo}.
 */
public class SurfaceFilledRectanglesDemo extends RectanglesDemo
{

    protected void drawRectangles(DemoController controller)
    {
        if (useAlpha) {
            controller.alphaSurface.fill(RGBA.TRANSPARENT);
        } else {
            controller.surface.fill(RGBA.BLACK);
        }

        for (BouncyRectangle br : rectangles) {
            if (useAlpha) {
                controller.alphaSurface.fill(br.rect, br.color);
            } else {
                controller.surface.fill(br.rect, br.color);
            }
            br.move();
        }

        controller.showSurface(useAlpha);
    }

}
