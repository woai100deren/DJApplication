package com.dj.collection.network.factory;

import com.alibaba.fastjson.JSON;
import com.dj.logutil.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by Charles on 2016/3/17.
 */
class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
    * 转换方法
    */
    @Override
    public T convert(ResponseBody value) throws IOException  {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        LogUtils.json(tempStr);
        return JSON.parseObject(tempStr, type);
    }
}
