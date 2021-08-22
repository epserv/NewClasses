package ru.epserv.epmodule.json.parser;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Container factory for creating containers for JSON object and JSON array.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 * @see org.json.simple.parser.JSONParser#parse(java.io.Reader, org.json.simple.parser.ContainerFactory)
 */
public interface ContainerFactory {
    /**
     * @return A Map instance to store JSON object, or null if you want to use org.json.simple.JSONObject.
     */
    @Nullable
    Map<Object, Object> createObjectContainer();

    /**
     * @return A List instance to store JSON array, or null if you want to use org.json.simple.JSONArray.
     */
    @Nullable
    List<?> createArrayContainer();
}
