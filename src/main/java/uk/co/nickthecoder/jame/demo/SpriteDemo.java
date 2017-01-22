package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.Surface;

public class SpriteDemo extends AbstractSpriteDemo
{
    public SpriteDemo(SizedTexture... textures)
        throws JameException
    {
        super(0, textures);
    }

    public SpriteDemo(Surface... surfaces)
        throws JameException
    {
        super(0, surfaces);
    }

    @Override
    public void display(DemoController controller) throws JameException
    {
        if (sprites.size() < 10000) {
            Sprite sprite = createSprite();
            sprite.dx = random.nextInt( 5 ) - 2;
            sprite.dy = random.nextInt( 5 ) - 2;
            sprites.add(sprite);
        }
        
        for (Sprite sprite : sprites) {
            sprite.move();
        }
        
        super.display(controller);
    }

}
