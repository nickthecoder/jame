package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;

public class SpriteTest extends AbstractTest
{
    public boolean useTextures;

    public Surface spriteSurface;

    public Texture spriteTexture;

    public int width;

    public int height;

    public List<BouncySprite> sprites;

    public TestController controller;

    public SpriteTest(Texture texture, int width, int height)
        throws JameException
    {
        spriteTexture = texture;
        this.width = width;
        this.height = height;

        this.useTextures = true;
    }
    
    public SpriteTest(Surface surface)
        throws JameException
    {
        spriteSurface = surface;
        this.width = surface.getWidth();
        this.height = surface.getHeight();

        this.useTextures = false;
    }

    public void showInfo(TestController controller) throws JameException
    {
        super.showInfo(controller);
        System.out.println("Sprite Count : " + this.sprites.size());
    }

    @Override
    public void begin(TestController controller) throws JameException
    {

        System.out.println("Sprite " + spriteSurface);
        System.out.println("Sprite " + spriteTexture);
        System.out.println();

        sprites = new ArrayList<BouncySprite>(1000);
        this.controller = controller;
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        if (sprites.size() < 10000) {
            sprites.add(new BouncySprite());
        }

        if (useTextures) {
            controller.renderer.setDrawColor(RGBA.BLACK);
            controller.renderer.clear();
        } else {
            controller.alphaSurface.fill(RGBA.BLACK);
        }

        for (BouncySprite bouncy : sprites) {
            if (useTextures) {
                controller.renderer.copy(spriteTexture, bouncy.x, bouncy.y);
            } else {
                spriteSurface.blit(controller.alphaSurface, bouncy.x, bouncy.y, Surface.BlendMode.COMPOSITE);
            }
            bouncy.move();
        }

        if (useTextures) {
            controller.renderer.present();
        } else {
            controller.showSurface(true);
        }
    }

    class BouncySprite extends Bouncy
    {
        public BouncySprite()
        {
            super();
        }

        @Override
        protected int getWidth()
        {
            return width;
        }

        @Override
        protected int getHeight()
        {
            return height;
        }

    }

}
