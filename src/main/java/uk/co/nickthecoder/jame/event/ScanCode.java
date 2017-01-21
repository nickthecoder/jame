/*******************************************************************************
 * Copyright (c) 2013 Nick Robinson
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution ), and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package uk.co.nickthecoder.jame.event;

import java.util.HashMap;

/**
 * Scan codes for each key on a keyboard. These values refer to the POSITION of a key, rather than its label.
 * For example, the ScanCode {@link #Q}, will be used for the "A" key on an azerty keyboard.
 * This makes it ideal to use for games, because usually care more about the position of the key than its label.
 * <p>
 * The values are based on SDL2's SDL_scancode.h, which was based on the
 * <a href="http://www.usb.org/developers/devclass_docs/Hut1_12v2.pdf">USB usage page standard</a>
 * </p>
 */
public enum ScanCode
{
    NONE(0, "NONE"),
    A(4, "A"),
    B(5, "B"),
    C(6, "C"),
    D(7, "D"),
    E(8, "E"),
    F(9, "F"),
    G(10, "G"),
    H(11, "H"),
    I(12, "I"),
    J(13, "J"),
    K(14, "K"),
    L(15, "L"),
    M(16, "M"),
    N(17, "N"),
    O(18, "O"),
    P(19, "P"),
    Q(20, "Q"),
    R(21, "R"),
    S(22, "S"),
    T(23, "T"),
    U(24, "U"),
    V(25, "V"),
    W(26, "W"),
    X(27, "X"),
    Y(28, "Y"),
    Z(29, "Z"),

    KEY_1(30, "1"),
    KEY_2(31, "2"),
    KEY_3(32, "3"),
    KEY_4(33, "4"),
    KEY_5(34, "5"),
    KEY_6(35, "6"),
    KEY_7(36, "7"),
    KEY_8(37, "8"),
    KEY_9(38, "9"),
    KEY_0(39, "0"),

    RETURN(40, "Return"),
    ESCAPE(41, "Escape"),
    BACKSPACE(42, "Backspace"),
    TAB(43, "Tab"),
    SPACE(44, "Space"),

    MINUS(45, "Minus"),
    EQUALS(46, "Equals"),
    LEFTBRACKET(47, "Left Bracket"),
    RIGHTBRACKET(48, "Right Bracket"),
    /**
     * Located at the lower left of the return key on ISO keyboards and at the right end
     * of the QWERTY row on ANSI keyboards. Produces REVERSE SOLIDUS (backslash, "") and
     * VERTICAL LINE in a US layout , ""), REVERSE SOLIDUS and VERTICAL LINE in a UK Mac
     * layout , ""), NUMBER SIGN and TILDE in a UK Windows layout , ""), DOLLAR SIGN and POUND SIGN
     * in a Swiss German layout , ""), NUMBER SIGN and APOSTROPHE in a German layout , ""), GRAVE
     * ACCENT and POUND SIGN in a French Mac layout , ""), and ASTERISK and MICRO SIGN in a
     * French Windows layout.
     */
    BACKSLASH(49, "Backslash"),
    /**
     * ISO USB keyboards actually use this code instead of 49 for the same key , ""), but all
     * OSes I've seen treat the two codes identically. So , ""), as an implementor , ""), unless
     * your keyboard generates both of those codes and your OS treats them differently , ""),
     * you should generate SDL_SCANCODE_BACKSLASH instead of this code. As a user , ""), you
     * should not rely on this code because SDL will never generate it with most (all?, "")
     * keyboards.
     */
    NONUSHASH(50, "Non-US Hash"),
    SEMICOLON(51, "Semicolon"),
    APOSTROPHE(52, "Apostrophe"),
    /**
     * Located in the top left corner (on both ANSI and ISO keyboards, ""). Produces GRAVE ACCENT and
     * TILDE in a US Windows layout and in US and UK Mac layouts on ANSI keyboards , ""), GRAVE ACCENT
     * and NOT SIGN in a UK Windows layout , ""), SECTION SIGN and PLUS-MINUS SIGN in US and UK Mac
     * layouts on ISO keyboards , ""), SECTION SIGN and DEGREE SIGN in a Swiss German layout (Mac:
     * only on ISO keyboards, "") , ""), CIRCUMFLEX ACCENT and DEGREE SIGN in a German layout (Mac: only on
     * ISO keyboards, "") , ""), SUPERSCRIPT TWO and TILDE in a French Windows layout , ""), COMMERCIAL AT and
     * NUMBER SIGN in a French Mac layout on ISO keyboards , ""), and LESS-THAN SIGN and GREATER-THAN
     * SIGN in a Swiss German , ""), German , ""), or French Mac layout on ANSI keyboards.
     */
    GRAVE(53, "Grave"),
    COMMA(54, "Comma"),
    PERIOD(55, "Period"),
    SLASH(56, "Slash"),

