package com.pdf;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfActivity extends BaseActivity {
    private int page = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        final PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        final Button next = (Button) findViewById(R.id.next);
//        pdfView.fromAsset("123.pdf")
        pdfView.fromAsset("2.pdf")
                .defaultPage(page)
                .swipeHorizontal(true)
                .enableDoubletap(false)
                .spacing(20)
                .load();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page ++;
                pdfView.jumpTo(page);
            }
        });
    }
}
