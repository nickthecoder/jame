package uk.co.nickthecoder.jame.test;

import java.util.Random;

public abstract class Bouncy
{
    static Random random = new Random();

    static int windowWidth = 640;
    static int windowHeight = 480;

    int x;
    int y;

    int dx;
    int dy;

    public Bouncy()
    {
        x = random.nextInt(640 + 200) - 100;
        y = random.nextInt(480 + 200) - 100;

        dx = random.nextInt(5) - 2;
        dy = random.nextInt(5) - 2;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    public void move()
    {
        x += dx;
        y += dy;

        if ((x < getWidth()/2) && (dx < 0)) {
            dx = -dx;
            bounced();
        }
        if ((y < getWidth()/2) && (dy < 0)) {
            dy = -dy;
            bounced();
        }

        if ((x + getWidth()/2 > windowWidth) && (dx > 0)) {
            dx = -dx;
            bounced();
        }
        if ((y + getHeight()/2 > windowHeight) && (dy > 0)) {
            dy = -dy;
            bounced();
        }
    }

    public void bounced()
    {
        // Do nothing
    }
    
}
