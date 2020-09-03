package io.dcloud.uniplugin;


import android.content.Context;

import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;

import github.hotstu.ezuniplugin.annotation.ModuleEntry;
import github.hotstu.ezuniplugin.base.SimpleLoader;

@ModuleEntry
public class TestStartup extends SimpleLoader {
    @Override
    public void init(Context context) {
        try {
            WXSDKEngine.registerModule("TestModule", TestModule.class, false);
        } catch (WXException e) {
            e.printStackTrace();
        }
    }
}
