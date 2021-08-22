/*
 * $Id: Yytoken.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package ru.epserv.epmodule.json.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class Yytoken {
    public static final int TYPE_VALUE = 0;//JSON primitive value: string,number,boolean,null
    public static final int TYPE_LEFT_BRACE = 1;
    public static final int TYPE_RIGHT_BRACE = 2;
    public static final int TYPE_LEFT_SQUARE = 3;
    public static final int TYPE_RIGHT_SQUARE = 4;
    public static final int TYPE_COMMA = 5;
    public static final int TYPE_COLON = 6;
    public static final int TYPE_EOF = -1;//end of file

    public int type;
    @Nullable
    public Object value;

    public Yytoken(int type, @Nullable Object value) {
        this.type = type;
        this.value = value;
    }

    @NotNull
    public String toString() {
        return switch (type) {
            case TYPE_VALUE -> "VALUE(" + value + ")";
            case TYPE_LEFT_BRACE -> "LEFT BRACE({)";
            case TYPE_RIGHT_BRACE -> "RIGHT BRACE(})";
            case TYPE_LEFT_SQUARE -> "LEFT SQUARE([)";
            case TYPE_RIGHT_SQUARE -> "RIGHT SQUARE(])";
            case TYPE_COMMA -> "COMMA(,)";
            case TYPE_COLON -> "COLON(:)";
            case TYPE_EOF -> "END OF FILE";
            default -> "";
        };
    }
}
