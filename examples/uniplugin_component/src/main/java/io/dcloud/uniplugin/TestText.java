package io.dcloud.uniplugin;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.util.HashMap;
import java.util.Map;

import github.hotstu.ezuniplugin.annotation.Module;
import github.hotstu.ezuniplugin.base.EZComponent;

@Module("myText")
public class TestText extends EZComponent<TextView> {

    public TestText(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected TextView initComponentHostView(Context context) {
        TextView textView = new TextView(context);
        textView.setTextSize(20);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    @WXComponentProp(name = "tel")
    public void setTel(String telNumber) {
        getHostView().setText("tel: " + telNumber);
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> number = new HashMap<>();
        number.put("tel", telNumber);
        //目前uni限制 参数需要放入到"detail"中 否则会被清理
        params.put("detail", number);
        fireEvent("onTel", params);
    }

    @JSMethod
    public void clearTel() {
        getHostView().setText("");
    }

    @Override
    public void onActivityResume() {
        super.onActivityResume();
    }

    @Override
    public void onActivityPause() {
        super.onActivityPause();
    }

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
    }
}
