package com.finger;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 指纹解锁
 */
public class FingerMainActivity extends BaseActivity {
    private FingerprintManager fingerprintManager = null;
    @BindView(R.id.info)
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger);
        ButterKnife.bind(this);

        getFingerprintManager();
    }

    /**
     * 开始指纹识别
     */
    @OnClick(R.id.open)
    protected void open(){
        info.setVisibility(View.VISIBLE);
        info.setText("请验证指纹...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager.authenticate(null,
                    new CancellationSignal(),
                    0,
                    new FingerprintManager.AuthenticationCallback() {
                        @Override
                        public void onAuthenticationError(int errorCode, CharSequence errString) {
                            info.setText("验证错误");
                        }

                        @Override
                        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                            info.setText(helpString);
                        }

                        @Override
                        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                            info.setText("验证成功");
                        }

                        @Override
                        public void onAuthenticationFailed() {
                            info.setText("验证失败");
                        }
                    },
                    null);
        }
    }

    /**
     * 检查是否支持指纹识别
     */
    @OnClick(R.id.check)
    protected void check(){
        info.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(fingerprintManager.hasEnrolledFingerprints()){
                Toast.makeText(FingerMainActivity.this,"支持",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(FingerMainActivity.this,"不支持",Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getFingerprintManager() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
