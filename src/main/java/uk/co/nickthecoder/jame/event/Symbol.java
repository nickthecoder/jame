/*******************************************************************************
 * Copyright (c) 2014 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import java.util.HashMap;

import uk.co.nickthecoder.jame.event.ScanCode;

/**
 * Symbols on a keyboard.
 * These do NOT represent the physical position of the key on a keyboard, but rather the label on the key.
 * Therefore it is often better to use {@link ScanCode}s instead. For example, the keys "WASD" are often used in
 * games, but on an "Azerty" keyboard you would want to use the keys "ZQSD".
 * {@link ScanCode#W} is the scan code for the Azerty "Z" key.
 */
public enum Symbol
{
    NONE(0,"NONE"),
    
    RETURN(13, "Return"),
    ESCAPE(27, "Escape"),
    BACKSPACE(8, "Backspace"),
    TAB(9, "Tab"),    
    SPACE(32, "Space", true),
    EXCLAIM('!'),
    QUOTEDBL('\\'),
    HASH('#'),
    DOLLAR('$'),
    AMPERSAND('&'),
    QUOTE('\''),
    LEFTPAREN('('),
    RIGHTPAREN(')'),
    ASTERISK('*'),
    PLUS('+'),
    COMMA(','),
    MINUS('-'),
    PERIOD('.'),
    SLASH('/'),
    KEY_0('0'),
    KEY_1('1'),
    KEY_2('2'),
    KEY_3('3'),
    KEY_4('4'),
    KEY_5('5'),
    KEY_6('6'),
    KEY_7('7'),
    KEY_8('8'),
    KEY_9('9'),
    COLON(':'),
    SEMICOLON(';'),
    LESS('<'),
    EQUALS('='),
    GREATER('>'),
    QUESTION('?'),
    AT('@'),

    LEFTBRACKET('['),
    BACKSLASH('\\'),
    RIGHTBRACKET(']'),
    CARET('^'),
    UNDERSCORE('_'),
    BACKQUOTE('`'),
    a('a'),
    b('b'),
    c('c'),
    d('d'),
    e('e'),
    f('f'),
    g('g'),
    h('h'),
    i('i'),
    j('j'),
    k('k'),
    l('l'),
    m('m'),
    n('n'),
    o('o'),
    p('p'),
    q('q'),
    r('r'),
    s('s'),
    t('t'),
    u('u'),
    v('v'),
    w('w'),
    x('x'),
    y('y'),
    z('z'),
    
    F1(ScanCode.F1),
    F2(ScanCode.F2),
    F3(ScanCode.F3),
    F4(ScanCode.F4),
    F5(ScanCode.F5),
    F6(ScanCode.F6),
    F7(ScanCode.F7),
    F8(ScanCode.F8),
    F9(ScanCode.F9),
    F10(ScanCode.F10),
    F11(ScanCode.F11),
    F12(ScanCode.F12),
    
    PRINTSCREEN(ScanCode.PRINTSCREEN),
    SCROLLLOCK(ScanCode.SCROLLLOCK),
    PAUSE(ScanCode.PAUSE),
    INSERT(ScanCode.INSERT),
    HOME(ScanCode.HOME),
    PAGEUP(ScanCode.PAGEUP),
    DELETE(127, "DELETE"),
    END(ScanCode.END),
    PAGEODWN(ScanCode.PAGEDOWN),
    RIGHT(ScanCode.RIGHT),
    LEFT(ScanCode.LEFT),
    DOWN(ScanCode.DOWN),
    UP(ScanCode.UP),
    
    NUMLOCKCLEAR(ScanCode.NUMLOCK),
    KP_DIVIDE(ScanCode.KP_DIVIDE),
    KP_MULTIPLY(ScanCode.KP_MULTIPLY),
    KP_MINUS(ScanCode.KP_MINUS),
    KP_PLUS(ScanCode.KP_PLUS),
    KP_ENTER(ScanCode.KP_ENTER),
    KP_1(ScanCode.KP_1),
    KP_2(ScanCode.KP_2),
    KP_3(ScanCode.KP_3),
    KP_4(ScanCode.KP_4),
    KP_5(ScanCode.KP_5),
    KP_6(ScanCode.KP_6),
    KP_7(ScanCode.KP_7),
    KP_8(ScanCode.KP_8),
    KP_9(ScanCode.KP_9),
    KP_0(ScanCode.KP_0),
    KP_PERIOD(ScanCode.KP_PERIOD),
    
    LSHIFT(ScanCode.LSHIFT),
    RSHIFT(ScanCode.RSHIFT),
    LALT(ScanCode.LALT),
    RALT(ScanCode.RALT),
    LCTRL(ScanCode.LCTRL),
    RCTRL(ScanCode.RCTRL),
    //(ScanCode.),
    
    //TODO Add the remainder
    SLEEP(ScanCode.SLEEP);

    
    // Reverse-lookup map for getting a day from an abbreviation
    private static final HashMap<Integer, Symbol> lookup = new HashMap<Integer, Symbol>();

    static {
        for (Symbol keysEnum : Symbol.values()) {
            lookup.put(keysEnum.value, keysEnum);
        }
    }

    public static Symbol findKey(int keyCode)
    {
        return lookup.get(keyCode);
    }

    public final int value;

    public final String label;

    public final boolean printable;
    
    /**
     * Creates a Symbol, which has the 31st bit set - these are used for non-printable characters
     * @param label
     * @param value
     */
    private Symbol(ScanCode scanCode)
    {
        this( scanCode.value | 1<<30, scanCode.label, false );
    }
    
    private Symbol(char c)
    {
        this( (int) c, Character.toString(c), true );
    }
    
    private Symbol(int value, String label)
    {
        this( value, label, false );
    }

    private Symbol(int value, String label, boolean printable)
    {
        this.value = value;
        this.label = label;
        this.printable = printable;
    }
    
    public String toString()
    {
        return label;
    }
}
