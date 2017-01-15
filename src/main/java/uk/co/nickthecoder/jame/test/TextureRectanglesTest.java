package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.BlendMode;
import uk.co.nickthecoder.jame.RGBA;

public class TextureRectanglesTest extends RectanglesTest
{

    protected void drawRectangles(TestController controller)
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        controller.renderer.setDrawBlendMode(useAlpha ? BlendMode.BLEND : BlendMode.NONE);

        for (BouncyRectangle br : rectangles) {
            controller.renderer.setDrawColor(br.color);
            controller.renderer.drawRect(br.rect);
            br.move();
        }
        
        controller.renderer.present();
    }
}
