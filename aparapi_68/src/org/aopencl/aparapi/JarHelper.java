package org.aopencl.aparapi;

import java.io.*;
import java.util.zip.*;
import android.content.res.Resources;
import org.aopencl.aparapi.R;

public class JarHelper {
    static Resources currentResources;
    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
    	unzip(new FileInputStream(zipFilePath),destDirectory);
    }
    public static void unzip(InputStream is, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(is);
        ZipEntry entry = zipIn.getNextEntry();
        System.out.println(entry.getClass().getName());
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
            	System.out.println(filePath);
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
            	
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    private static byte[]  extractBytes(ZipInputStream zipIn) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
	int sum=0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
	    sum += read;
        }
        System.out.println("extractBytes:"+sum);
	
        bos.close();
        return bos.toByteArray();
    }
    public static InputStream getClassByteStream(String className) throws IOException {
	    try{
		    byte[] ret=getClassBytes(currentResources.openRawResource(R.raw.myapp),className);
		    ByteArrayInputStream bis = new ByteArrayInputStream(ret);
		    return bis;
	    }catch(IOException e){
		    throw new RuntimeException(e);
	    }
    }
    public static byte[] getClassBytes(String className)  {
	try{
		System.out.printf("getClassBytes Needed for :%s , res-id:%x\n",className,R.raw.myapp);
		return getClassBytes(currentResources.openRawResource(R.raw.myapp),className);
	}catch(IOException e){
		throw new RuntimeException(e);
	}
    }
    public static byte[] getClassBytes(InputStream is, String className) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(is);
        ZipEntry entry = zipIn.getNextEntry();
        System.out.println("getClassBytes:"+entry.getClass().getName()+", getting bytes for:"+className);
        byte[] ret=new byte[0];
        try{
        	// iterates over entries in the zip file
        	while (entry != null) {
        		String klassName = entry.getName();
        		if (!entry.isDirectory()) {
        			if(klassName.equals(className)){
        				// 	if the entry is a file, extracts it
        				System.out.println("Got :"+klassName +", size:"+entry.getSize());
        				ret=extractBytes(zipIn);
        				break;
        			}
        		} 
        		zipIn.closeEntry();
        		entry = zipIn.getNextEntry();
        	}
        }finally{
        	if(zipIn != null) zipIn.closeEntry();
        	close(zipIn);
        }
        return ret;
    } 
    private static  void close(InputStream is) {
    	try {
			if( is != null)
				is.close();
		} catch (IOException e) {
		}
	}
	public static void main(String[] args) {
        String jarFilePath = "/home/gssm/Myjar.jar";
        String destDirectory = "/tmp/my/";
        try {
            JarHelper.unzip(jarFilePath, destDirectory);
            //JarHelper.getClassBytes(jarFilePath,"com/amd/aparapi/KernelRunner.class");
        } catch (Exception ex) {
            // some errors occurred
            ex.printStackTrace();
        }
    }
}
