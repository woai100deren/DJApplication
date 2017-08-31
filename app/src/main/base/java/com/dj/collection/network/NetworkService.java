package com.dj.collection.network;

import com.dj.collection.bean.SoResponseBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {
//    @GET("so.json")
//    Observable<SoResponseBean> getSoDownloadInfo();
    @GET("video/Omnibus/getInfo")
    Observable<SoResponseBean> getSoDownloadInfo(@Query("oid") String oid);
}
