package com.example.fang.baiduyuyin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private Button start,pause,resume,stop;
    private EditText content;
    private SpeechUtils speechUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();
        iniOnClick();
        getPermission();
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_PHONE_STATE
            },1001);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_SETTINGS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_SETTINGS
            },1002);
        }
    }

    private void iniOnClick() {
        start.setOnClickListener(this);
        resume.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);
        speechUtils=SpeechUtils.getsSpeechUtils(this);
    }

    private void iniView() {
        start= (Button) findViewById(R.id.btn_start);
        pause= (Button) findViewById(R.id.btn_pause);
        stop= (Button) findViewById(R.id.btn_stop);
        resume= (Button) findViewById(R.id.btn_resume);
        content= (EditText) findViewById(R.id.edt_content);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                if (!TextUtils.isEmpty(content.getText().toString())){
                    speechUtils.speak(content.getText().toString());
                }
                break;
            case R.id.btn_pause:
                speechUtils.pause();
                break;
            case R.id.btn_resume:
                speechUtils.resume();
                break;
            case R.id.btn_stop:
                speechUtils.stop();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechUtils.stop();
        speechUtils.release();
    }
}
