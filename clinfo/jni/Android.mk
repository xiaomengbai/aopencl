LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS := -ldl -llog
LOCAL_MODULE    := aopenclClInfo  
LOCAL_CXXFLAGS += -fno-exceptions
LOCAL_SRC_FILES := clInfoCore.c clInfoCL.c
include $(BUILD_SHARED_LIBRARY)  
