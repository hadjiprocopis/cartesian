import	java.util.ArrayList;
import	java.util.Map;
import	java.util.HashMap;

import ahp.org.Cartesians.*;

public class TestSpecsParser {
	public static void main(String args[]){
	try {
		int	i;
		int dims[] = new int[]{ 3, 5, 10 };
		// we can have specs with integer indices, e.g. 1,2,3
		// or specs with label indices (a:c) provided we provide
		// a arraylist of hashmaps with the correspondences

		// data for integer specs
		String specs[] = new String[]{"*", "1:3", "4,5,6"};
		// data for labels specs
		ArrayList<HashMap<String, Integer>> labels2ints = new ArrayList<HashMap<String, Integer>>();
		for(i=0;i<dims.length;i++){
			HashMap<String, Integer> amap = new HashMap<String, Integer>();
			amap.put("a", 1);
			amap.put("b", 2);
			amap.put("c", 3);
			amap.put("d", 4);
			amap.put("shit_121", 5);
			labels2ints.add(amap);
		}
		String labelspecs[] = new String[]{
			"a,b,shit_121,d", "*", "a:c"
		};

		System.out.println("TestSpecsParser.java : checking specs with integer indices (easy):");
		CartesianProductIteratorUnit units_from_integer_specs[] = SpecsParser.parse_specs_compressed_form(
			specs, dims
		);
		for(i=0;i<units_from_integer_specs.length;i++){
			System.out.println("integer spec for dim "+(i+1)+": "+units_from_integer_specs[i].toString());
		}

		System.out.println("TestSpecsParser.java : checking specs with labels:");
		CartesianProductIteratorUnit units_from_label_specs[] = SpecsParser.parse_specs_compressed_form(
			labelspecs, dims, labels2ints
		);
		for(i=0;i<units_from_label_specs.length;i++){
			System.out.println("label spec for dim "+(i+1)+": "+units_from_label_specs[i].toString());
		}

		
	} catch(Exception ex){ System.err.println("Exception was caught:\n"+ex); ex.printStackTrace(); System.exit(1); }
	} // main
}
