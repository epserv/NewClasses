package ru.epserv.epmodule.json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;

/**
 * Beans that support customized output of JSON text to a writer shall implement this interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public interface JSONStreamAware {
    /**
     * write JSON string to out.
     */
    void writeJSONString(@NotNull Writer out) throws IOException;
}
