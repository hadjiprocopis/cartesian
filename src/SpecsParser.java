package	ahp.org.Cartesians;

import	java.util.Map;
import	java.util.TreeMap;
import	java.util.ArrayList;
import	java.util.Arrays;

import	java.util.regex.Pattern;
import	java.util.regex.Matcher;

public class SpecsParser {
	// two static methods and all the necessary compiled Regexs to
	// parse the 'Spec' for selecting a cartesian product.
	// The 'Specs' is an array of String, one for each dimension.
	// Each item (the 'Spec') of the array can be a single number e.g. "3"
	// or "1:3", or "3,4,5,6", or "*"
	// At this point note that at each Dimension, adim, we have N[adim] items
	// starting from 0 (ZEROOOO). So for N[0] = 3, a Spec of "*" will result in {0,1,2}
	// 1:3 means a range and BOTH ENDS ARE INCLUSIVE
	// 1,2,3 means what it means

	// note that spec_parser can return for each dimension a
	// broken-down list of indices (e.g. for 1:3 it will return new int[]{1,2,3}
	// OR its compressed version it can return a CartesianProductIteratorUnit[]
	// array whose first and only (for the time being we do not allow
	// 1:3 AND 5:6. If this was allowed then the spec parser would have returned
	// two items in the array (for this dimension).
	// Now why compressed? because 1:10000000 will result in an array
	// of 10000000 items while a CartesianProductIteratorUnitRange
	// can do the same job with less memory.
	// There is no savings for the list so avoid lists if possible.

	// don't forget UTF labels, so \p{L} matches all UTF characters.

