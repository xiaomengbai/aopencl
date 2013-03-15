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
LOCAL_MODULE := aparapi
LOCAL_C_INCLUDES := aopencl.h
#LOCAL_SRC_FILES :=  aopencl.c agent.cpp classtools.cpp CLHelper.cpp JNIHelper.cpp aparapi.cpp
LOCAL_SRC_FILES :=  aopencl.c aparapi.cpp

# Optional compiler flags.
LOCAL_LDLIBS   = -lz -lm -llog
LOCAL_CFLAGS   = -Wall -pedantic -std=c99 -g
LOCAL_CFLAGS   = -Wall -pedantic -Wno-variadic-macros  -g
include $(BUILD_SHARED_LIBRARY)  
#include $(BUILD_STATIC_LIBRARY)

# Now executable using C++
###include $(CLEAR_VARS)
###LOCAL_MODULE := helloworld2
# Optional compiler flags.
###LOCAL_LDLIBS   = -llog
#LOCAL_CFLAGS   = -Wall -pedantic -g
###LOCAL_STATIC_LIBRARIES := aopencl
###LOCAL_C_INCLUDES := aopencl_def.h
###LOCAL_SRC_FILES := HelloWorld.cpp

#include $(BUILD_SHARED_LIBRARY)
###include $(BUILD_EXECUTABLE)
