package uk.co.nickthecoder.jame.event;

/**
 * Fired when a file is dragged onto an SDL window.
 * <p>
 * Note, only SDL2.0 SDL_DROPFILE events are implemented, but a future version of SDL has additional events, which are
 * ignored. If you drop multiple files in one go, this implementation will fire a DropFileEvent for each file in turn,
 * without any start/stop events. Future versions of Jame may also include these start/stop events (and will have their
 * own class).
 * </p>
 */
public class DropFileEvent extends DropEvent
{
    public String filename;

    public String toString()
    {
        return "DropEvent " + filename;
    }
}
