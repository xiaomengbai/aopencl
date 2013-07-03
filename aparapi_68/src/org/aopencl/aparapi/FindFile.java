package org.aopencl.aparapi;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;


public class FindFile {

	public static String[] findFiles(String root, String pat, boolean ignoreCase){
		File f = new File(root);
		if( !f.exists() || !f.isDirectory()){
			System.err.println("File path does not exist or not a directory:"+root);
			return new String[0];
		}
		if(pat == null) pat = "^.*$";
		Pattern pattern = Pattern.compile(pat , ignoreCase ? Pattern.CASE_INSENSITIVE: 0);
		ArrayList<String> files=new ArrayList<String>();
		walk(f,pattern,files);
		return files.toArray(new String[0]);
	}

	public static void walk(File path, Pattern pat ,ArrayList<String> files){
		File[] list = path.listFiles();
		if( list == null || list.length == 0) return; 
		for ( File f : list ) {
			if ( f.isDirectory() ) {
				walk( f ,pat,files);
			}	else {
				String fqdn = f.getAbsolutePath();
				fqdn=fqdn.replace('\\', '/');
				if(pat.matcher(fqdn).matches()){
					files.add(fqdn);
				}
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("Find com.*dll:" + Arrays.toString(findFiles("c:/windows","^.*com.*\\.dll$",true)));

//		System.out.println("Find com.*dll:" + Arrays.toString(
//				findFiles("c:/windows","^.*/[0-9a-zA-Z .\\-]*com[0-9a-zA-Z \\.\\-]*\\.dll$",true)).replace(',','\n'));
		
		System.out.println("\n\nFind opencl.*dll:" + Arrays.toString(
				findFiles("c:/windows","^.*/[0-9a-zA-Z .\\-]*opencl[0-9a-zA-Z \\.\\-]*\\.dll$",true)).replace(',','\n'));
		
		System.out.println("\n\nFind opencl.*dll:" + Arrays.toString(
				findFiles("c:/windows","^.*/[0-9a-zA-Z .\\-]*opencl[0-9a-zA-Z \\.\\-]*\\.dll[0-9a-zA-Z.\\-]*$",true)).replace(',','\n'));
	}

}
