package github.hotstu.ezuniplugin.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author hglf
 * @desc
 * @since 7/1/20
 */
public class GsonUtil {
    private static Gson gson;

    private GsonUtil() {
    }

    public static Gson getInstance() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    gson = new GsonBuilder().create();
                }
            }
        }
        return gson;
    }
}
