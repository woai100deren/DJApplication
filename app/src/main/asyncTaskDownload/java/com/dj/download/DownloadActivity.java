package com.dj.download;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

public class DownloadActivity extends BaseActivity {
    private ProgressView mProgressView;
    private String url = "http://clientsys.aslkt.com/uploads/apk/2020/06/13/aslkt-student-release_V2.1.1_202006131335.apk";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        mProgressView = findViewById(R.id.progressView);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mProgressView.setProgress(75);
                Downloader downloader = new Downloader(DownloadActivity.this, url, new CallBack() {
                    @Override
                    public void updateProgress(int progress) {
                        mProgressView.setProgress(progress);
                    }
                });
                downloader.download();
            }
        });
    }
}
