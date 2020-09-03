package github.hotstu.ezuniplugin.base.extend;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.taobao.weex.bridge.Invoker;
import com.taobao.weex.bridge.NativeInvokeHelper;

import java.lang.reflect.Type;

/**
 * @author hglf
 * @desc
 * @since 7/6/20
 */
public class EZNativeInvokerHelper extends NativeInvokeHelper {
    public EZNativeInvokerHelper(String instanceId) {
        super(instanceId);
    }

    @Override
    public Object invoke(Object target, Invoker invoker, JSONArray args) throws Exception {
        Log.d("InvokeHook", "" + target + invoker+ args);
        Object invoke = super.invoke(target, invoker, args);
        return invoke;
    }

    @Override
    protected Object[] prepareArguments(Type[] paramClazzs, JSONArray args) throws Exception {
        return super.prepareArguments(paramClazzs, args);
    }
}