    CAPSLOCK(57, "Caps Lock"),

    F1(58, "F1"),
    F2(59, "F2"),
    F3(60, "F3"),
    F4(61, "F4"),
    F5(62, "F5"),
    F6(63, "F6"),
    F7(64, "F7"),
    F8(65, "F8"),
    F9(66, "F9"),
    F10(67, "F10"),
    F11(68, "F11"),
    F12(69, "F12"),

    PRINTSCREEN(70, "Print Screen"),
    SCROLLLOCK(71, "Scroll Lock"),
    PAUSE(72, "Pause"),
    /**
     * insert on PC , ""), help on some Mac keyboards (but does send code 73 , ""), not 117, "")
     */
    INSERT(73, "Insert"),
    HOME(74, "Home"),
    PAGEUP(75, "Page Up"),
    DELETE(76, "Delete"),
    END(77, "End"),
    PAGEDOWN(78, "Page Down"),
    RIGHT(79, "Right"),
    LEFT(80, "Left"),
    DOWN(81, "Down"),
    UP(82, "Up"),

    /**
     * num lock on PC , ""), clear on Mac keyboards
     */
    NUMLOCKCLEAR(83, "Number Lock Clear"),
    KP_DIVIDE(84, "KP Divide"),
    KP_MULTIPLY(85, "KP Multiply"),
    KP_MINUS(86, "KP Minus"),
    KP_PLUS(87, "KP Plus"),
    KP_ENTER(88, "KP Enter"),
    KP_1(89, "KP 1"),
    KP_2(90, "KP 2"),
    KP_3(91, "KP 3"),
    KP_4(92, "KP 4"),
    KP_5(93, "KP 5"),
    KP_6(94, "KP 6"),
    KP_7(95, "KP 7"),
    KP_8(96, "KP 8"),
    KP_9(97, "KP 9"),
    KP_0(98, "KP 0"),
    KP_PERIOD(99, "KP Period"),

    /**
     * This is the additional key that ISO keyboards have over ANSI ones , ""), located between left shift and Y.
     * Produces GRAVE ACCENT and TILDE in a US or UK Mac layout , ""), REVERSE SOLIDUS
     * (backslash, "") and VERTICAL LINE in a US or UK Windows layout , ""), and
     * LESS-THAN SIGN and GREATER-THAN SIGN in a Swiss German , ""), German , ""), or French layout.
     */
    NONUSBACKSLASH(100, "Non-US Backslash"),
    /**
     * windows contextual menu , ""), compose
     */
    APPLICATION(101, "Application"),
    /**
     * The USB document says this is a status flag , ""), not a physical key - but some Mac keyboards
     * do have a power key.
     */
    POWER(102, "Power"),
    KP_EQUALS(103, "PK Equals"),
    F13(104, "F13"),
    F14(105, "F14"),
    F15(106, "F15"),
    F16(107, "F16"),
    F17(108, "F17"),
    F18(109, "F18"),
    F19(110, "F19"),
    F20(111, "F20"),
    F21(112, "F21"),
    F22(113, "F22"),
    F23(114, "F23"),
    F24(115, "F24"),
    EXECUTE(116, "Execute"),
    HELP(117, "Help"),
    MENU(118, "Menu"),
    SELECT(119, "Select"),
    STOP(120, "Stop"),
    /**
     * redo
     * */
    AGAIN(121, "Redo"),
    UNDO(122, "Undo"),
    CUT(123, "Cut"),
    COPY(124, "Copy"),
    PASTE(125, "Paste"),
    FIND(126, "Find"),
    MUTE(127, "Mute"),
    VOLUMEUP(128, "Volume Up"),
    VOLUMEDOWN(129, "Volume Down"),
    /* not sure whether there's a reason to enable these */
    /* SDL_SCANCODE_LOCKINGCAPSLOCK( 130 , ""), */
    /* SDL_SCANCODE_LOCKINGNUMLOCK( 131 , ""), */
    /* SDL_SCANCODE_LOCKINGSCROLLLOCK( 132 , ""), */
    KP_COMMA(133, "KP Comma"),
    KP_EQUALSAS400(134, "KP Equals AS-400"),

