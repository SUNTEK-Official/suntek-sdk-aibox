package com.zysj.zysjApi;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SetSystemTimeActivity extends AppCompatActivity {
    public Button handSetTime,autoSetTime;
    public EditText etDate;
    //public EditText etTime;
    private ZysjSystemManager mzysjSystemManager;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_system_time);
        mzysjSystemManager = (ZysjSystemManager)getSystemService("zysj");
        etDate = (EditText)findViewById(R.id.etDate);
        //etTime = (EditText)findViewById(R.id.etTime);
        handSetTime = (Button)findViewById(R.id.handSetTime);
        autoSetTime = (Button)findViewById(R.id.autoSetTime);

        handSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etDate.getText().toString();
                //String time = etTime.getText().toString();
                if(data.equals(null)&& data.equals("")){
                    Toast.makeText(SetSystemTimeActivity.this, "设置失败，时间不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!validationTimeFormat(data)){
                    Toast.makeText(SetSystemTimeActivity.this, "设置失败，时间格式不正确，请按照正确格式输入", Toast.LENGTH_LONG).show();
                    return;
                }
                boolean value = mzysjSystemManager.zYsetSysTime(data);
                if(value==true){
                    Toast.makeText(SetSystemTimeActivity.this, "设置成功", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SetSystemTimeActivity.this, "设置失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        autoSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date data = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dataFormat = sdf.format(data);

                Date time = new Date(new Date().getTime() + 3600000);
                sdf = new SimpleDateFormat("HH-mm-ss");
                String timeFormat = sdf.format(time);

                String dataTime = dataFormat +"-"+ timeFormat;

                etDate.setText(dataTime);
                //String time = etTime.getText().toString();
                boolean value = mzysjSystemManager.zYsetSysTime(dataTime);
                if (value == true) {
                    Toast.makeText(SetSystemTimeActivity.this, "设置成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SetSystemTimeActivity.this, "设置失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 校验时间格式为2021-11-15-04-24-46
     * @param time
     * @return
     */
    public boolean validationTimeFormat(String time) {
        boolean bool = false;
        Pattern pattern = Pattern.compile("[0-9]*");
        if(time != null && time.length() == 19) {

            if(pattern.matcher(time.substring(0, 4)).matches()) {

                if("-".indexOf(time.substring(4, 5))!=-1) {
 // 6,7
                    if(pattern.matcher(time.substring(5,7)).matches()) {

                        if("-".indexOf(time.substring(7, 8))!=-1) {
 // 9,10
                            if(pattern.matcher(time.substring(8, 10)).matches()) {

                                if("-".indexOf(time.substring(10, 11))!=-1) {
 // 12,13
                                    if(pattern.matcher(time.substring(11, 13)).matches()) {

                                        if("-".indexOf(time.substring(13, 14))!=-1) {
 // 15,16
                                            if(pattern.matcher(time.substring(14, 16)).matches()) {

                                                if("-".indexOf(time.substring(16, 17))!=-1) {
 // 18,19
                                                    if(pattern.matcher(time.substring(17, 19)).matches()) {
                                                        bool = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return bool;
    }
}