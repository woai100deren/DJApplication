package com.dj.collection.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by wangjing4 on 2017/7/6.
 */

public class Utils {
    /**
     * 通用toast
     * @param context
     * @param charSequence
     */
    public static void toastShow(Context context,CharSequence charSequence){
        Toast.makeText(context,charSequence,Toast.LENGTH_SHORT).show();
    }

    public static void loadSoFile(Context context,String formPath) {
        File dir = getDataDir(context);
        if (dir == null || !isLoadSoFile(dir)) {
//            copy(formPath, dir.getAbsolutePath());
            copySdcardFile(formPath, dir.getAbsolutePath()+File.separator+"dynamic.so");
        }
    }

    public static boolean isLoadSoFile(File dir) {
        File[] currentFiles;
        currentFiles = dir.listFiles();
        boolean hasdynamic = false;
        if (currentFiles == null) {
            return false;
        }
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].getName().contains("dynamic")) {
                hasdynamic = true;
            }
        }
        return hasdynamic;
    }

    public static boolean copy(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return false;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory()) {
                //如果当前项为子目录 进行递归
                copy(currentFiles[i].getPath() + File.separator, toFile + currentFiles[i].getName() + File.separator);

            } else {
                //如果当前项为文件则进行文件拷贝
                if (currentFiles[i].getName().contains(".so")) {
                    int id = copySdcardFile(currentFiles[i].getPath(), toFile + File.separator + currentFiles[i].getName());
                }
            }
        }
        return true;
    }


    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static int copySdcardFile(String fromFile, String toFile) {

        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fosfrom.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            // 从内存到写入到具体文件
            fosto.write(baos.toByteArray());
            // 关闭文件流
            baos.close();
            fosto.close();
            fosfrom.close();
            return 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static File getDataDir(Context context){
        return context.getDir("libs", Context.MODE_PRIVATE);
    }

    public static String getSSS(){
        return "aaaaaaaa";
    }
}
