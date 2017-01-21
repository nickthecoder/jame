package uk.co.nickthecoder.jame.event;


public enum MouseButton
{
    LEFT( 1, "Left" ),
    MIDDLE( 2, "Middle" ),
    RIGHT( 3, "Right" ),
    X1( 4, "X1" ),
    X2( 5, "X2" );
        
    public static MouseButton findButton( int buttonNumber )
    {
        for(MouseButton mouseButton : values()){
            if( mouseButton.value == buttonNumber ) {
                return mouseButton;
            }
        }
        return null;
    }
    
    public final int value;
    
    public final String label;
    
    private MouseButton(int value, String label)
    {
        this.value = value;
        this.label = label;
    }
    
    
    public String toString()
    {
        return this.label + "(" + this.value + ")";
    }
}
