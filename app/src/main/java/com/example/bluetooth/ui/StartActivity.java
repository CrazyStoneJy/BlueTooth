package com.example.bluetooth.ui;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.code.space.androidlib.annotation.ContentView;
import com.example.bluetooth.R;
import com.example.bluetooth.base.BaseActivity;
import com.example.bluetooth.utils.BLEUtils;
import com.google.zxing.activity.CaptureActivity;

/**
 * Created by Administrator on 2016/8/23.
 */
@ContentView(R.layout.activity_start)
public class StartActivity extends BaseActivity implements View.OnClickListener {

//    @FindView(R.id.txt_scan_open_door)
    TextView txt_scan_open_door;

//    @FindView(R.id.linear_scan)
    LinearLayout linear_scan;


    @Override
    protected void init(Intent intent) {
        linear_scan= (LinearLayout) findViewById(R.id.linear_scan);
        $(this, linear_scan);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_scan:
                if (BLEUtils.getInstance().isOpenBlueTooth(this)) {
                    startActivity(new Intent(this, CaptureActivity.class));
                } else {
                    startActivity(new Intent(this, OpenBlueToothActivity.class));
                }
                break;
        }
    }
}
