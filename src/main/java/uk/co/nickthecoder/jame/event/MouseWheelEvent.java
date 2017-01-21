package uk.co.nickthecoder.jame.event;

/**
 * Most data is in the super class {@link MouseEvent}, with one weirdness - the x and y values are not screen positions,
 * but the amount of scrolling.
 * <p>
 * Note, using libsdl2-2.0-0 on linux (running Gnome 3), SDL appears not to send any mouse wheel events for the X axis,
 * when using the trackpad on my T400 laptop. I don't think I can do anything about that.
 * </p>
 */
public class MouseWheelEvent extends MouseEvent
{

    @Override
    public String toString()
    {
        return "MouseWheelEven{ windowID=" + windowID + " mouseID=" + mouseID + ", x=" + x + ", y=" + y + " }";
    }
}
