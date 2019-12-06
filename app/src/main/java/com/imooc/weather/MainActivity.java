package com.imooc.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.imooc.weather.json.JsonData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitle;
    private Button mBtn;

    private SharedPreferences mPreferences;
    private OkHttpClient mOkHttpClient;
    private MyListView mListView;
    private TextView mTemp;
    private Toast mToast;
    private TextView mYl;

    @Override
    protected void onInitView(Bundle savedInsanceState) {
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        mTitle = findViewById(R.id.tv_title);
        mTitle.setText(city);
        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
        mYl = findViewById(R.id.tv_yl);
        mTemp = findViewById(R.id.temp);
        mListView = findViewById(R.id.listView);

        mOkHttpClient = new OkHttpClient();
        try {
            URL url = new URL("https://api.ixiaowai.cn/api/ylapi.php");
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("Weather_Error", "Main1: " + e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    final String str = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mYl.setText("一句话：\n" + str);
                        }
                    });
                }
            });
        } catch (Exception e){
            Log.e("Weather_Error", "Main2: " + e);
        }
        getWeather(city);
    }

    @Override
    public void onClick(View v) {
        final EditText inputServer = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请输入城市").setView(inputServer).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = inputServer.getText().toString().trim();
                getWeather(text);
            }
        });
        builder.show();
    }

    private void getWeather(final String city) {
        try {
            URL url = new URL("https://www.mxnzp.com/api/weather/current/" + URLEncoder.encode(city, "UTF-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e("Weather_Error", "Splash1: " + e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Gson gson = new Gson();
                    final JsonData jsonData = gson.fromJson(response.body().string(), JsonData.class);
                    if (jsonData.getCode() == 1) {
                        mPreferences = getSharedPreferences("city", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= mPreferences.edit();
                        editor.putString("city", city);
                        editor.commit();
                        runOnUiThread(new Runnable() {
                            private JsonData.DataBean dataBean;
                            @Override
                            public void run() {
                                mTitle.setText(city);
                                dataBean = jsonData.getData();
                                mTemp.setText(dataBean.getTemp());
                                mListView.setAdapter(new BaseAdapter() {
                                    @Override
                                    public int getCount() {
                                        return dataBean.getDatas().length;
                                    }

                                    @Override
                                    public Object getItem(int position) {
                                        return dataBean.getDatas()[position];
                                    }

                                    @Override
                                    public long getItemId(int position) {
                                        return position;
                                    }

                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        ViewHolder viewHolder = new ViewHolder();
                                        if (convertView == null) {
                                            convertView = View.inflate(MainActivity.this, R.layout.list_main, null);
                                            viewHolder.tv1 = convertView.findViewById(R.id.tv1);
                                            viewHolder.tv2 = convertView.findViewById(R.id.tv2);
                                            convertView.setTag(viewHolder);
                                        } else {
                                            viewHolder = (ViewHolder) convertView.getTag();
                                        }
                                        viewHolder.tv1.setText(dataBean.getIds()[position]);
                                        viewHolder.tv2.setText(dataBean.getDatas()[position]);
                                        return convertView;
                                    }

                                    class ViewHolder{
                                        public TextView tv1;
                                        public TextView tv2;
                                    }
                                });
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToast = Toast.makeText(MainActivity.this, null, Toast.LENGTH_SHORT);
                                mToast.setText("请求数据失败");
                                mToast.show();
                            }
                        });
                    }
                }
            });
        }catch (Exception e){
            Log.e("Weather_Error", "Splash2: " + e);
        }
    }
}
