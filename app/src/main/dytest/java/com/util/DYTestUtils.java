package com.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangjing4 on 2017/7/19.
 */

public class DYTestUtils {
    private boolean isFlag = false;
    public void add(int a,int b){
        System.out.println("a+b="+returnAdd(a,b));
    }

    public int returnAdd(int a,int b){
        if(isFlag) {
            return a + b;
        }else{
            return 5;
        }
    }

    public void setFlag(boolean flag){
        this.isFlag = flag;
    }

    public void toastInfo(Context context, String aa){
        Toast.makeText(context,aa,Toast.LENGTH_SHORT).show();
    }
}
