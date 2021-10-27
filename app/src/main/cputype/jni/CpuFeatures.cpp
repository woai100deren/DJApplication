//
// Created by Administrator on 2017/5/4.
//

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include "cpu-features.h"
#include <jni.h>
#include <string.h>
extern "C"{

//_com_dj_cpu_
//com.example.administrator.cpu2
JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isArmCpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();

    //以下是调研jni如何调用java方法
    //获取java的类
    jclass javaClass =  (jclass) env->NewGlobalRef(env->FindClass("com/dj/cpu/CPUFrameworkHelper"));
    //获取java的构造函数
    jmethodID constructorID = env->GetMethodID(javaClass,"<init>","()V");
    //获取java的非静态方法：printString
    jmethodID methodID = env->GetMethodID(javaClass,"printString","()V");
    //获取java的静态方法：staticPrintString
    jmethodID staticMethodID = env->GetStaticMethodID(javaClass,"staticPrintString","()V");
    //调用构造函数生成java对象
    jobject javaClassObject = env->NewObject(javaClass,constructorID);
    //执行非静态方法，没有返回值用CallVoidMethod
    env->CallVoidMethod(javaClassObject,methodID);
    //执行静态方法，没有返回值用CallStaticVoidMethod
    env->CallStaticVoidMethod(javaClass,staticMethodID);

    return cpuFamily == ANDROID_CPU_FAMILY_ARM;
}

JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isArm7Compatible(JNIEnv *env, jclass type)
{
    uint64_t cpuFeatures = android_getCpuFeatures();
    return (cpuFeatures & ANDROID_CPU_ARM_FEATURE_ARMv7) == ANDROID_CPU_ARM_FEATURE_ARMv7;
}


JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isMipsCpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();
    return cpuFamily == ANDROID_CPU_FAMILY_MIPS;
}



JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isX86Cpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();
    return cpuFamily == ANDROID_CPU_FAMILY_X86;
}


JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isArm64Cpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();
    return cpuFamily == ANDROID_CPU_FAMILY_ARM64;
}

JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isMips64Cpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();
    return cpuFamily == ANDROID_CPU_FAMILY_MIPS64;
}


JNIEXPORT jboolean JNICALL
Java_com_dj_cpu_CPUFrameworkHelper_isX86_164Cpu(JNIEnv *env, jclass type)
{
    AndroidCpuFamily cpuFamily = android_getCpuFamily();
    return cpuFamily == ANDROID_CPU_FAMILY_X86_64;
}


static JNINativeMethod gMethods[] = {
        {"isArmCpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isArmCpu},
        {"isArm7Compatible", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isArm7Compatible},
        {"isMipsCpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isMipsCpu},
        {"isX86Cpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isX86Cpu},
        {"isArm64Cpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isArm64Cpu},
        {"isMips64Cpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isMips64Cpu},
        {"isX86_64Cpu", "()Z",
                (void *) Java_com_dj_cpu_CPUFrameworkHelper_isX86_164Cpu},
};


int register_android_os_cts_CpuFeatures(JNIEnv* env)
{
    jclass clazz = env->FindClass("android/os/cts/CPUFrameworkHelper");
    return env->RegisterNatives(clazz, gMethods,
                                sizeof(gMethods) / sizeof(JNINativeMethod));
}



}