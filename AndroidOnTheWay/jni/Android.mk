LOCAL_PATH := $(call my-dir)
 
include $(CLEAR_VARS) 
 
LOCAL_MODULE    := TestJNI
LOCAL_SRC_FILES := com_demo_androidontheway_testjni_TestJNI.cpp
LOCAL_SRC_FILES += Add.cpp
 
include $(BUILD_SHARED_LIBRARY)