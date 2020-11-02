package com.dj.scroll.message;

import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

public class ScrollMessageActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_message);

        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);

        View.inflate(this, R.layout.item_scroll_message, viewFlipper);
        View.inflate(this, R.layout.item_scroll_message, viewFlipper);
        View.inflate(this, R.layout.item_scroll_message, viewFlipper);
    }
}
