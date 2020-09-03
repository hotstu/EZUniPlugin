package github.hotstu.ezuniplugin.base;

import android.content.Context;
import android.text.TextUtils;

import github.hotstu.ezuniplugin.base.compat.ReflectUtil;

/**
 * @author hglf
 * @desc
 * @since 7/1/20
 */
public class EZApplication {
    static Context sContext;
    private static String applicationId;
    private static Class<?> buildconfigClass;

    public static Context getContext() {
        return sContext;
    }

    public static Class<?> getAppBuildConfigClass() {
        if (buildconfigClass == null) {
            try {
                buildconfigClass = Class.forName(sContext.getPackageName()+ ".BuildConfig");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return buildconfigClass;
    }

    public static String  getApplicationId() {
        if (TextUtils.isEmpty(applicationId)) {
            try {
                applicationId = (String) ReflectUtil.getField(getAppBuildConfigClass(),
                        getAppBuildConfigClass(), "APPLICATION_ID");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return applicationId;
    }
}
