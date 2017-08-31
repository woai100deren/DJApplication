LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

# 要生成的.so库名称。
LOCAL_MODULE := cpulib

# C++文件
LOCAL_SRC_FILES := CpuFeatures.cpp

include $(BUILD_SHARED_LIBRARY)