    /**
     * used on Asian keyboards , ""), see footnotes in USB doc
     */
    INTERNATIONAL1(135, "International 1"),
    INTERNATIONAL2(136, "International 2"),
    INTERNATIONAL3(137, "International 3"),
    INTERNATIONAL4(138, "International 4"),
    INTERNATIONAL5(139, "International 5"),
    INTERNATIONAL6(140, "International 6"),
    INTERNATIONAL7(141, "International 7"),
    INTERNATIONAL8(142, "International 8"),
    INTERNATIONAL9(143, "International 9"),
    /** < Hangul/English toggle */
    LANG1(144, "Lang 1"),
    /** < Hanja conversion */
    LANG2(145, "Lang 2"),
    /** < Katakana */
    LANG3(146, "Lang 3"),
    /** < Hiragana */
    LANG4(147, "Lang 4"),
    /** < Zenkaku/Hankaku */
    LANG5(148, "Lang 5"),
    /** < reserved */
    LANG6(149, "Lang 6"),
    /** < reserved */
    LANG7(150, "Lang 7"),
    /** < reserved */
    LANG8(151, "Lang 8"),
    /** < reserved */
    LANG9(152, "Lang 9"),

    /** < Erase-Eaze */
    ALTERASE(153, "Alt Erase"),
    SYSREQ(154, "Sys Req"),
    CANCEL(155, "Cancel"),
    CLEAR(156, "Clear"),
    PRIOR(157, "Prior"),
    RETURN2(158, "Return 2"),
    SEPARATOR(159, "Separator"),
    OUT(160, "Out"),
    OPER(161, "Oper"),
    CLEARAGAIN(162, "Clear Again"),
    CRSEL(163, "CRSEL"),
    EXSEL(164, "EXSEL"),

