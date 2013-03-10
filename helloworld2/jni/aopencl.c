/*******************************************************************************
 * Copyright (c) 2013  GSS Mahadevan
 *
 ******************************************************************************/
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wvariadic-macros"
#include <stdio.h>
#pragma GCC diagnostic pop

#include <CL/cl.h>
#include <dlfcn.h>
#include <string.h>

#include "aopencl.h"

static int loadedCL;
#define LIB_OPENCL "/system/lib/libOpenCL.so"
#define LIB_GLES_MALI "/system/vendor/lib/egl/libGLES_mali.so"
#define LIB_LLVM   "/system/lib/libllvm-a3xx.so"

#define GET_SYM(fn) a##fn
#define MAP_SYM(fn) *(void **) (&GET_SYM(fn)) = dlsym(handle,#fn);IAH();

static const char* DEF_SO_LOCS[]={
	LIB_OPENCL, LIB_GLES_MALI, LIB_LLVM
	};
static const int DEF_SO_LOCS_LEN = 3;

static void *getCLHandle(const char** libs,int len){
  int i=0;
  printf("Trying shared library at following locations:");
  for(;i<len;i++  ){
      printf("%s, ",libs[i]);
  }
  printf("\n");
  i=0;
  for(;i<len;i++  ){
    void* so = dlopen(libs[i],RTLD_LAZY);
    if ( so != 0 ){
      printf("Using the Shared libarey:%s\n",libs[i]);
      return so;
    }
  }
  printf("Could not resolve with any of above shared libraries\n");
  return 0;
}

void initFns(const char** libs, int len){
  const char** libs_ = ( libs == 0 ? DEF_SO_LOCS : libs);
  len = ( libs == 0 ? DEF_SO_LOCS_LEN : len);
  
  loadedCL = 0;
  void *handle = getCLHandle(libs_,len);
  if(handle==NULL) return;
  MAP_SYM(clGetPlatformIDs);
  MAP_SYM(clGetPlatformInfo);
  MAP_SYM(clGetDeviceIDs);
  MAP_SYM(clGetDeviceInfo);
  MAP_SYM(clCreateContext);
  MAP_SYM(clCreateContextFromType);
  MAP_SYM(clRetainContext);
  MAP_SYM(clReleaseContext);
  MAP_SYM(clGetContextInfo);
  MAP_SYM(clCreateCommandQueue);
  MAP_SYM(clRetainCommandQueue);
  MAP_SYM(clReleaseCommandQueue);
  MAP_SYM(clGetCommandQueueInfo);
  MAP_SYM(clCreateBuffer);
  MAP_SYM(clCreateSubBuffer);
  MAP_SYM(clRetainMemObject);
  MAP_SYM(clReleaseMemObject);
  MAP_SYM(clGetSupportedImageFormats);
  MAP_SYM(clGetMemObjectInfo);
  MAP_SYM(clGetImageInfo);
  MAP_SYM(clSetMemObjectDestructorCallback);
  MAP_SYM(clCreateSampler);
  MAP_SYM(clRetainSampler);
  MAP_SYM(clReleaseSampler);
  MAP_SYM(clGetSamplerInfo);
  MAP_SYM(clCreateProgramWithSource);
  MAP_SYM(clCreateProgramWithBinary);
  MAP_SYM(clRetainProgram);
  MAP_SYM(clReleaseProgram);
  MAP_SYM(clBuildProgram);
  MAP_SYM(clGetProgramInfo);
  MAP_SYM(clGetProgramBuildInfo);
  MAP_SYM(clCreateKernel);
  MAP_SYM(clCreateKernelsInProgram);
  MAP_SYM(clRetainKernel);
  MAP_SYM(clReleaseKernel);
  MAP_SYM(clSetKernelArg);
  MAP_SYM(clGetKernelInfo);
  MAP_SYM(clGetKernelWorkGroupInfo);
  MAP_SYM(clWaitForEvents);
  MAP_SYM(clGetEventInfo);
  MAP_SYM(clCreateUserEvent);
  MAP_SYM(clRetainEvent);
  MAP_SYM(clReleaseEvent);
  MAP_SYM(clSetUserEventStatus);
  MAP_SYM(clSetEventCallback);
  MAP_SYM(clGetEventProfilingInfo);
  MAP_SYM(clFlush);
  MAP_SYM(clFinish);
  MAP_SYM(clEnqueueReadBuffer);
  MAP_SYM(clEnqueueReadBufferRect);
  MAP_SYM(clEnqueueWriteBuffer);
  MAP_SYM(clEnqueueWriteBufferRect);
  MAP_SYM(clEnqueueCopyBuffer);
  MAP_SYM(clEnqueueCopyBufferRect);
  MAP_SYM(clEnqueueReadImage);
  MAP_SYM(clEnqueueWriteImage);
  MAP_SYM(clEnqueueCopyImage);
  MAP_SYM(clEnqueueCopyImageToBuffer);
  MAP_SYM(clEnqueueCopyBufferToImage);
  MAP_SYM(clEnqueueUnmapMemObject);
  MAP_SYM(clEnqueueNDRangeKernel);
  MAP_SYM(clEnqueueTask);
  MAP_SYM(clEnqueueNativeKernel);
  //MAP_SYM(clSetCommandQueueProperty);
//  MAP_SYM(clCreateImage2D);
  //MAP_SYM(clCreateImage3D);
//  MAP_SYM(clEnqueueMarker);
  //MAP_SYM(clEnqueueWaitForEvents);
//  MAP_SYM(clEnqueueBarrier);
//  MAP_SYM(clUnloadCompiler);

  loadedCL = 1;
}



