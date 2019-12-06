package com.imooc.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imooc.weather.json.JsonData;
import com.imooc.weather.utils.FrameApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Utf8;

public class SplashActivity extends BaseActivity {

    private SharedPreferences mPreferences;
    private Intent mIntent;

    @Override
    protected void onInitView(Bundle savedInsanceState) {
        setContentView(R.layout.activity_splash);
        mPreferences = getSharedPreferences("city", Context.MODE_PRIVATE);
        String city = mPreferences.getString("city", null);
        mIntent = new Intent(SplashActivity.this,MainActivity.class);
        if (city == null){
            final EditText inputServer = new EditText(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请输入城市").setView(inputServer).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    FrameApplication.exitApp();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String text = inputServer.getText().toString().trim();
                    mIntent.putExtra("city", text);
                    SharedPreferences.Editor editor= mPreferences.edit();
                    editor.putString("city", text);
                    editor.commit();
                    startActivity(mIntent);
                    finish();
                }
            });
            builder.show();
        } else {
            mIntent.putExtra("city", city);
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    startActivity(mIntent);
                    finish();
                }
            };
            timer.schedule(timerTask, 500);
        }
    }
}
