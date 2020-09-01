package com.dj.download;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.dj.logutil.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader {
    public static final String TAG = "Downloader";
    private CallBack callBack;
    private List<DownloadTask> tasks;
    private InnerHandler handler = new InnerHandler();
    private static final int THREAD_POOL_SIZE = 9;  //线程池大小为9
    private static final int THREAD_NUM = 3;  //每个文件3个线程下载
    private static final int GET_LENGTH_SUCCESS = 1;
    public static final Executor THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private long fileLength;
    private long downloadLength; //下载过程中记录已下载大小
    private Context context;
    private String downloadUrl;//下载地址

    public Downloader(Context context, String downloadUrl,CallBack callBack) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.callBack = callBack;
    }

    public void download(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(downloadUrl).build();
                try {
                    Response response = client.newCall(request).execute();
                    fileLength = response.body().contentLength();

                    Message.obtain(handler, GET_LENGTH_SUCCESS).sendToTarget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 开始创建AsyncTask下载
     */
    private void beginDownload() {
        String savePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/download";
        File fileDir = new File(savePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(savePath, downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1));
        if(file.exists()){
            file.delete();
        }


        LogUtils.e("文件大小为："+fileLength);
        long blockLength = fileLength / THREAD_NUM;
        for (int i = 0; i < THREAD_NUM; i++) {
            long beginPosition = i * blockLength;//每条线程下载的开始位置
            long endPosition = (i + 1) * blockLength;//每条线程下载的结束位置
            if (i == (THREAD_NUM - 1)) {
                endPosition = fileLength;//如果整个文件的大小不为线程个数的整数倍，则最后一个线程的结束位置即为文件的总长度
            }
            LogUtils.e("文件拆分为："+beginPosition+" - "+endPosition);
            DownloadTask task = new DownloadTask(i, beginPosition, endPosition, this, context);
            task.executeOnExecutor(THREAD_POOL_EXECUTOR, downloadUrl);
            if(tasks == null) {
                tasks = new ArrayList<DownloadTask>();
            }
            tasks.add(task);
        }
    }

    /**
     * 暂停下载
     */
    public void pause() {
        for (DownloadTask task : tasks) {
            if (task != null && (task.getStatus() == AsyncTask.Status.RUNNING || !task.isCancelled())) {
                task.cancel(true);
            }
        }
        tasks.clear();
    }

    /**
     * 添加已下载大小
     * 多线程访问需加锁
     * @param size
     */
    protected synchronized void updateDownloadLength(long size){
        this.downloadLength += size;
        //通知更新界面
        int percent = (int)((float)downloadLength * 100 / (float)fileLength);
        if(callBack!=null){
            callBack.updateProgress(percent);
        }
    }

    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_LENGTH_SUCCESS :
                    beginDownload();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
