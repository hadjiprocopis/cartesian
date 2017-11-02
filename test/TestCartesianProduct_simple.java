import	ahp.org.Cartesians.Cartesian;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProduct_simple {
	public static void main(String[] args){
		long time_started = System.nanoTime();
		Cartesian ca = new Cartesian(
			new int[]{3,3,3}
		);
		String[] specs = new String[]{"1,2,2", "*", "*"};
		try {
			int ret[][] = ca.product(
				// the string array must contain
				// as many items AS THE NUM OF DIMENSIONS (above)
				// ranges are inclusive
				// indices start from ZERO (naturally)
//				new String[]{"1:2", "*", "*"}
				specs
			);
			System.out.println("done: "+ret.length+" items. Here is the list for the spec: "+Arrays.toString(specs)+":");
			System.out.println(Cartesian.toString_arraylist(ret));
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }

		System.out.println("TestCartesianProduct_simple.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
