package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

public abstract class RectanglesTest extends AbstractTest
{
    List<BouncyRectangle> rectangles;

    boolean useAlpha = false;

    @Override
    public void begin(TestController controller) throws JameException
    {
        rectangles = new ArrayList<BouncyRectangle>(1000);
    }

    protected void keyboardEvent(TestController controller, KeyboardEvent ke) throws JameException
    {
        if (ke.pressed && (ke.symbol == '1')) {
            useAlpha = !useAlpha;
            System.out.println("Using alpha : " + useAlpha);
            return;
        }
        super.keyboardEvent(controller, ke);
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        if (rectangles.size() < 10000) {
            rectangles.add(new BouncyRectangle());
        }

        drawRectangles(controller);
    }

    protected abstract void drawRectangles(TestController controller);

    public void showInfo(TestController controller) throws JameException
    {
        super.showInfo(controller);
        System.out.println("Rectangle Count : " + this.rectangles.size());
    }

    public void end(TestController controller) throws JameException
    {
        super.end(controller);
        this.rectangles.clear();
    }

    public class BouncyRectangle extends Bouncy
    {
        Rect rect;
        RGBA color;

        public BouncyRectangle()
        {
            super();
            color = new RGBA(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));
            rect = new Rect(0, 0, random.nextInt(200), random.nextInt(200));
            move();
        }

        public void move()
        {
            super.move();
            rect.x = x;
            rect.y = y;
        }

        @Override
        protected int getWidth()
        {
            return rect.width;
        }

        @Override
        protected int getHeight()
        {
            return rect.height;
        }

    }

}
