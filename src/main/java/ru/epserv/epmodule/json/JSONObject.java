/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package ru.epserv.epmodule.json;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Serial;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class JSONObject extends HashMap<Object, Object> implements Map<Object, Object>, JSONAware, JSONStreamAware {

    @Serial
    private static final long serialVersionUID = -503443796854799292L;

    public JSONObject() {
        super();
    }

    /**
     * Allows creation of a JSONObject from a Map. After that, both the
     * generated JSONObject and the Map can be modified independently.
     *
     * @param map Map to use as source
     */
    public JSONObject(@NotNull Map<?, ?> map) {
        super(map);
    }


    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     *
     * @param map {@link Map} to use as input
     * @param out {@link Writer} to use as output
     * @see JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(@Nullable Map<?, ?> map, @NotNull Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator<? extends Entry<?, ?>> iterator = map.entrySet().iterator();

        out.write('{');
        while (iterator.hasNext()) {
            if (first)
                first = false;
            else
                out.write(',');
            Entry<?, ?> entry = iterator.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write('}');
    }

    /**
     * Convert a map to JSON text. The result is a JSON object.
     * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     *
     * @param map {@link Map} to use as input
     * @return JSON text, or "null" if map is null.
     * @see JSONValue#toJSONString(Object)
     */
    @NotNull
    public static String toJSONString(@Nullable Map<Object, Object> map) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(map, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen with a StringWriter
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public static String toString(@Nullable String key, @Nullable Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        if (key == null)
            sb.append("null");
        else
            JSONValue.escape(key, sb);
        sb.append('\"').append(':');

        sb.append(JSONValue.toJSONString(value));

        return sb.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * It's the same as JSONValue.escape() only for compatibility here.
     *
     * @param s {@link String} to escape
     * @return JSON-escaped {@link String}
     * @see JSONValue#escape(String)
     */
    @NotNull
    public static String escape(@Nullable String s) {
        return JSONValue.escape(s);
    }

    public void writeJSONString(@NotNull Writer out) throws IOException {
        writeJSONString(this, out);
    }

    public String toJSONString() {
        return toJSONString(this);
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Nullable
    public <T> T get(@NotNull Object key, @NotNull Class<T> clazz) {
        return clazz.cast(this.get(key, clazz.cast(null)));
    }

    @Nullable
    @Contract("_, !null -> !null")
    public Object get(@NotNull Object key, @Nullable Object def) {
        Object value = this.get(key);
        return value == null ? def : value;
    }

    @NotNull
    public String getString(@NotNull String key) {
        return string(this.get(key));
    }

    public boolean getBoolean(@NotNull String key) {
        return this.getBoolean(key, false);
    }

    public byte getByte(@NotNull String key) {
        return Byte.parseByte(this.getString(key));
    }

    public short getShort(@NotNull String key) {
        return Short.parseShort(this.getString(key));
    }

    public int getInt(@NotNull String key) {
        return Integer.parseInt(this.getString(key));
    }

    public long getLong(@NotNull String key) {
        return Long.parseLong(this.getString(key));
    }

    public float getFloat(@NotNull String key) {
        return Float.parseFloat(this.getString(key));
    }

    public double getDouble(@NotNull String key) {
        return Double.parseDouble(this.getString(key));
    }


    @Contract("_, !null -> !null")
    public String getString(@NotNull String key, @Nullable Object def) {
        return String.valueOf(this.getOrDefault(key, def));
    }

    public boolean getBoolean(@NotNull String key, boolean def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Boolean b ? b : Boolean.parseBoolean(string(value));
    }

    public byte getByte(@NotNull String key, byte def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Byte b ? b : Byte.parseByte(string(value));
    }

    public short getShort(@NotNull String key, short def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Short s ? s : Short.parseShort(string(value));
    }

    public int getInt(@NotNull String key, int def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Integer i ? i : Integer.parseInt(string(value));
    }

    public long getLong(@NotNull String key, long def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Long l ? l : Long.parseLong(string(value));
    }

    public float getFloat(@NotNull String key, float def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Float f ? f : Float.parseFloat(string(value));
    }

    public double getDouble(@NotNull String key, double def) {
        Object value = this.getOrDefault(key, def);
        return value instanceof Double d ? d : Double.parseDouble(string(value));
    }

    @Nullable
    public JSONObject child(@NotNull String key) {
        return (JSONObject) this.get(key);
    }

    @Contract("_, !null -> !null")
    public JSONObject child(@NotNull String key, @Nullable JSONObject def) {
        return (JSONObject) Objects.requireNonNullElse(this.get(key), def);
    }

    @Nullable
    public JSONArray array(@NotNull String key) {
        return (JSONArray) this.get(key);
    }

    @Contract("_, !null -> !null")
    public JSONArray array(@NotNull String key, @Nullable JSONArray def) {
        return (JSONArray) Objects.requireNonNullElse(this.get(key), def);
    }

    public boolean isNull(@NotNull String key) {
        return this.get(key) == null;
    }

    @NotNull
    private String string(@Nullable Object object) {
        return Objects.requireNonNullElse(String.valueOf(object), "null");
    }

}
