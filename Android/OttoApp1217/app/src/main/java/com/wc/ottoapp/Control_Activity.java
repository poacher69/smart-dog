package com.wc.ottoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Control_Activity extends Activity {
    private ImageButton btn_swim, btn_sitdown, btn_standup, btn_pee, btn_attention;
    private WebView webView, webView1;
    private RelativeLayout rl;
    private String ip_address, control_web;
//    private float downX, downY, upX, upY;
//    private boolean touchchk;
    private TextView tv_time, tv_temp, tv_humi, tv_dist, tv_ip;

    DatabaseReference myRef;

    private Handler mHandler = new Handler();
    private Runnable mSwimRunnable = new Runnable() {
        @Override
        public void run() {
            control_web = "http://" + ip_address + ":5000/?control=swim";
            webView1.loadUrl(control_web);

            Log.d("QQQ",control_web);
            mHandler.postDelayed(this,2000);
        }
    };
    private Runnable mSitdownRunnable = new Runnable() {
        @Override
        public void run() {
            control_web = "http://" + ip_address + ":5000/?control=sitdown";
            webView1.loadUrl(control_web);

            Log.d("QQQ",control_web);
            mHandler.postDelayed(this,2000);
        }
    };
    private Runnable mStandupRunnable = new Runnable() {
        @Override
        public void run() {
            control_web = "http://" + ip_address + ":5000/?control=standup";
            webView1.loadUrl(control_web);

            Log.d("QQQ",control_web);
            mHandler.postDelayed(this,2000);
        }
    };
    private Runnable mPeeRunnable = new Runnable() {
        @Override
        public void run() {
            control_web = "http://" + ip_address + ":5000/?control=WC";
            webView1.loadUrl(control_web);

            Log.d("QQQ",control_web);
            mHandler.postDelayed(this,2000);
        }
    };
    private Runnable mAttentionRunnable = new Runnable() {
        @Override
        public void run() {
            control_web = "http://" + ip_address + ":5000/?control=attention";
            webView1.loadUrl(control_web);

            Log.d("QQQ",control_web);
            mHandler.postDelayed(this,2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隱藏ActionBar方法2，注意extends Activity，而非AppCompatActivity，因為AppCompatActivity上方的父類別有ActionBar有ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control_);



        //抓firebase溫濕度數據
        myRef = FirebaseDatabase.getInstance().getReference("TH");

        //方向四鍵
        btn_swim = (ImageButton) findViewById(R.id.imageButton_swim);
        btn_sitdown = (ImageButton) findViewById(R.id.imageButton_sitdown);
        btn_standup = (ImageButton) findViewById(R.id.imageButton_stand);
        btn_pee = (ImageButton) findViewById(R.id.imageButton_pee);
        btn_attention = (ImageButton) findViewById(R.id.imageButton_attention);

        tv_time = (TextView) findViewById(R.id.textView_time);
        tv_temp = (TextView) findViewById(R.id.textView_temp);
        tv_humi = (TextView) findViewById(R.id.textView_humidity);
        tv_dist = (TextView) findViewById(R.id.textView_dis);
        tv_ip = (TextView) findViewById(R.id.textView_address);

        Intent intent = getIntent();
        ip_address = intent.getStringExtra("ip");
        tv_ip.setText("IP : " + ip_address);

        //webView顯示影像
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
//        webView.loadUrl("http://192.168.0.1:5000");
//        webView.loadUrl("https://ottoapp-ea5ec.firebaseio.com/");
        webView.loadUrl("http://"+ip_address+":8080/?action=streamer");

        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        /*
第一种方法：
WebSettings settings = webView.getSettings();
settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
LayoutAlgorithm是一个枚举用来控制页面的布局，有三个类型：
1.NARROW_COLUMNS：可能的话使所有列的宽度不超过屏幕宽度
2.NORMAL：正常显示不做任何渲染
3.SINGLE_COLUMN：把所有内容放大webview等宽的一列中
用SINGLE_COLUMN类型可以设置页面居中显示，页面可以放大缩小，但这种方法不怎么好，有时候会让你的页面布局走样而且我测了一下，只能显示中间那一块，超出屏幕的部分都不能显示。

第二种方法：
//设置加载进来的页面自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
第一个方法设置webview推荐使用的窗口，设置为true。第二个方法是设置webview加载的页面的模式，也设置为true。
这方法可以让你的页面适应手机屏幕的分辨率，完整的显示在屏幕上，可以放大缩小。
两种方法都试过，推荐使用第二种方法

         */

        //webView1連結操控http...get
        webView1 = new WebView(this);

        //監聽imagebutton -- 作用-按鍵操控
//        btn_top.setOnClickListener(this);
//        btn_down.setOnClickListener(this);
//        btn_left.setOnClickListener(this);
//        btn_right.setOnClickListener(this);

        btn_swim.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSwimRunnable.run();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mSwimRunnable);

                        Log.d("QQQ","Exit mSwimRunnalbe");
                        return true;
                }
                return false;
            }
        });

        btn_sitdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mSitdownRunnable.run();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mSitdownRunnable);

                        Log.d("QQQ","Exit mSitdownRunnalbe");
                        return true;
                }
                return false;
            }
        });

        btn_standup.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStandupRunnable.run();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mStandupRunnable);

                        Log.d("QQQ","Exit mStandupRunnalbe");
                        return true;
                }
                return false;
            }
        });

        btn_pee.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPeeRunnable.run();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mPeeRunnable);

                        Log.d("QQQ","Exit mPeeRunnalbe");
                        return true;
                }
                return false;
            }
        });

        btn_attention.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAttentionRunnable.run();
                        return true;
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAttentionRunnable);

                        Log.d("QQQ","Exit mAttentionRunnalbe");
                        return true;
                }
                return false;
            }
        });

        //監聽觸控 -- 作用-滑動操控
