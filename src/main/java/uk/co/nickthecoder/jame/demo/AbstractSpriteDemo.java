package uk.co.nickthecoder.jame.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.RGBA;
import uk.co.nickthecoder.jame.Surface;

public abstract class AbstractSpriteDemo extends AbstractDemo
{
    public SizedTexture[] textures;

    public Surface[] surfaces;

    public List<Sprite> sprites;

    public DemoController controller;

    public int count;

    public static final Random random = new Random();

    public AbstractSpriteDemo(int count, SizedTexture... textures)
    {
        this.count = count;
        this.textures = textures;
    }

    public AbstractSpriteDemo(int count, Surface... surfaces)
    {
        this.count = count;
        this.surfaces = surfaces;
    }

    @Override
    public void showInfo(DemoController controller) throws JameException
    {
        super.showInfo(controller);
        System.out.println("Sprite Count : " + this.sprites.size());
    }

    @Override
    public void begin(DemoController controller) throws JameException
    {
        this.controller = controller;
        sprites = new ArrayList<Sprite>();
        for (int i = 0; i < count; i++) {
            addSprite();
        }

    }
    
    public void addSprite()
    {
        sprites.add(createSprite());
    }
    
    public Sprite createSprite()
    {
        Sprite sprite;
        if (textures == null) {
            sprite = new Sprite(controller, surfaces[random.nextInt(surfaces.length)]);
        } else {
            sprite = new Sprite(controller, textures[random.nextInt(textures.length)]);        
        }
        
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
    public void display(DemoController controller) throws JameException
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
