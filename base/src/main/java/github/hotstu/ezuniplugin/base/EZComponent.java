package github.hotstu.ezuniplugin.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXVContainer;

public abstract class EZComponent<T extends View> extends WXComponent<T> {
    public EZComponent(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected T initComponentHostView(@NonNull Context context) {
        return super.initComponentHostView(context);
    }


}
