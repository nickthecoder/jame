package uk.co.nickthecoder.jame.event;


public enum MouseButton
{
    LEFT( MouseButtonEvent.BUTTON_LEFT, "Left" ),
    MIDDLE(MouseButtonEvent.BUTTON_MIDDLE, "Middle" ),
    RIGHT(MouseButtonEvent.BUTTON_RIGHT, "Right" ),
    WHEEL_UP(MouseButtonEvent.BUTTON_WHEEL_UP, "Wheel Up" ),
    WHEEL_DOWN(MouseButtonEvent.BUTTON_WHEEL_DOWN, "Wheel Down" ),
    WHEEL_LEFT(MouseButtonEvent.BUTTON_WHEEL_LEFT, "Wheel Left" ),
    WHEEL_RIGHT(MouseButtonEvent.BUTTON_WHEEL_RIGHT, "Wheel Right" );
        
    public final int value;
    
    public final String label;
    
    private MouseButton(int value, String label)
    {
        this.value = value;
        this.label = label;
    }
    
    public static MouseButton findButton( int buttonNumber )
    {
        for(MouseButton mouseButton : values()){
            if( mouseButton.value == buttonNumber ) {
                return mouseButton;
            }
        }
        return null;
    }
}
