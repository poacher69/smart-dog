package com.wc.ottoapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import static android.Manifest.permission.*;

import java.util.List;

public class SpeechActivity extends AppCompatActivity {

    private static final int REQUEST_AUDIO = 1;
    private ImageView img_swim,img_standup,img_sitdown,img_pee,img_attention;
    private Context context;
    private MediaPlayer mp = new MediaPlayer();
    //    private TextToSpeech tts;

    private SpeechRecognizer recognizer;
    Intent intent;
    final Handler handler = new Handler();
    private TextView tv_ip, tv_sp;
    private String ip_address;
    private WebView webView1;
    private String control_web;
    private ImageButton button_mic;
    private boolean spFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{RECORD_AUDIO},
                    REQUEST_AUDIO);
        }

        context = this;

        img_swim = (ImageView) findViewById(R.id.imageView_swim);
        img_sitdown = (ImageView) findViewById(R.id.imageView_sitdown);
        img_standup = (ImageView) findViewById(R.id.imageView_stand);
        img_pee = (ImageView) findViewById(R.id.imageView_pee);
        img_attention = (ImageView) findViewById(R.id.imageView_attention);
        button_mic = (ImageButton) findViewById(R.id.imageButton_mic);
        tv_ip = (TextView) findViewById(R.id.textView_speechIP);
        tv_sp = (TextView) findViewById(R.id.textView_sp);

        webView1 = new WebView(this);

        setTitle("Speech Mode");

        Intent data = getIntent();
        ip_address = data.getStringExtra("ip");
        tv_ip.setText("IP : " + ip_address);

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizer = SpeechRecognizer.createSpeechRecognizer(context);
        recognizer.setRecognitionListener(new MyRecognizerListener());
        recognizer.startListening(intent);

        button_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.startListening(intent);
            }
        });

