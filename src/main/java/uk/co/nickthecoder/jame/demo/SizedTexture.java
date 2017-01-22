package uk.co.nickthecoder.jame.demo;

import uk.co.nickthecoder.jame.Renderer;
import uk.co.nickthecoder.jame.Surface;
import uk.co.nickthecoder.jame.Texture;


public class SizedTexture extends Texture
{
    public final int width;
    public final int height;

    public SizedTexture(Renderer renderer, Surface surface)
    {
        super(renderer, surface);
        width = surface.getWidth();
        height = surface.getHeight();
    }
}