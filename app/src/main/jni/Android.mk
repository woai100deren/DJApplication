LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# 要生成的.so库名称。
LOCAL_MODULE := dynamic

# C++文件
LOCAL_SRC_FILES := dynamicso.c

include $(BUILD_SHARED_LIBRARY)