//        rl.setOnTouchListener(this);
        //監聽firebase有無新增數據
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getInformation info = dataSnapshot.getValue(getInformation.class);
                String str_time = (String) info.getDate();
                float temp =(float) info.getTemperature();
                float humi =(float) info.getHumidity();
                float dist =(float) info.getDistance();

                tv_time.setText(str_time);
                tv_temp.setText("溫度 : " + temp + "%");
                tv_humi.setText("濕度 : " + humi + "%");
                tv_dist.setText("距離 : " + dist + " cm");
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getInformation info = dataSnapshot.getValue(getInformation.class);
                String str_time = (String) info.getDate();
                float temp =(float) info.getTemperature();
                float humi =(float) info.getHumidity();
                float dist =(float) info.getDistance();

                tv_time.setText(str_time);
                tv_temp.setText("溫度 : " + temp + "%");
                tv_humi.setText("濕度 : " + humi + "%");
                tv_dist.setText("距離 : " + dist + " cm");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // 判斷觸控的動作
//        switch (event.getAction()) {
//            // 按下
//            case MotionEvent.ACTION_DOWN:
//                downX = event.getX();  // 觸控的 X 軸位置
//                downY = event.getY();  // 觸控的 Y 軸位置
//                touchchk = true;
//                return true;
//            // 拖曳
//            case MotionEvent.ACTION_MOVE:
//                upX = event.getX();
//                upY = event.getY();
//                float x = Math.abs(upX - downX);
//                float y = Math.abs(upY - downY);
//                double z = Math.sqrt(x * x + y * y);
//                int jiaodu = Math.round((float) (Math.asin(y / z) / Math.PI * 180));//計算角度
//
//                Log.d("BBB", String.valueOf(x) + "\t" + String.valueOf((downX - upX)));
//
//                //輸出"上"
//                if (((downY - upY) > 150) && jiaodu > 45 && touchchk) {
//                    control_web = "http://" + ip_address + ":5000/?control=front";
//                    webView1.loadUrl(control_web);
//                    touchchk = false;
//
//                    Log.d("BBB",control_web);
//                }
//                //輸出"下"
//                else if (((downY - upY) < -150) && jiaodu > 45 && touchchk) {
//                    control_web = "http://" + ip_address + ":5000/?control=back";
//                    webView1.loadUrl(control_web);
//                    touchchk = false;
//
//                    Log.d("BBB",control_web);
//                }
//                //輸出"左"
//                else if (((downX - upX) > 150) && jiaodu <= 45 && touchchk) {
//                    control_web = "http://" + ip_address + ":5000/?control=left";
//                    webView1.loadUrl(control_web);
//                    touchchk = false;
//
//                    Log.d("BBB",control_web);
//                }
//                //輸出"右"
//                else if (((downX - upX) < -150) && jiaodu <= 45 && touchchk) {
//                    control_web = "http://" + ip_address + ":5000/?control=right";
//                    webView1.loadUrl(control_web);
//                    touchchk = false;
//
//                    Log.d("BBB",control_web);
//                }
//                return true;
//            // 手指放開時，輸出STOP指令
//            case MotionEvent.ACTION_UP:
////                control_web = "http://" + ip_address + ":5000/?control=stop";
////                webView1.loadUrl(control_web);
//                touchchk = false;
//
//                Log.d("BBB","Action_UP");
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }

}