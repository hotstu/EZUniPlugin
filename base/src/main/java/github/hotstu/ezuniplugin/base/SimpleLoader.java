package github.hotstu.ezuniplugin.base;


import android.text.TextUtils;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import github.hotstu.ezuniplugin.annotation.Module;
import github.hotstu.ezuniplugin.base.extend.EZModuleFactory;


public abstract class SimpleLoader implements ILoader {
    protected boolean registerModule(Class<? extends EZModule> moduleClazz) {
        Module annotation = moduleClazz.getAnnotation(Module.class);
        if (annotation == null) {
            throw new RuntimeException("模块未注册:Module注解class中");
        }
        String name = annotation.value();
        if (TextUtils.isEmpty(name)) {
            name = moduleClazz.getSimpleName();
        }
        try {
            WXSDKEngine.registerModule(name, new EZModuleFactory<>(moduleClazz), false);
        } catch (WXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean registerComponent(Class<? extends EZComponent<?>> componentClazz) {
        Module annotation = componentClazz.getAnnotation(Module.class);
        if (annotation == null) {
            throw new RuntimeException("模块未注册:Module注解class中");
        }
        String name = annotation.value();
        if (TextUtils.isEmpty(name)) {
            name = componentClazz.getSimpleName();
        }
        try {
            WXSDKEngine.registerComponent(name, componentClazz);
        } catch (WXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
