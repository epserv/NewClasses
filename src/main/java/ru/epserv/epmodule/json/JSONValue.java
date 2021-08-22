/*
 * $Id: JSONValue.java,v 1.1 2006/04/15 14:37:04 platform Exp $
 * Created on 2006-4-15
 */
package ru.epserv.epmodule.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.epserv.epmodule.json.parser.JSONParser;
import ru.epserv.epmodule.json.parser.ParseException;

import java.io.*;
import java.util.Collection;
import java.util.Map;


/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class JSONValue {
    /**
     * Parse JSON text into java object from the input source.
     * Please use parseWithException() if you don't want to ignore the exception.
     *
     * @param in Reader to read JSON from
     * @return Instance of the following:
     * org.json.simple.JSONObject,
     * org.json.simple.JSONArray,
     * java.lang.String,
     * java.lang.Number,
     * java.lang.Boolean,
     * null
     * @see JSONParser#parse(Reader)
     * @see #parseWithException(Reader)
     * @deprecated this method may throw an {@code Error} instead of returning
     * {@code null}; please use {@link org.json.simple.JSONValue#parseWithException(Reader)}
     * instead
     */
    @Nullable
    public static Object parse(@NotNull Reader in) {
        try {
            return new JSONParser().parse(in);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse JSON text into java object from the given string.
     * Please use parseWithException() if you don't want to ignore the exception.
     *
     * @param s String to read JSON from
     * @return Instance of the following:
     * org.json.simple.JSONObject,
     * org.json.simple.JSONArray,
     * java.lang.String,
     * java.lang.Number,
     * java.lang.Boolean,
     * null
     * @see JSONParser#parse(Reader)
     * @see #parseWithException(Reader)
     * @deprecated this method may throw an {@code Error} instead of returning
     * {@code null}; please use {@link org.json.simple.JSONValue#parseWithException(String)}
     * instead
     */
    @Nullable
    public static Object parse(String s) {
        return parse(new StringReader(s));
    }

    /**
     * Parse JSON text into java object from the input source.
     *
     * @param in Reader to read JSON from
     * @return Instance of the following:
     * org.json.simple.JSONObject,
     * org.json.simple.JSONArray,
     * java.lang.String,
     * java.lang.Number,
     * java.lang.Boolean,
     * null
     * @throws IOException    when {@link IOException} happens
     * @throws ParseException when parsing fails
     * @see JSONParser
     */
    @NotNull
    public static Object parseWithException(@NotNull Reader in) throws IOException, ParseException {
        return new JSONParser().parse(in);
    }

    public static Object parseWithException(String s) throws ParseException {
        return new JSONParser().parse(s);
    }

    /**
     * Encode an object into JSON text and write it to out.
     * <p>
     * If this object is a Map or a List, and it's also a JSONStreamAware or a JSONAware, JSONStreamAware or JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from writeJSONString(Writer) of a class that implements both JSONStreamAware and (Map or List) with
     * "this" as the first parameter, use JSONObject.writeJSONString(Map, Writer) or JSONArray.writeJSONString(List, Writer) instead.
     *
     * @param value Value to represent as JSON string
     * @param out   Writer to write to
     * @see JSONObject#writeJSONString(Map, Writer)
     * @see JSONArray#writeJSONString(Collection, Writer)
     */
    public static void writeJSONString(@Nullable Object value, @NotNull Writer out) throws IOException {
        if (value == null) {
            out.write("null");
            return;
        }

        if (value instanceof String s) {
            out.write('\"');
            out.write(escape(s));
            out.write('\"');
            return;
        }

        if (value instanceof Double d) {
            if (d.isInfinite() || d.isNaN())
                out.write("null");
            else
                out.write(value.toString());
            return;
        }

        if (value instanceof Float f) {
            if (f.isInfinite() || f.isNaN())
                out.write("null");
            else
                out.write(value.toString());
            return;
        }

        if (value instanceof Number n) {
            out.write(n.toString());
            return;
        }

        if (value instanceof Boolean b) {
            out.write(b.toString());
            return;
        }

        if (value instanceof JSONStreamAware aware) {
            aware.writeJSONString(out);
            return;
        }

        if (value instanceof JSONAware aware) {
            out.write(aware.toJSONString());
            return;
        }

        if (value instanceof Map<?, ?> m) {
            JSONObject.writeJSONString(m, out);
            return;
        }

        if (value instanceof Collection<?> c) {
            JSONArray.writeJSONString(c, out);
            return;
        }

        if (value instanceof byte[] bytes) {
            JSONArray.writeJSONString(bytes, out);
            return;
        }

        if (value instanceof short[] shorts) {
            JSONArray.writeJSONString(shorts, out);
            return;
        }

        if (value instanceof int[] ints) {
            JSONArray.writeJSONString(ints, out);
            return;
        }

        if (value instanceof long[] longs) {
            JSONArray.writeJSONString(longs, out);
            return;
        }

        if (value instanceof float[] floats) {
            JSONArray.writeJSONString(floats, out);
            return;
        }

        if (value instanceof double[] doubles) {
            JSONArray.writeJSONString(doubles, out);
            return;
        }

        if (value instanceof boolean[] booleans) {
            JSONArray.writeJSONString(booleans, out);
            return;
        }

        if (value instanceof char[] chars) {
            JSONArray.writeJSONString(chars, out);
            return;
        }

        if (value instanceof Object[] objects) {
            JSONArray.writeJSONString(objects, out);
            return;
        }

        out.write(value.toString());
    }

    /**
     * Convert an object to JSON text.
     * <p>
     * If this object is a Map or a List, and it's also a JSONAware, JSONAware will be considered firstly.
     * <p>
     * DO NOT call this method from toJSONString() of a class that implements both JSONAware and Map or List with
     * "this" as the parameter, use JSONObject.toJSONString(Map) or JSONArray.toJSONString(List) instead.
     *
     * @param value Value to represent as JSON string
     * @return JSON text, or "null" if value is null or it's an NaN or an INF number.
     * @see JSONObject#toJSONString(Map)
     * @see JSONArray#toJSONString(Collection)
     */
    @NotNull
    public static String toJSONString(@Nullable Object value) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(value, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     *
     * @param s {@link String} to be JSON-escaped
     * @return JSON-escaped string
     */
    @NotNull
    public static String escape(@Nullable String s) {
        if (s == null)
            return "null";
        StringBuilder sb = new StringBuilder();
        escape(s, sb);
        return sb.toString();
    }

    /**
     * @param s  - Must not be null.
     * @param sb {@link StringBuilder} to write escaped string to
     */
    static void escape(@NotNull String s, @NotNull StringBuilder sb) {
        final int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if (ch <= '\u001F' || ch >= '\u007F' && ch <= '\u009F' || ch >= '\u2000' && ch <= '\u20FF') {
                        String ss = Integer.toHexString(ch);
                        sb.append("\\u").append("0".repeat(4 - ss.length())).append(ss.toUpperCase());
                    } else
                        sb.append(ch);
            }
        }//for
    }

}
