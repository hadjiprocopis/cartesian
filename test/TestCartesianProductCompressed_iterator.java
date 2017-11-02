import	ahp.org.Cartesians.*;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProductCompressed_iterator {
	public static void main(String[] args){
		long time_started = System.nanoTime();

		String[] specs = new String[]{"0,1,2", "5:6", "0,1"};
//		String[] specs = new String[]{"*", "*", "1"};
		int num_items = 0;
		try {
			CartesianProductIteratorCompressed CPI = new CartesianProductIteratorCompressed(
				new int[]{3,7,3},
				specs
			);
			System.out.println("CPI: "+CPI);
			while( CPI.hasNext() ){
				//System.out.println("before: "+CPI);
				System.out.println("state: "+Arrays.toString(CPI.next()));
				//System.out.println("after: "+CPI);
				num_items++;
			}
			System.out.println("TestCartesianProduct.java : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }
		System.out.println("TestCartesianProductCompressed_iterator.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
