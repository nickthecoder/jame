package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;


public class Sprite extends Bouncy
{
    public TestController controller;
    public Texture texture;
    public Surface surface;
    public Rect rect;

    public Sprite(TestController controller, Surface surface)
    {
        this.controller = controller;
        this.surface = surface;
        rect = new Rect(random.nextInt(400), random.nextInt(300), surface.getWidth(), surface.getHeight());
    }

    public Sprite(TestController controller, SizedTexture texture)
    {
        this.controller = controller;
        this.texture = texture;
        rect = new Rect(random.nextInt(400), random.nextInt(300), texture.getWidth(), texture.getHeight());
    }

    @Override
    public void move()
    {
        rect.x = x;
        rect.y = y;
        super.move();
    }

    public void draw(Renderer renderer)
    {
        if (texture == null) {
            surface.blit(controller.alphaSurface, rect.x, rect.y);
        } else {
            renderer.copy(texture, rect.x, rect.y);
        }
    }

    public boolean contains(int x, int y)
    {
        if (!rect.contains(x, y)) {
            return false;
        }

        // False if it is a transparent pixel
        RGBA color;
        if (texture == null) {
            color = surface.getPixelRGBA(x - rect.x, y - rect.y);
        } else {
            color = texture.getPixel(controller.renderer, x - rect.x, y - rect.y);
        }

        return color.a > 5;
    }

    public void moveBy(int dx, int dy)
    {
        rect.x += dx;
        rect.y += dy;
    }

    @Override
    protected int getWidth()
    {
        if (texture == null) {
            return surface.getWidth();
        } else {
            return texture.getWidth();
        }
    }

    @Override
    protected int getHeight()
    {
        if (texture == null) {
            return surface.getHeight();
        } else {
            return texture.getHeight();
        }
    }
}
