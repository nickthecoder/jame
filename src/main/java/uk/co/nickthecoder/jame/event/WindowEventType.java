package uk.co.nickthecoder.jame.event;

/**
 * Used by {@link WindowEvent#windowEventType} to indicate the type of event, such as minimised, maximised etc.
 * <p>
 * Note The SDL C code uses the name SDL_WindowEventId, but I dislike "Id", and prefer "type", because a
 * SDL_WindowEventId does NOT identify a window event! There will be many {@link WindowEvent}s, with the same
 * {@link WindowEvent#windowEventType}.
 * </p>
 */
public enum WindowEventType
{
    /** Never used */
    NONE,

    /** Window has been shown */
    SHOWN,

    /** Window has been hidden */
    HIDDEN,

    /**
     * Window has been exposed and should be redrawn
     */
    EXPOSED,

    /**
     * Window has been moved to data1, data2
     */
    MOVED,

    /** Window has been resized to data1xdata2 */
    RESIZED,

    /**
     * The window size has changed, either as a result of an API call or through the
     * system or user changing the window size.
     */
    SIZE_CHANGED,

    /** Window has been minimized */
    MINIMIZED,

    /** Window has been maximized */
    MAXIMIZED,
    /**
     * Window has been restored to normal size and position
     */
    RESTORED,

    /** Window has gained mouse focus */
    ENTER,

    /** Window has lost mouse focus */
    LEAVE,

    /** Window has gained keyboard focus */
    FOCUS_GAINED,

    /** Window has lost keyboard focus */
    FOCUS_LOST,

    /** The window manager requests that the window be closed */
    CLOSE,

    /**
     * Window is being offered a focus (should SetWindowInputFocus() on itself or a
     * sub-window, or ignore)
     */
    TAKE_FOCUS,

    /** Window had a hit test that wasn't SDL_HITTEST_NORMAL. */
    HIT_TEST

}
