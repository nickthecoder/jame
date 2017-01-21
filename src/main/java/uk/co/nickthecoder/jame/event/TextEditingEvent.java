package uk.co.nickthecoder.jame.event;

public class TextEditingEvent extends EventForWindow
{
    public String text;

    public int start;
    
    public int length;

    public String toString()
    {
        return "TextEditingEvent start=" + start + " length=" + length + " text=" + text;
    }
}
