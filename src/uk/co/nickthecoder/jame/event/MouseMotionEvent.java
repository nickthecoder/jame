package uk.co.nickthecoder.jame.event;

public class MouseMotionEvent extends MouseEvent
{
    /**
     * A bitmask indicating which buttons are currently depressed. You cannot
     * compare with BUTTON_LEFT BUTTON_RIGHT etc directly, use isPressed
     * instead.
     */
    public int state;

    public int relativeX;
    public int relativeY;

    /**
     * Uses bitwise-OR of the this.state to test if a mouse button is pressed.
     * 
     * @param button
     *            The button number (1 for left button etc) You can use the
     *            constants BUTTON_LEFT, BUTTON_MIDDLE
     * @return true iff the mouse button is pressed.
     */
    public boolean isPressed( int button )
    {
        return ( button & ( 1 << ( button - 1 ) ) ) != 0;
    }

    public String toString()
    {
        return "MouseMotionEvent{ state=" + state + ", x=" + x + ", y=" + y + ", relativeX=" + relativeX
            + ", relativeY=" + relativeY + " }";
    }

}
