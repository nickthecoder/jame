package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.Rect;
import uk.co.nickthecoder.jame.Renderer;

public class RotateAndScaleDemo extends AbstractSpriteDemo
{

    public RotateAndScaleDemo(int count, SizedTexture... textures)
    {
        super(count, textures);
    }

    @Override
    public Sprite createSprite()
    {
        Sprite sprite = new RotateSprite(controller, textures[random.nextInt(textures.length)]);

        return sprite;
    }

    public class RotateSprite extends Sprite
    {
        public double angle;
        public double rotate;
        public Rect srcRect;
        public int bounceCount = 0;

        public RotateSprite(DemoController controller, SizedTexture texture)
        {
            super(controller, texture);
            this.rotate = random.nextDouble();
            srcRect = new Rect(0, 0, texture.getWidth(), texture.getHeight());
        }

        @Override
        public void move()
        {
            this.angle += this.rotate;
            super.move();
            if (bounceCount > 0) {
                bounceCount--;
            }
        }

        public static final int MAX_BOUNCE_COUNT = 100;

        @Override
        public void bounced()
        {
            bounceCount = MAX_BOUNCE_COUNT;
            rotate = -rotate;
        }

        @Override
        public void draw(Renderer renderer)
        {
            double scale = 1 + 0.3 * (
                bounceCount > 90 ?
                (MAX_BOUNCE_COUNT - bounceCount) / 10.0:
                bounceCount / 90.0
            );

            int width = (int) (texture.getWidth() * scale);
            int height = (int) (texture.getHeight() * scale);

            Rect dstRect = new Rect(x - width/2, y - width/2, width, height);
            renderer.copy(texture, srcRect, dstRect, angle, width / 2, height / 2, false, false);

        }

    }
}
