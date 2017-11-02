import	ahp.org.Cartesians.*;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProduct_iterator {
	public static void main(String[] args){
		long time_started = System.nanoTime();

//		String[] specs = new String[]{"0,1,2", "1:2", "*"};
		String[] specs = new String[]{"*", "*", "1"};
		int num_items = 0;
		try {
			CartesianProductIterator CPI = new CartesianProductIterator(
				new int[]{3,3,3},
				specs
			);
			while( CPI.hasNext() ){
				//System.out.println("before: "+CPI);
				System.out.println(Arrays.toString(CPI.next()));
				//System.out.println("after: "+CPI);
				num_items++;
			}
			System.out.println("TestCartesianProduct.java : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }
		System.out.println("TestCartesianProduct_iterator.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