//        createLanguageTTS();
//        tts.setLanguage(Locale.CHINESE);

    } //end of onCreate

    private class MyRecognizerListener implements RecognitionListener {
        @Override
        public void onReadyForSpeech(Bundle params) {
        }

        @Override
        public void onBeginningOfSpeech() {
        }

        @Override
        public void onRmsChanged(float rmsdB) {
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
        }

        @Override
        public void onEndOfSpeech() {
            //用handler製造delay效果
            //在上面宣告final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    //結束至onResults的時間差不多0.5秒左右，所以需延遲0.5秒以上再執行recognizer.startListening(intent);才可執行
                    //參考資料http://rach-chen.logdown.com/posts/829008-android-endless-conversations-listening-in-silence-he
                    Log.d("RECOGNIZER", "done");
                    recognizer.startListening(intent);  //使語音辨識不斷執行
                }
            }, 1000);  //延遲1秒
        }

        @Override
        public void onError(int error) {
            Log.d("RECOGNIZER", "Error Code: " + error);
        }

        @Override
        public void onResults(Bundle results) {
            List resList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            StringBuffer sb = new StringBuffer();
            for (Object res : resList) {
                sb.append(res + "\n");
            }
            spFlag = false;
            tv_sp.setText("");


            boolean status1 =false;
            if (sb.toString().contains("前進")||sb.toString().contains("go"))
                status1=true;
            if (status1) {
                control_web = "http://" + ip_address + ":5000/?control=swim";
                webView1.loadUrl(control_web);
                spFlag = true;
                tv_sp.setText("前進\ngo");
                img_swim.setVisibility(View.VISIBLE);
                img_sitdown.setVisibility(View.INVISIBLE);
                img_standup.setVisibility(View.INVISIBLE);
                img_pee.setVisibility(View.INVISIBLE);
                img_attention.setVisibility(View.INVISIBLE);
                Log.d("QQ",sb.toString());
            }
            boolean status2 = false;
            if (sb.toString().contains("坐下")||sb.toString().contains("sit down")||sb.toString().contains("飛彈"))
                status2=true;
            if (status2) {
                control_web = "http://" + ip_address + ":5000/?control=sitdown";
                webView1.loadUrl(control_web);
                spFlag = true;
                tv_sp.setText("坐下\nsit down");
                img_swim.setVisibility(View.INVISIBLE);
                img_sitdown.setVisibility(View.VISIBLE);
                img_standup.setVisibility(View.INVISIBLE);
                img_pee.setVisibility(View.INVISIBLE);
                img_attention.setVisibility(View.INVISIBLE);
                Log.d("QQ",sb.toString());
            }
            boolean status3 = false;
            if (sb.toString().contains("起立")||sb.toString().contains("stand up"))
                status3=true;

            if (status3) {
                control_web = "http://" + ip_address + ":5000/?control=standup";
                webView1.loadUrl(control_web);
                spFlag = true;
                tv_sp.setText("起立\nstand up");
                img_swim.setVisibility(View.INVISIBLE);
                img_sitdown.setVisibility(View.INVISIBLE);
                img_standup.setVisibility(View.VISIBLE);
                img_pee.setVisibility(View.INVISIBLE);
                img_attention.setVisibility(View.INVISIBLE);
                Log.d("QQ",sb.toString());
            }
            boolean status4 = false;
            if (sb.toString().contains("廁所")||sb.toString().contains("WC"))
                status4=true;
            if (status4) {
                control_web = "http://" + ip_address + ":5000/?control=WC";
                webView1.loadUrl(control_web);
                spFlag = true;
                tv_sp.setText("廁所\nWC");
                img_swim.setVisibility(View.INVISIBLE);
                img_sitdown.setVisibility(View.INVISIBLE);
                img_standup.setVisibility(View.INVISIBLE);
                img_pee.setVisibility(View.VISIBLE);
                img_attention.setVisibility(View.INVISIBLE);
                Log.d("QQ",sb.toString());
            }
            Log.d("RECOGNIZER", "onResults: " + sb.toString());
            boolean status5 = false;
            if (sb.toString().contains("立正")||sb.toString().contains("attention"))
                status5=true;
            if (status5) {
                control_web = "http://" + ip_address + ":5000/?control=attention";
                webView1.loadUrl(control_web);
                spFlag = true;
                tv_sp.setText("立正\nattention");
                img_swim.setVisibility(View.INVISIBLE);
                img_sitdown.setVisibility(View.INVISIBLE);
                img_standup.setVisibility(View.INVISIBLE);
                img_pee.setVisibility(View.INVISIBLE);
                img_attention.setVisibility(View.VISIBLE);
                Log.d("QQ",sb.toString());
            }
            Log.d("QQ", "onResults: " + sb.toString());

            if (spFlag == false) {
                control_web = "http://" + ip_address + ":5000/?control=null";
                webView1.loadUrl(control_web);
                tv_sp.setText("Result : \n"+ sb);
                img_swim.setVisibility(View.INVISIBLE);
                img_sitdown.setVisibility(View.INVISIBLE);
                img_standup.setVisibility(View.INVISIBLE);
                img_pee.setVisibility(View.INVISIBLE);
                img_attention.setVisibility(View.INVISIBLE);

                Log.d("QQ","onResults:" + sb.toString());
                Toast.makeText(context,"學習中...",Toast.LENGTH_SHORT).show();

                mp = MediaPlayer.create(context,R.raw.voice);
                mp.start();
            }
            Log.d("QQ", "onResults: " + sb.toString());
        }

        @Override
        public void onPartialResults(Bundle partialResults) {
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
        }
    }

//    private void createLanguageTTS() {
//
//        if (tts == null) {
//            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//                @Override
//                public void onInit(int arg0) {
//                    // TTS 初始化成功
//                    if (arg0 == TextToSpeech.SUCCESS) {
//                        // 目前指定的【語系+國家】TTS, 已下載離線語音檔, 可以離線發音
//                        if (tts.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
//                            tts.setLanguage(Locale.CHINESE);    //中文
//                        }
//                    }
//                }
//            });
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_AUDIO:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{
                    new AlertDialog.Builder(this).setMessage("必須允許權限")
                            .setPositiveButton("OK",null)
                            .show();
                }

        }

    }

    @Override
    protected void onDestroy() {
        recognizer.stopListening();
        recognizer.destroy();
        super.onDestroy();
    }

}
