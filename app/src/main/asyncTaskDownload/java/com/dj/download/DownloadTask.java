package com.dj.download;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import com.baidu.ocr.sdk.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String, Integer , Long>  {
    private static final String TAG = "DownloadTask";

    private int taskId;
    private long beginPosition;
    private long endPosition;
    private long downloadLength;
    private String url;
    private Downloader downloader;
    public DownloadTask(int taskId, long beginPosition, long endPosition, Downloader downloader,
                        Context context) {
        this.taskId = taskId;
        this.beginPosition = beginPosition;
        this.endPosition = endPosition;
        this.downloader = downloader;
    }
    @Override
    protected Long doInBackground(String... params) {
        //这里加判断的作用是：如果还处于等待就暂停了，运行到这里已经cancel了，就直接退出
        if(isCancelled()) {
            return null;
        }
        url = params[0];
        if(url == null) {
            return null;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "close")
                .addHeader("Range", "bytes=" + beginPosition + "-" + endPosition)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.i(TAG,"download failed");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                RandomAccessFile fos = null;
                // 储存下载文件的目录
                String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download";
                try {
                    is = response.body().byteStream();

                    File file = new File(savePath, url.substring(url.lastIndexOf("/") + 1));
                    //此处不能判断文件存在然后删除，因为这里是多线程在操作，碰到第二个以上的线程会把第一个线程写入的文件给删掉，文件就不完整
//                    if(file.exists()){
//                        file.delete();
//                    }

                    //创建文件输出流
                    fos = new RandomAccessFile(file, "rw");
                    //从文件的size以后的位置开始写入
                    fos.seek(beginPosition);

                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        downloader.updateDownloadLength(len);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e(TAG,"download failed : "+e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
        return null;
    }
}
