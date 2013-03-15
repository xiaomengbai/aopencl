package org.aopencl.aparapi;

import com.amd.aparapi.Kernel;


public class MyKernel extends Kernel {
	public final int size;
	/** Input float array for which square values need to be computed. */
	public final int[] values;
	/** Output array which will be populated with square values of corresponding input array elements. */
	public final int[] squares;
    
	public MyKernel(int size){
		this.size=size;
		values = new int[size];
		squares = new int[size];
		init();
	}
	/** Initialize input array. */
	private void init(){
		for (int i = 0; i < size; i++) {
			values[i] = i;
		}
	}


	/** Aparapi Kernel which computes squares of input array elements and populates them in corresponding elements of 
	 * output array. 
	 **/
	@Override public void run() {
		int gid = getGlobalId();
		squares[gid] = values[gid] + values[gid]/2;
	}

}