	public	final static Pattern Regex_spec_integers_range =
		//Pattern.compile("^([\\(\\[])\\s*(\\p{Digit}+)\\s*\\:\\s*(\\p{Digit}+)\\s*([\\)\\]])$")
		Pattern.compile("^\\s*(\\p{Digit}+)\\s*\\:\\s*(\\p{Digit}+)\\s*$")
	;
	public	final static Pattern Regex_spec_integers_list =
		Pattern.compile("\\s*(\\p{Digit}+)\\s*,?")
	;
	public	final static Pattern Regex_spec_single_integer =
		Pattern.compile("^\\s*(\\p{Digit}+)\\s*$")
	;
	public	final static Pattern Regex_spec_stringlabels_range =
		Pattern.compile("^\\s*([\\p{L}_]+[\\p{L}\\p{Digit}_]*)\\s*\\:\\s*([\\p{L}_][\\p{L}\\p{Digit}_]*)\\s*$")
	;
	public	final static Pattern Regex_spec_stringlabels_list =
		Pattern.compile("\\s*([\\p{L}_][\\p{L}\\p{Digit}_]*)\\s*,?")
	;
	public	final static Pattern Regex_spec_single_stringlabels =
		Pattern.compile("^\\s*([\\p{L}_][\\p{L}\\p{Digit}_]*)\\s*$")
	;
	public	final static Pattern Regex_spec_single_label =
		Pattern.compile("([\\p{L}_][\\p{L}\\p{Digit}_]*)")
	;
	// for each dimension, it returns an array of integers which
	// represent the list of the 'spec'. The spec is made of string labels
	// rather than integer indices.
	public	static int[][] parse_specs(
		String[]	specs_with_labels,
		int[]		numItemsPerDimension,
		// this is the correspondence string_label -> integer_index FOR EACH dimension (the arraylist)
		ArrayList<? extends Map<String, Integer>>	labels2integer_indices
	) throws Exception {
		int myNumDims = numItemsPerDimension.length;
		if( specs_with_labels.length != myNumDims ){ throw new Exception("Parameter 'specs_with_labels[]' must have size (1st) equal to the number of dimensions which is the length of the 'numItemsPerDimension[]' parameter. The lengths are "+specs_with_labels.length+" != "+numItemsPerDimension.length); }

		String specs[] = SpecsParser.labels2integer_indices_spec(specs_with_labels, labels2integer_indices);
		return SpecsParser.parse_specs(specs, numItemsPerDimension);
	}
	// for each dimension, it returns an array of integers which
	// represent the list of the 'spec' - which is wriiten using INTEGERS only (no labels-see above)
	public	static int[][] parse_specs(
		String[]	specs,
		int[]		numItemsPerDimension
	) throws Exception {
		int myNumDims = numItemsPerDimension.length;
		if( specs.length != myNumDims ){ throw new Exception("Parameter 'specs[][]' must have size (1st) equal to the number of dimensions which is the length of the 'numItemsPerDimension[]' parameter. The lengths are "+specs.length+" != "+numItemsPerDimension.length); }
		int	ret[][] = new int[myNumDims][];
		for(int atDim=myNumDims;atDim-->0;){
			ret[atDim] = SpecsParser.parse_spec(
				atDim,
				specs[atDim],
				numItemsPerDimension[atDim]
			);
		}
		return ret;
	}
	public	static CartesianProductIteratorUnit[] parse_specs_compressed_form(
		String[]	specs,
		int[]		numItemsPerDimension
	) throws Exception {
		int myNumDims = numItemsPerDimension.length;
		if( specs.length != myNumDims ){ throw new Exception("Parameter 'specs[][]' must have size (1st) equal to the number of dimensions which is the length of the 'numItemsPerDimension[]' parameter. The lengths are "+specs.length+" != "+numItemsPerDimension.length); }
		CartesianProductIteratorUnit	ret[] =
			new CartesianProductIteratorUnit[myNumDims]
		;
		for(int atDim=myNumDims;atDim-->0;){
			ret[atDim] = SpecsParser.parse_spec_compressed_form(
				atDim,
				specs[atDim],
				numItemsPerDimension[atDim]
			);
		}
		return ret;
	}
	// for each dimension, it returns an array of integers which
	// represent the list of the 'spec'. The spec is made of string labels
	// rather than integer indices.
	public	static CartesianProductIteratorUnit[] parse_specs_compressed_form(
		String[]	specs_with_labels,
		int[]		numItemsPerDimension,
		// this is the correspondence string_label -> integer_index FOR EACH dimension (the arraylist)
		ArrayList<? extends Map<String, Integer>>	labels2integer_indices
	) throws Exception {
		int myNumDims = numItemsPerDimension.length;
		if( specs_with_labels.length != myNumDims ){ throw new Exception("Parameter 'specs_with_labels[]' must have size (1st) equal to the number of dimensions which is the length of the 'numItemsPerDimension[]' parameter. The lengths are "+specs_with_labels.length+" != "+numItemsPerDimension.length); }

		String specs[] = SpecsParser.labels2integer_indices_spec(specs_with_labels, labels2integer_indices);
		return SpecsParser.parse_specs_compressed_form(specs, numItemsPerDimension);
	}
	public	static String label2integer_index_spec(
		String spec,
		Map<String, Integer> labels2indi_for_a_dimension
	) throws Exception {
		StringBuffer sb = new StringBuffer();
		Matcher m = SpecsParser.Regex_spec_single_label.matcher(spec);
		String	a_label;
		Integer an_index;
		while(m.find()) {
			a_label = m.group(1);
			if( (an_index=labels2indi_for_a_dimension.get(a_label)) == null ){ throw new Exception("SpecsParser.java : label2integer_index_spec() : could not find integer index corresponding to label: '"+a_label+"', label2index is "+labels2indi_for_a_dimension+" and spec is '"+spec+"'."); }
			m.appendReplacement(sb, an_index.toString());
		}
		m.appendTail(sb);
		return sb.toString();
	}
	public	static String[] labels2integer_indices_spec(
		String[] specs,
		// this is the correspondence string_label -> integer_index FOR EACH dimension (the arraylist)
		ArrayList<? extends Map<String, Integer>>	labels2indi
	) throws Exception {
		int	L = specs.length;
		String specs_with_integer_indices[] = new String[L];
		for(int atDim=L;atDim-->0;){
			specs_with_integer_indices[atDim] = SpecsParser.label2integer_index_spec(
				specs[atDim], labels2indi.get(atDim)
			);
		}
		return specs_with_integer_indices;
	}
	// give it a spec String for a specific dimension (atDim) and it will
	// return the enumeration of all cases satisfying the spec,
	// e.g. "*" will return an integer array of 0 to myNumItemsPerDimension (0,1,2,3...)
	// "1" will return an array with a single 1 in it
	// 1:3 will return 1,2,3
	// this is specific for each dimension
	// putting all together will result in the cartesian product
	public static	int[] parse_spec(
		int	atDim, // dimension level
		String spec, // e.g "*" or "1:4" or "1" or "1,2,3"
		int	numItemsAtThisDimension // i.e. {0,1,2},{3,4,5,6} -> 3 and 4 items per dimension
	) throws Exception {
		int	i;

		if( spec.equals("*") ){
			int ret[] = new int[numItemsAtThisDimension];
			for(i=numItemsAtThisDimension;i-->0;){ ret[i] = i; }
			//if( debug ){ System.out.println("parse_spec() : matched * ("+spec+") : "+Arrays.toString(ret)); }
			return ret;
		}

		Matcher is_it_a_range = SpecsParser.Regex_spec_integers_range.matcher(spec);
		if( is_it_a_range.matches() ){
			// All range is INCLUSIVE
			// regex Matcher group indices start from 1
			int from = Integer.valueOf(is_it_a_range.group(1));
			// inclusive FROM
			//if( is_it_a_range.group(1).equals("[") ){ from--; }

			int to = Integer.valueOf(is_it_a_range.group(2));
			// inclusive TO
			//if( is_it_a_range.group(2).equals("]") ){ to++; }

			int l = to - from + 1;
			if( l <= 0 ){ throw new Exception("Cartesian::parse_spec() : integer range FROM must not be greater than TO ("+spec+")"); }
			if( to >= numItemsAtThisDimension ){ throw new Exception("Cartesian::parse_spec() : integer range TO must be less than the number of dimensions: "+numItemsAtThisDimension+" ("+spec+")"); }
			if( from >= numItemsAtThisDimension ){ throw new Exception("Cartesian::parse_spec() : integer range FROM must be less than the number of dimensions: "+numItemsAtThisDimension+" ("+spec+")"); }

			int[] ret = new int[l];
			for(i=0;i<l;i++){ ret[i] = i+from; }
			//if( debug ){ System.out.println("parse_spec() : matched range: "+from+":"+to+" ("+spec+") : "+Arrays.toString(ret)); }
			return ret;
		}
		Matcher is_it_a_list = SpecsParser.Regex_spec_integers_list.matcher(spec);
		if( is_it_a_list.find() ){ // matches beginning
			Map<Integer,Integer> alist = new TreeMap<Integer,Integer>();
			do {
				Integer w = new Integer(is_it_a_list.group(1));
				alist.put(w, w);
			} while( is_it_a_list.find() );
			int 	ret[] = new int[alist.size()];
			i = 0;
			for(Map.Entry<Integer, Integer> shit : alist.entrySet()){
				ret[i++] = shit.getValue().intValue();
			}
			//if( debug ){ System.out.println("parse_spec() : matched list of integers: "+Arrays.toString(ret)+" ("+spec+")"); }
			return ret;
		}
		Matcher is_single_integer = SpecsParser.Regex_spec_single_integer.matcher(spec);
		if( is_single_integer.matches() ){
			//if( debug ){ System.out.println("parse_spec() : matched singleton "+is_single_integer.group(1)+" ("+spec+") : "+is_single_integer.group(1)); }
			return new int[]{ Integer.valueOf(is_single_integer.group(1)) };
		}
		// parsed failed for given spec:
		throw new Exception("Cartesian::parse_spec() : could not parse spec: '"+spec+"'.");
	}
	public static	CartesianProductIteratorUnit parse_spec_compressed_form(
		int	atDim, // dimension level
		String spec, // e.g "*" or "1:4" or "1" or "1,2,3"
		int	numItemsAtThisDimension // i.e. {0,1,2},{3,4,5,6} -> 3 and 4 items per dimension
	) throws Exception {
		int	i;

		if( spec.equals("*") ){
			//System.out.println("parse_spec_compressed_form() : spec '"+spec+"' : it is wildcard: 0 to "+(numItemsAtThisDimension-1));
			CartesianProductIteratorUnit ret =  new CartesianProductIteratorUnit(
				new CartesianProductIteratorSubunitRange(0, numItemsAtThisDimension-1)
			);
			return ret;
		}

		Matcher is_it_a_range = SpecsParser.Regex_spec_integers_range.matcher(spec);
		if( is_it_a_range.matches() ){
			// All range is INCLUSIVE
			// regex Matcher's group indices start from 1
			int from = Integer.valueOf(is_it_a_range.group(1));
			// inclusive FROM
			//if( is_it_a_range.group(1).equals("[") ){ from--; }

			int to = Integer.valueOf(is_it_a_range.group(2));
			// inclusive TO
			//if( is_it_a_range.group(2).equals("]") ){ to++; }

			if( (to-from) < 0 ){ throw new Exception("Cartesian::parse_spec_compressed_form() : integer range FROM must not be greater than TO ("+spec+")"); }
			if( to >= numItemsAtThisDimension ){ throw new Exception("Cartesian::parse_spec_compressed_form() : integer range TO must be less than the number of dimensions: "+numItemsAtThisDimension+" ("+spec+")"); }
			if( from >= numItemsAtThisDimension ){ throw new Exception("Cartesian::parse_spec_compressed_form() : integer range FROM must be less than the number of dimensions: "+numItemsAtThisDimension+" ("+spec+")"); }
			//System.out.println("parse_spec_compressed_form() : spec '"+spec+"' : it is a range: "+from+" to "+to);
			CartesianProductIteratorUnit ret =  new CartesianProductIteratorUnit(
				new CartesianProductIteratorSubunitRange(from, to)
			);
			return ret;
		}

		Matcher is_it_a_list = SpecsParser.Regex_spec_integers_list.matcher(spec);
		if( is_it_a_list.find() ){ // matches beginning
			Map<Integer,Integer> alist = new TreeMap<Integer,Integer>();
			do {
				Integer w = new Integer(is_it_a_list.group(1));
				alist.put(w, w);
			} while( is_it_a_list.find() );
			int 	thelist[] = new int[alist.size()];
			i = 0;
			for(Map.Entry<Integer, Integer> shit : alist.entrySet()){
				thelist[i++] = shit.getValue().intValue();
			}
			//System.out.println("parse_spec_compressed_form() : spec '"+spec+"' : it is a list: "+Arrays.toString(thelist));
			CartesianProductIteratorUnit ret =  new CartesianProductIteratorUnit(
				new CartesianProductIteratorSubunitList(thelist)
			);
			return ret;
		}

		Matcher is_single_integer = SpecsParser.Regex_spec_single_integer.matcher(spec);
		if( is_single_integer.matches() ){
			//if( debug ){ System.out.println("parse_spec_compressed_form() : matched singleton "+is_single_integer.group(1)+" ("+spec+") : "+is_single_integer.group(1)); }
			int the_integer = Integer.valueOf(is_single_integer.group(1));
			//System.out.println("parse_spec_compressed_form() : spec '"+spec+"' : it is a single integer: "+the_integer);
			CartesianProductIteratorUnit ret =  new CartesianProductIteratorUnit(
				new CartesianProductIteratorSubunitList(
					new int[]{the_integer}
				)
			);
			return ret;
		}
		// parsed failed for given spec:
		throw new Exception("Cartesian::parse_spec_compressed_form() : could not parse spec: '"+spec+"'.");
	}
}
