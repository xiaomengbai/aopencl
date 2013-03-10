##LOCAL_PATH := $(call my-dir)
##include $(CLEAR_VARS)
##LOCAL_LDLIBS := -ldl 
##LOCAL_MODULE    := helloworld2
##LOCAL_CFLAGS := -g
##LOCAL_CXXFLAGS += -fno-exceptions
##LOCAL_SRC_FILES := aopencl.c helloworld.c
#LOCAL_SRC_FILES := helloworld.c
#include $(BUILD_SHARED_LIBRARY)  
##include $(BUILD_EXECUTABLE)

LOCAL_PATH := $(call my-dir)

# Define vars for library that will be build statically.
include $(CLEAR_VARS)
LOCAL_MODULE := aopencl
LOCAL_C_INCLUDES := aopencl.h
LOCAL_SRC_FILES :=  aopencl.c

# Optional compiler flags.
LOCAL_LDLIBS   = -lz -lm
LOCAL_CFLAGS   = -Wall -pedantic -std=c99 -g

include $(BUILD_STATIC_LIBRARY)

# First lib, which will be built statically.
include $(CLEAR_VARS)
LOCAL_MODULE := helloworld2
LOCAL_STATIC_LIBRARIES := aopencl
LOCAL_C_INCLUDES := aopencl_def.h
LOCAL_SRC_FILES := HelloWorld.cpp

#include $(BUILD_SHARED_LIBRARY)
include $(BUILD_EXECUTABLE)
