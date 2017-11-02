# cartesian

author: andreas hadjiprocopis (andreashad2@gmail.com)

Cartesian product enumerator.

Ha?

It will list all integer combinations in an n-dimensional
integer space.

Why is this useful? Well for getting slices
out of n-dimensional arrays for example.


Let's see what an example in 3 dimensions.

1st dimension: spans from 0 to 2 (3 elements)<br/>
2nd dimension: spans from 0 to 2<br/>
3rd dimension: spans from 0 to 3<br/>

```
import  ahp.org.Cartesians.*;

import  java.util.ArrayList;
import  java.util.Arrays;

public  class   TestCartesianProduct_iterator {
	public static void main(String[] args){
		long time_started = System.nanoTime();

		int num_items = 0;
		try {
			CartesianProductIterator CPI = new CartesianProductIterator(
				// 3 dimensions with 3,3,4 items each respectively
				new int[]{3,3,4},
			);
			// enumerate, list all integer combinations in there
			while( CPI.hasNext() ){
				System.out.println(Arrays.toString(CPI.next()));
				num_items++;
			}
			System.out.println("main() : produced "+num_items+" items.");
		} catch(Exception ex){ System.err.println("main() : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }
		System.out.println("main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
```

and here is the result:
```
     [java] [0, 0, 0]
     [java] [0, 0, 1]
     [java] [0, 0, 2]
     [java] [0, 0, 3]
     [java] [0, 1, 0]
     [java] [0, 1, 1]
     [java] [0, 1, 2]
     [java] [0, 1, 3]
     [java] [0, 2, 0]
     [java] [0, 2, 1]
     [java] [0, 2, 2]
     [java] [0, 2, 3]
     [java] [1, 0, 0]
     [java] [1, 0, 1]
     [java] [1, 0, 2]
     [java] [1, 0, 3]
     [java] [1, 1, 0]
     [java] [1, 1, 1]
     [java] [1, 1, 2]
     [java] [1, 1, 3]
     [java] [1, 2, 0]
     [java] [1, 2, 1]
     [java] [1, 2, 2]
     [java] [1, 2, 3]
     [java] [2, 0, 0]
     [java] [2, 0, 1]
     [java] [2, 0, 2]
     [java] [2, 0, 3]
     [java] [2, 1, 0]
     [java] [2, 1, 1]
     [java] [2, 1, 2]
     [java] [2, 1, 3]
     [java] [2, 2, 0]
     [java] [2, 2, 1]
     [java] [2, 2, 2]
     [java] [2, 2, 3]
     [java] main() : produced 36 items.
     [java] main() : time taken: 10 milli seconds.
```

In the following example we get all the integer combinations in
an array:
```
import  ahp.org.Cartesians.Cartesian;

import  java.util.ArrayList;
import  java.util.Arrays;

public  class   TestCartesianProduct_speed {
	public static void main(String[] args){
		long time_started = System.nanoTime();
		Cartesian ca = new Cartesian(
			// 4 dimensional integer space with 100 items in each dimension
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
				// use this stricter spec to get fewer items
				// the spec: '*' means everything, i.e. all from 0 to 99
				// '5' means fix it to just the number 5
				// '10:30' means all numbers from 10 to 30
				// '3,40' means just 3 and 40
				// new String[]{"*", "5", "10:30", "3,4,59,67"}
			);
			System.out.println("done: "+ret.length+" items in the cartesian product of "+ca.num_dimensions()+" dimensions.");
//		      System.out.println(Cartesian.toString_arraylist(ret));
		} catch(Exception ex){ System.err.println("TestCartesianProduct.java : exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }

		System.out.println("TestCartesianProduct_speed.java : main() : time taken: "+((System.nanoTime()-time_started)/1000000)+" milli seconds.");
	}
}
```
author: andreas hadjiprocopis (andreashad2@gmail.com)
