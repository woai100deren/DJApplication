package com.dj.collection.network.listener;

/**
 * Created by wangjing4 on 2017/7/6.
 */

public interface OnDownloadListener {
    /**
     * 下载成功
     */
    void onDownloadSuccess();

    /**
     * @param progress
     * 下载进度
     */
    void onDownloading(int progress);

    /**
     * 下载失败
     */
    void onDownloadFailed();
}
