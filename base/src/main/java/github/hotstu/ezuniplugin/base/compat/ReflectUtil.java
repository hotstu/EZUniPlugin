/*
 * Copyright (C) 2017 Beijing Didi Infinity Technology and Development Co.,Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.hotstu.ezuniplugin.base.compat;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Handler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("rawtypes")
public class ReflectUtil {

    public static Object sActivityThread;
    public static Object sLoadedApk;
    public static Instrumentation sInstrumentation;


    /**
     * 反射获取 field 的值
     * 会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @return Object
     * @throws Exception Exception
     */
    public static Object getField(Class clazz, Object target, String name) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }


    /**
     * 反射获取 field 的值
     * 不会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @return Object
     */
    public static Object getFieldNoException(Class clazz, Object target, String name) {
        try {
            return ReflectUtil.getField(clazz, target, name);
        } catch (Exception e) {
            //ignored.
        }

        return null;
    }


    /**
     * 反射设置 field 的值
     * 会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @param value value
     * @throws Exception Exception
     */
    public static void setField(Class clazz, Object target, String name, Object value)
        throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }


    /**
     * 反射设置 field 的值
     * 不会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @param value value
     * @throws Exception Exception
     */
    public static void setFieldNoException(Class clazz, Object target, String name, Object value) {
        try {
            ReflectUtil.setField(clazz, target, name, value);
        } catch (Exception e) {
            //ignored.
        }
    }


    /**
     * 反射调用 method
     * 会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @param args args
     * @return Object
     * @throws Exception Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object target, String name, Object... args)
        throws Exception {
        Class[] parameterTypes = null;
        if (args != null) {
            parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }

        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }


    /**
     * 反射调用 method
     * 会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @param parameterTypes parameterTypes
     * @param args args
     * @return Object
     * @throws Exception Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object target, String name, Class[] parameterTypes, Object... args)
        throws Exception {
        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }


    /**
     * 反射调用 method
     * 不会抛异常
     *
     * @param clazz clazz
     * @param target target
     * @param name name
     * @param parameterTypes parameterTypes
     * @param args args
     * @return Object
     */
    @SuppressWarnings("unchecked")
    public static Object invokeNoException(Class clazz, Object target, String name, Class[] parameterTypes, Object... args) {
        try {
            return invoke(clazz, target, name, parameterTypes, args);
        } catch (Exception e) {
        }

        return null;
    }


    /**
     * 反射调用 构造方法
     * 会抛异常
     *
     * @param clazz clazz
     * @param parameterTypes parameterTypes
     * @param args args
     * @return Object
     * @throws Exception Exception
     */
    @SuppressWarnings("unchecked")
    public static Object invokeConstructor(Class clazz, Class[] parameterTypes, Object... args)
        throws Exception {
        Constructor constructor = clazz.getDeclaredConstructor(parameterTypes);
        constructor.setAccessible(true);
        return constructor.newInstance(args);
    }


    /**
     * 反射获取 ActivityThread
     *
     * 两个尝试点
     *
     * ActivityThread # ActivityThread sCurrentActivityThread
     * ActivityThread # ThreadLocal<ActivityThread> sThreadLocal
     *
     * @param base base
     * @return Object
     */
    public static Object getActivityThread(Context base) {
        if (sActivityThread == null) {
            try {
                Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
                Object activityThread = null;
                try {
                    activityThread = ReflectUtil.getField(activityThreadClazz, null,
                        "sCurrentActivityThread");
                } catch (Exception e) {
                    // ignored
                }
                if (activityThread == null) {
                    activityThread = ((ThreadLocal<?>) ReflectUtil.getField(activityThreadClazz,
                        null, "sThreadLocal")).get();
                }
                sActivityThread = activityThread;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sActivityThread;
    }


    /**
     * 反射获取 Instrumentation
     *
     * ActivityThread # Instrumentation getInstrumentation()
     *
     * @param base base
     * @return Instrumentation
     */
    public static Instrumentation getInstrumentation(Context base) {
        if (getActivityThread(base) != null) {
            try {
                sInstrumentation = (Instrumentation) ReflectUtil.invoke(
                    sActivityThread.getClass(), sActivityThread, "getInstrumentation");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sInstrumentation;
    }


    /**
     * 反射设置 Instrumentation
     *
     * ActivityThread # Instrumentation mInstrumentation
     *
     * @param activityThread activityThread
     * @param instrumentation instrumentation
     */
    public static void setInstrumentation(Object activityThread, Instrumentation instrumentation) {
        try {
            ReflectUtil.setField(activityThread.getClass(), activityThread, "mInstrumentation",
                instrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 反射获取 LoadedApk
     *
     * ContextImpl # @NonNull LoadedApk mPackageInfo
     *
     * @param base base
     * @return Object
     */
    public static Object getPackageInfo(Context base) {
        if (sLoadedApk == null) {
            try {
                sLoadedApk = ReflectUtil.getField(base.getClass(), base, "mPackageInfo");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sLoadedApk;
    }


    /**
     * 反射设置 Handler # Handler.Callback mCallback
     *
     * 主要为了 hook ActivityThread # H # Handler.Callback mCallback
     *
     * @param base base
     * @param callback callback
     */
    public static void setHandlerCallback(Context base, Handler.Callback callback) {
        try {
            Object activityThread = getActivityThread(base);
            Handler mainHandler = (Handler) ReflectUtil.invoke(activityThread.getClass(),
                activityThread, "getHandler", (Object[]) null);
            ReflectUtil.setField(Handler.class, mainHandler, "mCallback", callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
