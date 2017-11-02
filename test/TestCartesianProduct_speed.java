import	ahp.org.Cartesians.Cartesian;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProduct_speed {
	public static void main(String[] args){
		long time_started = System.nanoTime();
		Cartesian ca = new Cartesian(
			new int[]{100,100,100,100}
		);
		try {
			int ret[][] = ca.product(
				// the string array must contain
				// as many items AS THE NUM OF DIMENSIONS (above)
				// ranges are inclusive
				// indices start from ZERO (naturally)

				// 100 million items: 1 min
				new String[]{"*", "*", "*", "*"}
			);
			System.out.println("done: "+ret.length+" items in the cartesian product of "+ca.num_dimensions()+" dimensions.");
//			System.out.println(Cartesian.toString_arraylist(ret));
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }

		System.out.println("TestCartesianProduct_speed.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
