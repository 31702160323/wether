package com.imooc.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.imooc.weather.utils.FrameApplication;
import com.imooc.weather.utils.StatusBarUtil;

public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this);
        //构建View,绑定事件
        this.onInitView(savedInstanceState);
        FrameApplication.addToActivityList(this);
    }

    @Override
    protected void onDestroy() {
        FrameApplication.addToActivityList(this);
        super.onDestroy();
    }

    protected abstract void onInitView(final Bundle savedInsanceState);
}
