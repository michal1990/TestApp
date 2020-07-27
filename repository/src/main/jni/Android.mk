LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := KeysProvider
LOCAL_SRC_FILES := KeysProvider.c

include $(BUILD_SHARED_LIBRARY)