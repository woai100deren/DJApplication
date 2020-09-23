package com.dj.ws;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.logutil.LogUtils;

import java.io.IOException;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.ResponseBody;

/**
 * android请求webservice
 */
public class WSActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_ws);
        TextView result = findViewById(R.id.result);

        findViewById(R.id.getWeather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WSNetworkHelper.getInstance().getWeather("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "  <soap:Body>\n" +
                        "    <getSupportCity xmlns=\"http://WebXml.com.cn/\">\n" +
                        "      <byProvinceName>湖北</byProvinceName>\n" +
                        "    </getSupportCity>\n" +
                        "  </soap:Body>\n" +
                        "</soap:Envelope>", new ResponseListener<ResponseBody>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            String xmlResult = responseBody.string();
                            LogUtils.e("xml结果："+xmlResult);
                            XmlToJson xmlToJson = new XmlToJson.Builder(xmlResult).build();
                            String jsonResult = xmlToJson.toJson().toString();
                            LogUtils.e("json结果："+jsonResult);
                            result.setText(jsonResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailed(Throwable e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }
        });
    }
}
