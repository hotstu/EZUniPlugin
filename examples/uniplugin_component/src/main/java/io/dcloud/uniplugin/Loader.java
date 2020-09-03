package io.dcloud.uniplugin;

import android.content.Context;

import github.hotstu.ezuniplugin.annotation.ModuleEntry;
import github.hotstu.ezuniplugin.base.SimpleLoader;

/**
 * @author hglf [hglf](https://github.com/hotstu)
 * @desc
 * @since 9/3/20
 */
@ModuleEntry
public class Loader extends SimpleLoader {
    @Override
    public void init(Context context) {
        registerComponent(TestText.class);
    }
}
