//
// Created by wangjing4 on 2017/7/6.
//

#include "com_dynamicso_SoUtils.h"
/*
 * Class:     com_dynamicso_SoUtils
 * Method:    getDynamicsoString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_dynamicso_SoUtils_getDynamicsoString
  (JNIEnv *env, jobject obj){
     return (*env)->NewStringUTF(env,"dynamic so!");
  }
