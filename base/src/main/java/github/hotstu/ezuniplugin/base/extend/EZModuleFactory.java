package github.hotstu.ezuniplugin.base.extend;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.Invoker;
import com.taobao.weex.bridge.MethodInvoker;
import com.taobao.weex.common.TypeModuleFactory;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.ui.config.ConfigModuleFactory;
import com.taobao.weex.utils.WXLogUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import github.hotstu.ezuniplugin.annotation.WithPermission;
import github.hotstu.ezuniplugin.base.compat.ReflectUtil;

/**
 * 继承ConfigModuleFactory,weex会在buildInstance的时候带入WXSDKInstance, 实际上委托给TypeModuleFactory
 * @param <T>
 */
public class EZModuleFactory<T extends WXModule> extends ConfigModuleFactory<T> {

    private final TypeModuleFactory<T> typeModuleFactory;
    private final Class<T> mClazz;
    Map<String, Invoker> mMethodMap;

    public EZModuleFactory(Class<T> clazz) {
        super(null, null, null);
        mClazz = clazz;
        typeModuleFactory = new TypeModuleFactory<>(clazz);
    }

    public String[] getMethods() {
        return typeModuleFactory.getMethods();
    }

    public Invoker getMethodInvoker(String name) {
        if (this.mMethodMap == null) {
            this.generateMethodMap();
        }

        return (Invoker)this.mMethodMap.get(name);
    }

    public T buildInstance() throws InstantiationException, IllegalAccessException {
        return (T) typeModuleFactory.buildInstance();
    }

    public T buildInstance(WXSDKInstance instance) throws InstantiationException, IllegalAccessException {
        try {
            ReflectUtil.setField(WXSDKInstance.class, instance, "mNativeInvokeHelper",
                    new EZNativeInvokerHelper(instance.getInstanceId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) typeModuleFactory.buildInstance();

//        try {
//            return (T) ReflectUtil.invokeConstructor(mClazz, new Class[]{});
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public String getName() {
        return null;
    }

    private void generateMethodMap() {
        HashMap methodMap = new HashMap();
        Class<WXModule> wxModuleClass = WXModule.class;
        try {
            Method[] methods = this.mClazz.getDeclaredMethods();
            Method[] methods2 = wxModuleClass.getDeclaredMethods();
            Method[] mergedMethods = new Method[methods.length + methods2.length];
            System.arraycopy(methods, 0, mergedMethods, 0, methods.length);
            System.arraycopy(methods2, 0, mergedMethods, methods.length, methods2.length);
            int length = mergedMethods.length;

            for(int i = 0; i < length; ++i) {
                Method method = mergedMethods[i];
                JSMethod annotation = method.getAnnotation(JSMethod.class);
                WithPermission withPermission = method.getAnnotation(WithPermission.class);
                if (annotation != null) {
                    String name = "_".equals(annotation.alias()) ? method.getName() : annotation.alias();
                    if (withPermission != null && withPermission.value().length > 0) {
                        if (!annotation.uiThread()) {
                            throw new IllegalArgumentException("使用权限控制必须在ui线程");
                        }
                        methodMap.put(name, new EZDangerMethodInvoker(method, withPermission.value()));

                    } else {
                        methodMap.put(name, new MethodInvoker(method, annotation.uiThread()));
                    }
                }

            }
        } catch (Throwable var12) {
            WXLogUtils.e("[WXModuleManager] extractMethodNames:", var12);
        }

        this.mMethodMap = methodMap;
    }
}
