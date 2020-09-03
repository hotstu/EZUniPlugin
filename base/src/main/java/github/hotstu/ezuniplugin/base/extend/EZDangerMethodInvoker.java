package github.hotstu.ezuniplugin.base.extend;

import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.bridge.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import github.hotstu.ezuniplugin.base.EZResultFuture;
import github.hotstu.ezuniplugin.base.GsonUtil;
import github.hotstu.ezuniplugin.base.EZModule;

/**
 * @author hglf
 * @desc
 * @since 7/3/20
 */
public class EZDangerMethodInvoker extends MethodInvoker {
    final String[] mPermissions;
    int granted = -1;
    public EZDangerMethodInvoker(Method method, String[] permissions) {
        super(method, true);
        this.mPermissions = permissions;
    }

    @Override
    public Object invoke(Object receiver, Object... params) throws InvocationTargetException, IllegalAccessException {
        EZModule ezModule = (EZModule) receiver;
        if (granted == 1) {
            return super.invoke(receiver, params);
        }
        boolean hasPermissions = ezModule.hasPermissions(mPermissions);
        granted = hasPermissions ? 1 : 0;
        if (granted == 1) {
            return super.invoke(receiver, params);
        } else {
            if (params.length > 0) {
                for (int i = params.length - 1; i >= 0; i--) {
                    Object param = params[i];
                    if (param instanceof JSCallback) {
                        JSCallback callback = (JSCallback) param;
                        EZResultFuture future = new EZResultFuture(callback);
                        future.reject("不具备权限：" + GsonUtil.getInstance().toJson(mPermissions));
                        return null;
                    }
                }
            }
            return null;
        }
    }

    @Override
    public Type[] getParameterTypes() {
        return super.getParameterTypes();
    }

    @Override
    public boolean isRunOnUIThread() {
        return super.isRunOnUIThread();
    }
}
