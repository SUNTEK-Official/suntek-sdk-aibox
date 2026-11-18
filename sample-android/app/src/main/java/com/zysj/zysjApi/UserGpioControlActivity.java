package com.zysj.zysjApi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ZysjSystemManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class UserGpioControlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final boolean IS_DEBUG = true;
    private static final String TAG = "MainActivity";

    TextView mTvGpioOne, mTvGpioTwo, mTvGpioThr, mTvGpioFour;

    Button mBtnHigh, mBtnLow;

    CheckBox mCbOne, mCbTwo, mCbThree, mCbFour;

    Toast mToast;
    int MODEL_TYPE = -1;
    ZysjSystemManager mZysjSystemManager = null;
    private boolean mIsRunning = true;
    private static final int HANDLER_SET_HIGH_VALUE = 0;
    private static final int HANDLER_SET_LOW_VALUE = 1;
    private static final int HANDLER_GET_ALL_VALUE = 2;
    int gpio1Value;
    int gpio2Value;
    int gpio3Value;
    int gpio4Value;
    int gpio5Value;
    int gpio6Value;
    int gpio7Value;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_SET_HIGH_VALUE: {
                    setGpioValue(1);
                }
                break;
                case HANDLER_SET_LOW_VALUE: {
                    setGpioValue(0);
                }
                break;
                case HANDLER_GET_ALL_VALUE: {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            getAllGpioValue();
                        }
                    }).start();
                }
                break;
                case 100: {
                    mTvGpioOne.setText(""  + gpio1Value);
                    mTvGpioTwo.setText(""  + gpio2Value);
                    mTvGpioThr.setText(""  + gpio3Value);
                    mTvGpioFour.setText("" + gpio4Value);
                }
                break;
            }
        }

    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class ReadGpioDataThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (mIsRunning) {
                try {
                    Thread.sleep(5);
                    mHandler.sendEmptyMessage(HANDLER_GET_ALL_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_gpio_control);
        if (Build.MODEL.contains("MStar")) {
            MODEL_TYPE = 0;
        } else if (Build.MODEL.contains("3399")){
            MODEL_TYPE = 1;
        } else {
            MODEL_TYPE = 2;
        }
        Log.i("lrm", "MODEL_TYPE: " + MODEL_TYPE);

        mZysjSystemManager = (ZysjSystemManager) getApplicationContext().getSystemService("zysj");

        initView();
        getAllGpioValue();
        (new ReadGpioDataThread()).start();
    }
    private void initCheckBox() {
        mCbOne.setChecked(false);
        mCbTwo.setChecked(false);
        mCbThree.setChecked(false);
        mCbFour.setChecked(false);

    }
    private void zysjToast(String toastMsg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, toastMsg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(toastMsg);
        }
        mToast.show();
    }
    protected void setGpioValue(int i) {
        Log.i(TAG, "i == " + i);
        if (!mCbOne.isChecked() && !mCbTwo.isChecked() && !mCbThree.isChecked() && !mCbFour.isChecked()) {
            zysjToast(getResources().getString(R.string.toast_tip));
            return;
        }


        int ret = 100;
        if (mCbOne.isChecked())
            ret = mZysjSystemManager.set_zysj_gpio_value(1, i);
        if (mCbTwo.isChecked())
            ret = mZysjSystemManager.set_zysj_gpio_value(2, i);
        if (mCbThree.isChecked())
            ret = mZysjSystemManager.set_zysj_gpio_value(3, i);
        if (mCbFour.isChecked())
            ret = mZysjSystemManager.set_zysj_gpio_value(4, i);
        Log.i("MainActivity", "ret = " + ret);


        getAllGpioValue();
        initCheckBox();
    }

    private void initView() {

        mTvGpioOne = (TextView) findViewById(R.id.tv_gpio_one);
        mTvGpioTwo = (TextView) findViewById(R.id.tv_gpio_two);
        mTvGpioThr = (TextView) findViewById(R.id.tv_gpio_thr);
        mTvGpioFour = (TextView) findViewById(R.id.tv_gpio_four);

        mBtnHigh = (Button) findViewById(R.id.btn_high);
        mBtnLow = (Button) findViewById(R.id.btn_low);

        mBtnHigh.setOnClickListener(this);
        mBtnLow.setOnClickListener(this);

        mCbOne = (CheckBox) findViewById(R.id.cb_one);
        mCbTwo = (CheckBox) findViewById(R.id.cb_two);
        mCbThree = (CheckBox) findViewById(R.id.cb_thr);
        mCbFour = (CheckBox) findViewById(R.id.cb_four);


    }

    private void getAllGpioValue() {

        gpio1Value = mZysjSystemManager.get_zysj_gpio_value(1);
        gpio2Value = mZysjSystemManager.get_zysj_gpio_value(2);
        gpio3Value = mZysjSystemManager.get_zysj_gpio_value(3);
        gpio4Value = mZysjSystemManager.get_zysj_gpio_value(4);
        gpio5Value = mZysjSystemManager.get_zysj_gpio_value(5);
        gpio6Value = mZysjSystemManager.get_zysj_gpio_value(6);
        gpio7Value = mZysjSystemManager.get_zysj_gpio_value(24);
        mHandler.sendEmptyMessage(100);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_high: {
                mHandler.sendEmptyMessage(HANDLER_SET_HIGH_VALUE);
            }
            break;
            case R.id.btn_low: {
                mHandler.sendEmptyMessage(HANDLER_SET_LOW_VALUE);
            }
            break;
            default:
                debugLog("the button value is error: " + v.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsRunning = false;
    }

    private void debugLog(String logMsg) {
        if (IS_DEBUG) {
            Log.i(TAG, logMsg);
        }
    }
}