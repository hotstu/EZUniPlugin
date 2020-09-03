package uni.dcloud.io.uniplugin_richalert;

import android.content.Context;

import github.hotstu.ezuniplugin.annotation.ModuleEntry;
import github.hotstu.ezuniplugin.base.SimpleLoader;

@ModuleEntry
public class RichAlert_AppProxy extends SimpleLoader {

    @Override
    public void init(Context context) {
        registerModule(RichAlertWXModule.class);
    }
}
