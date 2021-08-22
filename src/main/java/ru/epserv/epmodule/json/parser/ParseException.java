package ru.epserv.epmodule.json.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * ParseException explains why and where the error occurs in source JSON text.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class ParseException extends Exception {
    public static final int ERROR_UNEXPECTED_CHAR = 0;
    public static final int ERROR_UNEXPECTED_TOKEN = 1;
    public static final int ERROR_UNEXPECTED_EXCEPTION = 2;
    @Serial
    private static final long serialVersionUID = -7880698968187728547L;
    private int errorType;
    @Nullable
    private Object unexpectedObject;
    private int position;

    public ParseException(int errorType) {
        this(-1, errorType, null);
    }

    public ParseException(int errorType, @Nullable Object unexpectedObject) {
        this(-1, errorType, unexpectedObject);
    }

    public ParseException(int position, int errorType, @Nullable Object unexpectedObject) {
        this.position = position;
        this.errorType = errorType;
        this.unexpectedObject = unexpectedObject;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    /**
     * @return The character position (starting with 0) of the input where the error occurs.
     * @see org.json.simple.parser.JSONParser#getPosition()
     */
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return One of the following base on the value of errorType:
     * ERROR_UNEXPECTED_CHAR		java.lang.Character
     * ERROR_UNEXPECTED_TOKEN		org.json.simple.parser.Yytoken
     * ERROR_UNEXPECTED_EXCEPTION	java.lang.Exception
     * @see org.json.simple.parser.Yytoken
     */
    @Nullable
    public Object getUnexpectedObject() {
        return unexpectedObject;
    }

    public void setUnexpectedObject(@Nullable Object unexpectedObject) {
        this.unexpectedObject = unexpectedObject;
    }

    @NotNull
    public String getMessage() {
        return switch (errorType) {
            case ERROR_UNEXPECTED_CHAR -> String.format("Unexpected character (%s) at position %d.", unexpectedObject != null ? unexpectedObject.toString() : "", position);
            case ERROR_UNEXPECTED_TOKEN -> String.format("Unexpected token %s at position %d.", unexpectedObject != null ? unexpectedObject.toString() : "", position);
            case ERROR_UNEXPECTED_EXCEPTION -> String.format("Unexpected exception at position %d: %s", position, unexpectedObject != null ? unexpectedObject.toString() : "");
            default -> String.format("Unknown error at position %d.", position);
        };
    }
}
