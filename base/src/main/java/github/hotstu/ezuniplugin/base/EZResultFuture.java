package github.hotstu.ezuniplugin.base;

import com.taobao.weex.bridge.JSCallback;

import java.util.HashMap;

public class EZResultFuture implements ResultFuture {
    private final JSCallback callbackId;

    public EZResultFuture(JSCallback callback) {
        this.callbackId = callback;
    }

    public Object resolve(Object raw) {
        if (this.callbackId != null) {
            CallbackInfo info = new CallbackInfo();
            info.data = raw;
            callbackId.invoke(info);
            return null;
        } else {
            return raw;
        }
    }


    public Object resolve(String key, Object value) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        String raw = GsonUtil.getInstance().toJson(map);
        return this.resolve(raw);
    }

    public Object reject(Object err) {
        if (this.callbackId != null) {
            CallbackInfo info = new CallbackInfo();
            info.error = err;
            this.callbackId.invoke(info);
            return null;
        } else {
            return err;
        }
    }
}