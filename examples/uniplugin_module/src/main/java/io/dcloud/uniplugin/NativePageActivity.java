package io.dcloud.uniplugin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NativePageActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout rootView = new FrameLayout(this);
        TextView textView = new TextView(this);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        textView.setText("点击我将返回 并携带参数返回");
        rootView.addView(textView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("respond", "我是原生页面");
                setResult(TestModule.REQUEST_CODE, intent);
                finish();
            }
        });
        setContentView(rootView);
    }
}
