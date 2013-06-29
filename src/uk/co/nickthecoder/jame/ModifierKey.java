package uk.co.nickthecoder.jame;

import java.util.EnumSet;

public enum ModifierKey
{
    NONE(0x0000),
    LSHIFT(0x0001),
    RSHIFT(0x0002),
    LCTRL(0x0040),
    RCTRL(0x0080),
    LALT(0x0100),
    RALT(0x0200),
    LMETA(0x0400),
    RMETA(0x0800),
    NUM(0x1000),
    CAPS(0x2000),
    MODE(0x4000);
    
    public static final ModifierKeySet SHIFT = LSHIFT.or( RSHIFT );
    public static final ModifierKeySet CTRL = LCTRL.or( RCTRL );
    public static final ModifierKeySet ALT = LALT.or( RALT );
    public static final ModifierKeySet META = LMETA.or( RMETA );
    
    public final int code;
    
    ModifierKey( int code )
    {
    	this.code = code;
    }
    
    public boolean pressed( int flags )
    {
    	if ( this.code == 0 ){
    		return flags == 0;
    	} else {
    	
    		return (this.code & flags) != 0;
    	
    	}
    }
    
    public ModifierKeySet or( ModifierKey other )
    {
    	return new ModifierKeySet( EnumSet.of( this, other ) );
    }

    
    public final class ModifierKeySet
    {
    	private final int code;
    	
     	public ModifierKeySet( ModifierKey modifierKey )
     	{
     		this.code = modifierKey.code;
     	}
     	
     	public ModifierKeySet( EnumSet<ModifierKey> modifierKeys )
    	{
     		int code = 0;
     		for ( ModifierKey key : modifierKeys ) {
     			code |= key.code;
     		}
     		this.code = code;
    	}
    	
    	public int getCode() 
    	{
    		return this.code;
    	}
    	
        public boolean pressed( int flags )
        {
    		return (this.code & flags) != 0;
        }
    }
}
