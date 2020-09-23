package com.dj.ws;

import com.dj.collection.bean.AppInfoBean;
import com.dj.collection.bean.SoResponseBean;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface WSNetworkService {
    @Headers({
            "Content-Type: text/xml; charset=utf-8",
            "SOAPAction: http://WebXml.com.cn/getSupportCity"
    })
    @POST("WebServices/WeatherWebService.asmx")
    Observable<ResponseBody> getWeather(@retrofit2.http.Body String str);
}