    KP_00(176, "KP 00"),
    KP_000(177, "KP 000"),
    THOUSANDSSEPARATOR(178, "Thousads Separator"),
    DECIMALSEPARATOR(179, "Decimal Separator"),
    CURRENCYUNIT(180, "Currency Unit"),
    CURRENCYSUBUNIT(181, "Current Sub Unit"),
    KP_LEFTPAREN(182, "KP Left Paren"),
    KP_RIGHTPAREN(183, "KP Right Paren"),
    KP_LEFTBRACE(184, "KP Left Brace"),
    KP_RIGHTBRACE(185, "KP Right Brace"),
    KP_TAB(186, "KP Tab"),
    KP_BACKSPACE(187, "KP Backspace"),
    KP_A(188, "KP A"),
    KP_B(189, "KP B"),
    KP_C(190, "KP C"),
    KP_D(191, "KP D"),
    KP_E(192, "KP E"),
    KP_F(193, "KP F"),
    KP_XOR(194, "KP XOR"),
    KP_POWER(195, "KP Power"),
    KP_PERCENT(196, "KP Percent"),
    KP_LESS(197, "KP Less"),
    KP_GREATER(198, "KP Greater"),
    KP_AMPERSAND(199, "KP Ampersand"),
    KP_DBLAMPERSAND(200, "KP DBL Ampersand"),
    KP_VERTICALBAR(201, "KP Vertical Bar"),
    KP_DBLVERTICALBAR(202, "KP DBL Vertical Bar"),
    KP_COLON(203, "KP Colon"),
    KP_HASH(204, "KP Hash"),
    KP_SPACE(205, "KP Space"),
    KP_AT(206, "KP At"),
    KP_EXCLAM(207, "KP Exclamation"),
    KP_MEMSTORE(208, "KP Mem Store"),
    KP_MEMRECALL(209, "KP Mem Recall"),
    KP_MEMCLEAR(210, "KP Mem Clear"),
    KP_MEMADD(211, "KP Mem Add"),
    KP_MEMSUBTRACT(212, "KP Mem Subtract"),
    KP_MEMMULTIPLY(213, "KP Mem Multiply"),
    KP_MEMDIVIDE(214, "KP Mem Divide"),
    KP_PLUSMINUS(215, "KP Plus Minus"),
    KP_CLEAR(216, "KP Clear"),
    KP_CLEARENTRY(217, "KP Clear Entry"),
    KP_BINARY(218, "KP Binary"),
    KP_OCTAL(219, "KP Octual"),
    KP_DECIMAL(220, "KP Decial"),
    KP_HEXADECIMAL(221, "KP Hexadecimal"),

    LCTRL(224, "Left Ctrl"),
    LSHIFT(225, "Left Shift"),
    
    LALT(226, "Left Alt"),
    LGUI(227, "Left GUI"),
    RCTRL(228, "Right Control"),
    RSHIFT(229, "Right Shift"),
    RALT(230, "Right Alt"),
    RGUI(231, "Right GUI"),

    /**
     * < I'm not sure if this is really not covered by any of the above , ""), but since there's a
     * special KMOD_MODE for it I'm adding it here
     */
    MODE(257, "Mode"),

    AUDIONEXT(258, "Audio Next"),
    AUDIOPREV(259, "Audio Prev"),
    AUDIOSTOP(260, "Audio Stop"),
    AUDIOPLAY(261, "Audio Play"),
    AUDIOMUTE(262, "Audio Mute"),
    MEDIASELECT(263, "Media Select"),
    WWW(264, "WWW"),
    MAIL(265, "Mail"),
    CALCULATOR(266, "Calculator"),
    COMPUTER(267, "Computer"),
    AC_SEARCH(268, "AC Search"),
    AC_HOME(269, "AC Home"),
    AC_BACK(270, "AC Back"),
    AC_FORWARD(271, "AC Forward"),
    AC_STOP(272, "AC Stop"),
    AC_REFRESH(273, "AC Refresh"),
    AC_BOOKMARKS(274, "AC Bookmarks"),

    BRIGHTNESSDOWN(275, "Brightness Down"),
    BRIGHTNESSUP(276, "Brightness Up"),
    /** < display mirroring/dual display switch , ""), video mode switch */
    DISPLAYSWITCH(277, "Display Switch"),
    KBDILLUMTOGGLE(278, "Illumination Toggle"),
    KBDILLUMDOWN(279, "Illumination Down"),
    KBDILLUMUP(280, "Illumination Up"),
    EJECT(281, "Eject"),
    SLEEP(282, "Sleep"),

    APP1(283, "App 1"),
    APP2(284, "App 2");

    // Reverse-lookup map for getting a day from an abbreviation
    private static final HashMap<Integer, ScanCode> lookup = new HashMap<Integer, ScanCode>();

    static {
        for (ScanCode keysEnum : ScanCode.values()) {
            lookup.put(keysEnum.value, keysEnum);
        }
    }

    public static ScanCode findKey(int keyCode)
    {
        return lookup.get(keyCode);
    }

    public final int value;

    public final String label;
   
    private ScanCode(int value, String label)
    {
        this.value = value;
        this.label = label;
    }
    
    public String toString()
    {
        return label;
    }
}
