package com.zysj.zysjApi;

import android.app.ZysjSystemManager;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class McuActivity extends AppCompatActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener {
    private final String TAG = getClass().getSimpleName();
    private ZysjSystemManager mzysjSystemManager;
    private Button btn_write_flash;
    private Button btn_read_flash;
    private Button btn_read_power_on_time;
    private Button btn_read_power_on_status;
    private Button btn_write_power_on_time;
    private Button btn_read_watch_dog_status;
    private Button btn_write_watch_dog_time;
    private Button btn_feed_watch_dog;
    private Button btn_read_mcu_version;

    private TextView tv_mcu_flash;
    private TextView tv_power_on_time;
    private TextView tv_power_on_status;
    private TextView tv_watch_dog_status;
    private TextView tv_feed_watch_dog;
    private TextView tv_read_mcu_version;

    private EditText edt_flash;
    private EditText edt_power_on_time;
    private EditText edt_write_watch_dog_time;
    private Switch sw_feed_watch_dog_time_unit;
    private Switch sw_power_on_off;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcu);
        mzysjSystemManager = (ZysjSystemManager) getSystemService("zysj");
        initViews();
        registerListener();
        readMcuStatus();
        goneView();
    }

    private void initViews() {
        btn_write_flash = findViewById(R.id.btn_write_flash);
        btn_read_flash = findViewById(R.id.btn_read_flash);
        btn_read_power_on_time = findViewById(R.id.btn_read_power_on_time);
        btn_read_power_on_status = findViewById(R.id.btn_read_power_on_status);
        btn_write_power_on_time = findViewById(R.id.btn_write_power_on_time);
        btn_read_watch_dog_status = findViewById(R.id.btn_read_watch_dog_status);
        btn_write_watch_dog_time = findViewById(R.id.btn_write_watch_dog_time);
        btn_feed_watch_dog = findViewById(R.id.btn_feed_watch_dog);
        btn_read_mcu_version = findViewById(R.id.btn_read_mcu_version);

        tv_mcu_flash = findViewById(R.id.tv_mcu_flash);
        tv_power_on_time = findViewById(R.id.tv_power_on_time);
        tv_power_on_status = findViewById(R.id.tv_power_on_status);
        tv_watch_dog_status = findViewById(R.id.tv_watch_dog_status);
        tv_feed_watch_dog = findViewById(R.id.tv_feed_watch_dog);
        tv_read_mcu_version = findViewById(R.id.tv_read_mcu_version);

        edt_flash = findViewById(R.id.edt_flash);
        edt_power_on_time = findViewById(R.id.edt_power_on_time);
        edt_write_watch_dog_time = findViewById(R.id.edt_write_watch_dog_time);
        sw_feed_watch_dog_time_unit = findViewById(R.id.sw_feed_watch_dog_time_unit);
        sw_power_on_off = findViewById(R.id.sw_power_on_off);
    }

    private void goneView() {
        tv_mcu_flash.setVisibility(View.GONE);
        tv_power_on_time.setVisibility(View.GONE);
        tv_power_on_status.setVisibility(View.GONE);

        btn_write_flash.setVisibility(View.GONE);
        btn_read_flash.setVisibility(View.GONE);
        btn_read_power_on_time.setVisibility(View.GONE);
        btn_read_power_on_status.setVisibility(View.GONE);
        btn_write_power_on_time.setVisibility(View.GONE);
        edt_flash.setVisibility(View.GONE);
        edt_power_on_time.setVisibility(View.GONE);
        sw_power_on_off.setVisibility(View.GONE);
    }

    private void registerListener() {
        btn_write_flash.setOnClickListener(this);
        btn_read_flash.setOnClickListener(this);
        btn_read_power_on_time.setOnClickListener(this);
        btn_read_power_on_status.setOnClickListener(this);
        btn_write_power_on_time.setOnClickListener(this);
        btn_read_watch_dog_status.setOnClickListener(this);
        btn_write_watch_dog_time.setOnClickListener(this);
        btn_feed_watch_dog.setOnClickListener(this);
        btn_read_mcu_version.setOnClickListener(this);

        sw_feed_watch_dog_time_unit.setOnCheckedChangeListener(this);
    }

    private void readMcuStatus() {
        int version = mzysjSystemManager.getMcuVersion();
        Log.d(TAG, "readMcuStatus() version = " + version);
        if (version < 0) {
            toast("设备不支持单片机");
            return;
        }
        readFlashData();
        tv_power_on_time.setText(getString(R.string.power_on_time, mzysjSystemManager.getPowerOnTimeFromCh34()));
        tv_power_on_status.setText(getString(R.string.power_on_status, mzysjSystemManager.getPowerOnTimeStatus() == 1 ? "开" : "关"));
        readWatchdogStatus();
        tv_read_mcu_version.setText(getString(R.string.mcu_version, mzysjSystemManager.getMcuVersion()));

        edt_power_on_time.setText(getPowerTime());
    }

    private void readFlashData() {
        byte[] flashData = mzysjSystemManager.readDataFromCh34Flash();
        if (flashData == null) {
            toast("读取Flash数据为空！");
            return;
        }
        tv_mcu_flash.setText("0x");
        for (int i = 0; i < flashData.length; i++) {
            Log.i("Aurora", "read: " + (flashData[i] & 0xff));
            if (i > 0) {
                tv_mcu_flash.append("0x");
            }
            tv_mcu_flash.append(Integer.toHexString(flashData[i] & 0xff));
            if (i == flashData.length - 1) {
                break;
            }
            tv_mcu_flash.append(",");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_read_flash:
                readFlashData();
                break;
            case R.id.btn_write_flash:
                int resultWriteFlash = mzysjSystemManager.witeDataToCh34Flash(edt_flash.getText().toString());
                Log.d(TAG, "resultWriteFlash = " + resultWriteFlash);
                break;
            case R.id.btn_read_power_on_time:
                tv_power_on_time.setText(getString(R.string.power_on_time, mzysjSystemManager.getPowerOnTimeFromCh34()));
                break;
            case R.id.btn_read_power_on_status:
                tv_power_on_status.setText(getString(R.string.power_on_status, mzysjSystemManager.getPowerOnTimeStatus() == 1 ? "开" : "关"));
                break;
            case R.id.btn_write_power_on_time:
                String time = edt_power_on_time.getText().toString();
                if (TextUtils.isEmpty(time)) {
                    toast(R.string.content_cannot_empty);
                    return;
                }
                int powerType = sw_power_on_off.isChecked() ? 1 : 0;
                mzysjSystemManager.setPowerOnOffTime(time, powerType);
                tv_power_on_status.postDelayed(  () -> {
                    tv_power_on_status.setText(getString(R.string.power_on_status, mzysjSystemManager.getPowerOnTimeStatus() == 1 ? "开" : "关"));
                }, 500);
//                String[] timeSpit = time.split(" ");
//                Log.d(TAG, " timeSpit[0] = " + timeSpit[0] + ",timeSpit[1] = " + timeSpit[1]) ;
//                mzysjSystemManager.setTimeToRtc(2,timeSpit[0],timeSpit[1]);
                break;
            case R.id.btn_read_watch_dog_status:
                readWatchdogStatus();
                break;
            case R.id.btn_write_watch_dog_time:
                String watchTime = edt_write_watch_dog_time.getText().toString();
                if (sw_feed_watch_dog_time_unit.isChecked()) {
                    if (TextUtils.isEmpty(watchTime)) {
                        toast(R.string.content_cannot_empty);
                        return;
                    }
                    int timeOutInt = Integer.parseInt(watchTime);
                    if (timeOutInt < 5 || timeOutInt > 180) {
                        toast("请设置5到180秒时间");
                        return;
                    }
                }
                int resultWatchdog = mzysjSystemManager.setWatchDogConfigToCh34(Integer.parseInt(watchTime), sw_feed_watch_dog_time_unit.isChecked());
                Log.d(TAG, "resultWatchdog =  " + resultWatchdog);
                tv_watch_dog_status.postDelayed(() -> {
                    readWatchdogStatus();
                }, 500);

                break;
            case R.id.btn_feed_watch_dog:
                int result = mzysjSystemManager.feedWatchDog();
                Log.d(TAG, "feedWatchDog result " + result);
                tv_feed_watch_dog.setText(getString(R.string.feed_watch_dog_status, result== 0 ? "成功" : "失败" ));
                break;
            case R.id.btn_read_mcu_version:
                tv_read_mcu_version.setText(getString(R.string.mcu_version, mzysjSystemManager.getMcuVersion()));
                break;
        }
    }

    private void readWatchdogStatus() {
        int timeout = mzysjSystemManager.getWatchDogTimeout();
        tv_watch_dog_status.setText(getString(R.string.watch_dog_status, mzysjSystemManager.getWatchDogStatus()  == 1 ? "开" +", timeout :" + timeout + "秒": "关"));
    }

    private void toast(String content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    private void toast(int content) {
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (compoundButton.getId() == R.id.sw_feed_watch_dog_time_unit) {
            if (!isChecked) {
                int result = mzysjSystemManager.setWatchDogConfigToCh34(10, false);
                if (result == 0) {
                    toast("关闭看门狗成功");
                    tv_watch_dog_status.setText(getString(R.string.watch_dog_status,  "关"));
                }
                Log.d(TAG, "onCheckedChanged result = " +result);
            }
        }

    }

    private String getPowerTime() {
        Date closeSysData = new Date(new Date().getTime() + 300000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(closeSysData);
    }
}
