package com.dj.ut;
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
}
