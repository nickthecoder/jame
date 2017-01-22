package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.BlendMode;
import uk.co.nickthecoder.jame.RGBA;


public class TextureFilledRectanglesDemo extends RectanglesDemo
{
    protected void drawRectangles(DemoController controller)
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
