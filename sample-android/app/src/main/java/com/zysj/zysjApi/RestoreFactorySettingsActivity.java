package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RestoreFactorySettingsActivity extends AppCompatActivity {
    public Button resetSys;
    private ZysjSystemManager mzysjSystemManager;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_factory_settings);
        mzysjSystemManager = (ZysjSystemManager)getSystemService("zysj");
        resetSys = (Button)findViewById(R.id.resetSys);
        resetSys.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mzysjSystemManager.zYresetSys();
            }
        });
    }
}