package uk.co.nickthecoder.jame.test;

import uk.co.nickthecoder.jame.JameException;
import uk.co.nickthecoder.jame.Surface;

public class SpriteTest extends AbstractSpriteTest
{
    public SpriteTest(SizedTexture... textures)
        throws JameException
    {
        super(0, textures);
    }

    public SpriteTest(Surface... surfaces)
        throws JameException
    {
        super(0, surfaces);
    }

    @Override
    public void display(TestController controller) throws JameException
    {
        if (sprites.size() < 10000) {
            Sprite sprite = createSprite();
            sprite.dx = random.nextInt( 5 ) - 2;
            sprite.dy = random.nextInt( 5 ) - 2;
        }
        
        for (Sprite sprite : sprites) {
            sprite.move();
        }
        
        super.display(controller);
    }

}
