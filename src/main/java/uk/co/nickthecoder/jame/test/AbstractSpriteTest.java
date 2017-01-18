package uk.co.nickthecoder.jame.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Surface;

public abstract class AbstractSpriteTest extends AbstractTest
{
    public SizedTexture[] textures;

    public Surface[] surfaces;

    public List<Sprite> sprites;

    public TestController controller;

    public int count;

    public static final Random random = new Random();

    public AbstractSpriteTest(int count, SizedTexture... textures)
    {
        this.count = count;
        this.textures = textures;
    }

    public AbstractSpriteTest(int count, Surface... surfaces)
    {
        this.count = count;
        this.surfaces = surfaces;
    }

    @Override
    public void showInfo(TestController controller) throws JameException
    {
        super.showInfo(controller);
        System.out.println("Sprite Count : " + this.sprites.size());
    }

    @Override
    public void begin(TestController controller) throws JameException
    {
        System.out.println("ABT.Begin");
        this.controller = controller;
        sprites = new ArrayList<Sprite>();
        for (int i = 0; i < count; i++) {
            createSprite();
        }

    }
    
    public Sprite createSprite()
    {
        Sprite sprite;
        if (textures == null) {
            sprite = new Sprite(controller, surfaces[random.nextInt(surfaces.length)]);
        } else {
            sprite = new Sprite(controller, textures[random.nextInt(textures.length)]);        
        }
        sprites.add(sprite);
        
        return sprite;
    }

    public void displaySprites() throws JameException
    {

        for (Sprite sprite : sprites) {
            sprite.draw(controller.renderer);
        }
    }

    public void actions()
    {
        for (Sprite sprite : sprites) {
            sprite.move();
        }
    }

    @Override
    public void display(TestController controller) throws JameException
    {

        if (textures == null) {
            controller.alphaSurface.fill(RGBA.BLACK);
        } else {
            controller.renderer.setDrawColor(RGBA.BLACK);
            controller.renderer.clear();
        }
        
        displaySprites();
        actions();

        if (textures == null) {
            controller.showSurface(true);
        } else {
            controller.renderer.present();
        }
    }

}
