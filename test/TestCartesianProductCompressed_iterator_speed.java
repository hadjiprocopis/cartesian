import	ahp.org.Cartesians.*;

import	java.util.ArrayList;
import	java.util.Arrays;

public	class	TestCartesianProductCompressed_iterator_speed {
	public static void main(String[] args){
		long time_started = System.nanoTime();

		String specs[] = new String[]{"*", "*", "*", "*", "*"};
		int dims[] = new int[]{50,50,50,50,50};
		int num_dims = dims.length;
		try {
			CartesianProductIteratorCompressed CPI = new CartesianProductIteratorCompressed(
				dims,
				specs
			);
			//System.out.println("CPI: "+CPI);
			System.out.print("TestCartesianProductCompressed_iterator_speed : testing speed for dims: "+Arrays.toString(dims)+" with the spec: "+Arrays.toString(specs)+" :");
			// we use this pre-allocated array to get the state. calling next() will allocate an array every time!
			int	state[] = new int[num_dims];
			int num_items = 1;
			while( CPI.hasNext() ){
				CPI.next(state);
				//System.out.println("before: "+CPI);
				//System.out.println("state ("+num_items+"): "+Arrays.toString(state));
				//System.out.println("after: "+CPI);
				num_items++;
				if( num_items % 1000000 == 0 ){ System.out.print(" "+num_items); System.out.flush(); }
			}
			num_items--;
			System.out.println("\nTestCartesianProductCompressed_iterator_speed.java : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("TestCartesianProductCompressed_iterator_speed.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }
		System.out.println("TestCartesianProductCompressed_iterator_speed.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
