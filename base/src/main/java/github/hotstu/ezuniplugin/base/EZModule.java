package github.hotstu.ezuniplugin.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.taobao.weex.WXSDKEngine;

import java.util.Map;


public abstract class EZModule extends WXSDKEngine.DestroyableModule {
    public EZModule() {
    }

    public Context getContext() {
        return mWXSDKInstance.getContext();
    }

    public void startActivityForResult(Intent intent, int code) {
        ((Activity)mWXSDKInstance.getContext()).startActivityForResult(intent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void fireEvent(String event, Map<String, Object> params) {
        this.mWXSDKInstance.fireModuleEvent(event, this, params);
    }


    public boolean hasPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(getContext(),permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}

