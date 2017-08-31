package com.dj.zip.utils;


import android.os.Handler;

import com.dj.zip.CallBack;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.File;

/**
 * Created by wangjing4 on 2017/7/21.
 */

public class ZipUtils {
    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * <p>
     * 如果指定目录不存在,可以自动创建,不合法的路径将导致异常被抛出
     * @param zip 指定的ZIP压缩文件
     * @param dest 解压目录
     */
    public static void unzip(String zip, String dest, CallBack callBack) throws Exception {
        File zipFile = new File(zip);
        unzip(zipFile, new File(dest),callBack);
    }
    /**
     * 使用给定密码解压指定的ZIP压缩文件到指定目录
     * @param zipFile 指定的ZIP压缩文件
     * @param unzipPath 解压目录
     */
    public static void unzip(final File zipFile, File unzipPath, final CallBack callBack) throws Exception{
        final Handler handler = new Handler();


        ZipFile zFile = new ZipFile(zipFile);
        if (!zFile.isValidZipFile()) {//判断压缩文件是否有效
            throw new ZipException("压缩文件不合法,可能被损坏.");
        }

        File destDir = unzipPath;
        if (destDir.isDirectory() && !destDir.exists()) {//判断要解压到的文件夹是否存在
            destDir.mkdir();
        }
        if (zFile.isEncrypted()) {
            zFile.setPassword("123456");
        }

        final ProgressMonitor progressMonitor = zFile.getProgressMonitor();

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    int precentDone = 0;
                    int lastPr = 0;
                    while (true)
                    {
                        // 每隔1000ms,发送一个解压进度出去
                        Thread.sleep(1000);
                        precentDone = progressMonitor.getPercentDone();

                        if(lastPr>0 && precentDone ==0){
                            lastPr = 100;
                        }else{
                            lastPr = precentDone;
                        }

                        final int finalLastPr = lastPr;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(callBack!=null) {
                                    callBack.complete(String.valueOf(finalLastPr));
                                }
                            }
                        });


                        if (lastPr >= 100)
                        {
                            break;
                        }
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        zFile.setRunInThread(true); //true 在子线程中进行解压 , false主线程中解压
        zFile.extractAll(unzipPath.toString());
    }
}
