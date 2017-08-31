package com.dj.cpu;

/**
 * Created by wangjing4 on 2017/7/11.
 */

public class CPUFrameworkHelper {
    static {
        System.loadLibrary("cpu-lib");
    }
    public static final String ARM64_V8A = "arm64-v8a";
    public static final String ARMEABI = "armeabi";
    public static final String ARMEABI_V7A = "armeabi-v7a";
    public static final String MIPS = "mips";
    public static final String MIPS64 = "mips64";
    public static final  String X86 = "x86";
    public static final  String X86_64 = "x86_64";

    public static native boolean isArm64Cpu();//arm64-v8a
    public static native boolean isArmCpu();//armeabi
    public static native boolean isArm7Compatible();//armeabi-v7a
    public static native boolean isMipsCpu();//mips
    public static native boolean isMips64Cpu();//mips64
    public static native boolean isX86Cpu();//x86
    public static native boolean isX86_64Cpu();//x86_64

    /**
     * 通过读取设备CPU类型，获取设备支持的so库文件夹名称
     */
    public static final String getFileNameByCpuType(){
        String fileName = ARMEABI;
        if(isArm64Cpu()){
            fileName = ARM64_V8A;
        }else if(isArmCpu()){
            fileName = ARMEABI;
        }else if(isArm7Compatible()){
            fileName = ARMEABI_V7A;
        }else if(isMipsCpu()){
            fileName = MIPS;
        }else if(isMips64Cpu()){
            fileName = MIPS64;
        }else if(isX86Cpu()){
            fileName = X86;
        }else if(isX86_64Cpu()){
            fileName = X86_64;
        }
        return fileName;
    }
}
