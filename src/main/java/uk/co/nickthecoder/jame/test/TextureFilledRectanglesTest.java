package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.BlendMode;
import uk.co.nickthecoder.jame.RGBA;


public class TextureFilledRectanglesTest extends RectanglesTest
{
    protected void drawRectangles(TestController controller)
    {
        controller.renderer.setDrawColor(RGBA.BLACK);
        controller.renderer.clear();

        controller.renderer.setDrawBlendMode(useAlpha ? BlendMode.BLEND : BlendMode.NONE);

        for (BouncyRectangle br : rectangles) {
            controller.renderer.setDrawColor(br.color);
            controller.renderer.fillRect(br.rect);
            br.move();
        }
        
        controller.renderer.present();
    }
    
}
