package github.hotstu.ezuniplugin.base;


import android.app.Activity;
import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ServiceLoader;

import github.hotstu.ezuniplugin.base.compat.ReflectUtil;


/**
 * @author hglf
 * @desc
 * @since 8/19/19
 */
public class InitHook extends ContentProvider implements Application.ActivityLifecycleCallbacks {
    private final static String TAG = "InitHook";

    public InitHook() {
    }


    private void initFromRaw(Context context) {
        ServiceLoader<ILoader> starters = ServiceLoader.load(ILoader.class);
        Iterator<ILoader> it = starters.iterator();
        while (it.hasNext()) {
            try {
                ILoader next = it.next();
                next.init(context);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

    }




    private void initCallback(Context context) {
        try {
            ArrayList<Application.ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = (ArrayList<Application.ActivityLifecycleCallbacks>) ReflectUtil.getField(Application.class, context, "mActivityLifecycleCallbacks");
            mActivityLifecycleCallbacks.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreate() {
        //String packageName = getContext().getPackageName();
        //BaseInfo.uniVersionV3 = "2.7.14";
        EZApplication.sContext = getContext();
        initCallback(getContext());
        initFromRaw(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated:" + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed:" + activity.getClass().getSimpleName());
    }
}
