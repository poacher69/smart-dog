package com.wc.ottoapp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    ArrayList<HashMap<String, Object>> mylist;
    AlertDialog cdiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隱藏ActionBar方法1
        getSupportActionBar().hide();

        list = (ListView)findViewById(R.id.listView);

        final String connectList[] = getResources().getStringArray(R.array.connect_choice);
        int imgs[] = {R.drawable.ap_icon,R.drawable.wifi_icon};
        mylist = new ArrayList<>();
        for(int i=0; i< connectList.length; i++)
        {
            HashMap<String, Object> m1 = new HashMap<>();
            m1.put("conList",connectList[i]);
            m1.put("img", imgs[i]);
            mylist.add(m1);
        }

        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, mylist,R.layout.connect_choice_layout,new String[]{"conList","img"},new int[]{R.id.textView1,R.id.imageView1});
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(MainActivity.this,Control_Activity.class);
                        intent.putExtra("ip","192.168.0.1");
                        startActivity(intent);
                        break;
                    case 1:

                        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                        View diagView = inflater.inflate(R.layout.ip_input_layout,null);
                        final EditText ed_diag = (EditText)diagView.findViewById(R.id.editText1);
                        Button btn = (Button)diagView.findViewById(R.id.button1);
                        Button btn_speech = (Button) diagView.findViewById(R.id.button_speech);

                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(MainActivity.this,Control_Activity.class);
                                it.putExtra("ip",ed_diag.getText().toString());
                                startActivity(it);
                                cdiag.dismiss();

                            }
                        });

                        btn_speech.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent it = new Intent(MainActivity.this,SpeechActivity.class);
                                it.putExtra("ip",ed_diag.getText().toString());
                                startActivity(it);
                                cdiag.dismiss();
                            }
                        });

                        cdiag = new AlertDialog.Builder(MainActivity.this).setTitle(R.string.input_ip_address).setView(diagView).show();
                        break;
                }

            }
        });
    }
}
