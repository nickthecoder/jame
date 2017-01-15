package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.event.KeyboardEvent;

public abstract class RectanglesTest extends AbstractTest
{
    List<BouncyRectangle> rectangles;

    Random random;

    boolean useAlpha = false;

    @Override
    public void begin(TestController controller) throws JameException
    {
        rectangles = new ArrayList<BouncyRectangle>(1000);
        random = new Random();
    }

    protected void keyboardEvent(TestController controller, KeyboardEvent ke) throws JameException
    {
        if (ke.isPressed() && (ke.symbol == '1')) {
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

    public class BouncyRectangle
    {
        Rect rect;
        RGBA color;
        int dx;
        int dy;

        public BouncyRectangle()
        {
            rect = new Rect(random.nextInt(640 + 200) - 100, random.nextInt(480 + 200) - 100,
                random.nextInt(200), random.nextInt(200));
            color = new RGBA(random.nextInt(256), random.nextInt(256), random.nextInt(256), random.nextInt(256));

            dx = random.nextInt(5) - 2;
            dy = random.nextInt(5) - 2;
        }

        static final int WIDTH = 640;
        static final int HEIGHT = 480;

        public void move()
        {
            rect.x += dx;
            rect.y += dy;

            if ((rect.x < 0) && (dx < 0)) {
                dx = -dx;
            }
            if ((rect.y < 0) && (dy < 0)) {
                dy = -dy;
            }

            if ((rect.x + rect.width > WIDTH) && (dx > 0)) {
                dx = -dx;
            }
            if ((rect.y + rect.height > HEIGHT) && (dy > 0)) {
                dy = -dy;
            }
        }
    }

}
