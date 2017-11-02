import	ahp.org.Cartesians.*;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProductIteratorSubunits {
	public static void main(String[] args){
		long time_started = System.nanoTime();

		try {
			int num_items = 0;
			CartesianProductIteratorSubunitRange CPI = new CartesianProductIteratorSubunitRange(
				2, 5
			);
			System.out.println("CPI: "+CPI);
			while( CPI.hasNext() ){
				//System.out.println("before: "+CPI);
				System.out.println("RANGE state: "+CPI.next());
				//System.out.println("after: "+CPI);
				num_items++;
			}
			System.out.println("TestCartesianProduct.java : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }

		try {
			int num_items = 0;
			CartesianProductIteratorSubunitList CPI = new CartesianProductIteratorSubunitList(
				new int[]{7,8,9,10,0,4}
			);
			System.out.println("CPI: "+CPI);
			while( CPI.hasNext() ){
				//System.out.println("before: "+CPI);
				System.out.println("LIST state: "+CPI.next());
				//System.out.println("after: "+CPI);
				num_items++;
			}
			System.out.println("TestCartesianProduct.java : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }

		System.out.println("TestCartesianProductIteratorSubunits.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
