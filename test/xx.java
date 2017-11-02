import  ahp.org.Cartesians.*;

import  java.util.ArrayList;
import  java.util.Arrays;

public  class   xx {
        public static void main(String[] args){
                long time_started = System.nanoTime();

                int num_items = 0;
                try {
                        CartesianProductIterator CPI = new CartesianProductIterator(
                                // 3 dimensions with 3,3,4 items each respectively
                                new int[]{3,3,4}
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
