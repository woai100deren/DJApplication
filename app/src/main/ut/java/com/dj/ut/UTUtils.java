package com.dj.ut;

import com.dj.logutil.LogUtils;

public class UTUtils {
    public int add(int a,int b){
        return a+b;
    }

    private boolean checkUrl(String url){
        if(url == null || url.length() == 0){
            return false;
        }
        if((url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://"))
            && url.toLowerCase().endsWith(".apk")){
            return true;
        }
        return false;
    }

    public void loginApp(String name, String password){
        if (name == null || name.length() == 0) return;
        if (password == null || password.length() < 6) return;
        login(name, password, new UTNetworkCallback() {
            @Override
            public void onSuccess(String msg) {

            }

            @Override
            public void onFailure(String msg) {
                LogUtils.e(msg);
            }
        });
    }

    public void login(String name, String password,UTNetworkCallback networkCallback){
        if (name.equals("axb") && password.equals("123456")) {
            networkCallback.onSuccess("OK");
        }else {
            networkCallback.onFailure("FAIL");
        }
    }
}
