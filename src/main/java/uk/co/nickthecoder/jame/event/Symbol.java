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
    BACKSPACE(8, "Backspace"),
    TAB(9, "Tab"),
    CLEAR(12, "Clear"),
    RETURN(13, "Return"),
    PAUSE(19, "Pause"),
    ESCAPE(27, "Esc"),
    SPACE(32, "Space"),
    EXCLAIM(33, "!"),
    QUOTEDBL(34, "\""),
    HASH(35, "#"),
    DOLLAR(36, "$"),
    AMPERSAND(38, "&"),
    QUOTE(39, "'"),
    LEFTPAREN(40, "("),
    RIGHTPAREN(41, ")"),
    ASTERISK(42, "*"),
    PLUS(43, "+"),
    COMMA(44, ","),
    MINUS(45, "-"),
    PERIOD(46, "."),
    SLASH(47, "/"),
    KEY_0(48, "0"),
    KEY_1(49, "1"),
    KEY_2(50, "2"),
    KEY_3(51, "3"),
    KEY_4(52, "4"),
    KEY_5(53, "5"),
    KEY_6(54, "6"),
    KEY_7(55, "7"),
    KEY_8(56, "8"),
    KEY_9(57, "9"),
    COLON(58, ":"),
    SEMICOLON(59, ";"),
    LESS(60, "<"),
    EQUALS(61, "="),
    GREATER(62, ">"),
    QUESTION(63, "?"),
    AT(64, "@"),

    LEFTBRACKET(91, "["),
    BACKSLASH(92, "\\"),
    RIGHTBRACKET(93, "]"),
    CARET(94, "^"),
    UNDERSCORE(95, "_"),
    BACKQUOTE(96, "`"),
    a(97),
    b(98),
    c(99),
    d(100),
    e(101),
    f(102),
    g(103),
    h(104),
    i(105),
    j(106),
    k(107),
    l(108),
    m(109),
    n(110),
    o(111),
    p(112),
    q(113),
    r(114),
    s(115),
    t(116),
    u(117),
    v(118),
    w(119),
    x(120),
    y(121),
    z(122),
    DELETE(127),

    WORLD_0(160), /* 0xA0 */
    WORLD_1(161),
    WORLD_2(162),
    WORLD_3(163),
    WORLD_4(164),
    WORLD_5(165),
    WORLD_6(166),
    WORLD_7(167),
    WORLD_8(168),
    WORLD_9(169),
    WORLD_10(170),
    WORLD_11(171),
    WORLD_12(172),
    WORLD_13(173),
    WORLD_14(174),
    WORLD_15(175),
    WORLD_16(176),
    WORLD_17(177),
    WORLD_18(178),
    WORLD_19(179),
    WORLD_20(180),
    WORLD_21(181),
    WORLD_22(182),
    WORLD_23(183),
    WORLD_24(184),
    WORLD_25(185),
    WORLD_26(186),
    WORLD_27(187),
    WORLD_28(188),
    WORLD_29(189),
    WORLD_30(190),
    WORLD_31(191),
    WORLD_32(192),
    WORLD_33(193),
    WORLD_34(194),
    WORLD_35(195),
    WORLD_36(196),
    WORLD_37(197),
    WORLD_38(198),
    WORLD_39(199),
    WORLD_40(200),
    WORLD_41(201),
    WORLD_42(202),
    WORLD_43(203),
    WORLD_44(204),
    WORLD_45(205),
    WORLD_46(206),
    WORLD_47(207),
    WORLD_48(208),
    WORLD_49(209),
    WORLD_50(210),
    WORLD_51(211),
    WORLD_52(212),
    WORLD_53(213),
    WORLD_54(214),
    WORLD_55(215),
    WORLD_56(216),
    WORLD_57(217),
    WORLD_58(218),
    WORLD_59(219),
    WORLD_60(220),
    WORLD_61(221),
    WORLD_62(222),
    WORLD_63(223),
    WORLD_64(224),
    WORLD_65(225),
    WORLD_66(226),
    WORLD_67(227),
    WORLD_68(228),
    WORLD_69(229),
    WORLD_70(230),
    WORLD_71(231),
    WORLD_72(232),
    WORLD_73(233),
    WORLD_74(234),
    WORLD_75(235),
    WORLD_76(236),
    WORLD_77(237),
    WORLD_78(238),
    WORLD_79(239),
    WORLD_80(240),
    WORLD_81(241),
    WORLD_82(242),
    WORLD_83(243),
    WORLD_84(244),
    WORLD_85(245),
    WORLD_86(246),
    WORLD_87(247),
    WORLD_88(248),
    WORLD_89(249),
    WORLD_90(250),
    WORLD_91(251),
    WORLD_92(252),
    WORLD_93(253),
    WORLD_94(254),
    WORLD_95(255),

    KP0(256, "0"),
    KP1(257, "1"),
    KP2(258, "2"),
    KP3(259, "3"),
    KP4(260, "4"),
    KP5(261, "5"),
    KP6(262, "6"),
    KP7(263, "7"),
    KP8(264, "8"),
    KP9(265, "9"),
    KP_PERIOD(266, "."),
    KP_DIVIDE(267, "/"),
    KP_MULTIPLY(268, "*"),
    KP_MINUS(269, "-"),
    KP_PLUS(270, "+"),
    KP_ENTER(271, "Enter"),
    KP_EQUALS(272, "="),

    UP(273, "Up"),
    DOWN(274, "Down"),
    RIGHT(275, "Right"),
    LEFT(276, "Left"),
    INSERT(277, "Insert"),
    HOME(278, "Home"),
    END(279, "End"),
    PAGEUP(280, "PgUp"),
    PAGEDOWN(281, "PgDn"),

    F1(282),
    F2(283),
    F3(284),
    F4(285),
    F5(286),
    F6(287),
    F7(288),
    F8(289),
    F9(290),
    F10(291),
    F11(292),
    F12(293),
    F13(294),
    F14(295),
    F15(296),

    NUMLOCK(300, "NumLk"),
    CAPSLOCK(301, "CapsLk"),
    SCROLLOCK(302, "ScrLk"),
    RSHIFT(303, "RShift"),
    LSHIFT(304, "LShift"),
    RCTRL(305, "RCtrl"),
    LCTRL(306, "LCTRL"),
    RALT(307, "RAlt"),
    LALT(308, "LAlt"),
    RMETA(309, "RMeta"),
    LMETA(310, "LMeta"),
    LSUPER(311, "LSuper"),
    RSUPER(312, "RSuper"),
    MODE(313, "Mode"),
    COMPOSE(314, "Compose"),

    HELP(315, "Help"),
    PRINT(316, "PrtSc"),
    SYSREQ(317, "SysReq"),
    BREAK(318, "Break"),
    MENU(319, "Menu"),
    POWER(320, "Power"),
    EURO(321, "€"),
    UNDO(322, "Undo");

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

    private Symbol(int value)
    {
        this.value = value;
        this.label = this.name();
    }

    private Symbol(int value, String label)
    {
        this.value = value;
        this.label = label;
    }
}
