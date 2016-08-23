package com.example.bluetooth.ui;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.code.space.androidlib.annotation.ContentView;
import com.code.space.javalib.viewinject.FindView;
import com.example.bluetooth.R;
import com.example.bluetooth.base.BaseActivity;
import com.example.bluetooth.utils.BLEUtils;
import com.example.bluetooth.utils.BlueToothUtils;
import com.google.zxing.activity.CaptureActivity;

/**
 * Created by Administrator on 2016/8/23.
 */
@ContentView(R.layout.activity_dont_open_blue_tooth)
public class OpenBlueToothActivity extends BaseActivity implements View.OnClickListener {

    @FindView(R.layout.activity_dont_open_blue_tooth)
    RelativeLayout relative_open_blue_tooth;


    @Override
    protected void init(Intent intent) {
        relative_open_blue_tooth= (RelativeLayout) findViewById(R.id.relative_open_blue_tooth);
        $(this, relative_open_blue_tooth);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_open_blue_tooth:
                BLEUtils.getInstance().openBlueTooth(this);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BlueToothUtils.REQUEST_ENABLE_BT:
                switch (resultCode) {
                    case RESULT_OK:
                        Toast.makeText(OpenBlueToothActivity.this, "蓝牙打开成功!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, CaptureActivity.class));
                        break;
                    case RESULT_CANCELED:
                        Toast.makeText(OpenBlueToothActivity.this, "蓝牙打开失败!", Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
        }
    }
}
