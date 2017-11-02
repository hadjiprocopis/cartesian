package	ahp.org.Cartesians;

import	java.util.Arrays;

public class Cartesian {
	private	int	myNumDims;
	private	int[]	myNumItemsPerDimension;
	private	int[][] scratch = null;
	private	int	myScratchFilledSize = 0;

	private	boolean	debug = true;

	public	Cartesian(
		int	num_items_per_dimension[]
	){
		this.myNumDims = num_items_per_dimension.length;
		this.myNumItemsPerDimension = num_items_per_dimension.clone();
	}

	// the iterator implements Iterator (hasNext and next()) over the space
	// of the optional specs e.g. "1:2", "4" will return 1,4 and 2,4 after each next() call (returns an int[])
	public	CartesianProductIterator iterator(String[] specs) throws Exception {
		return new CartesianProductIterator(
			this.myNumItemsPerDimension,
			specs
		);
	}
	public	CartesianProductIterator iterator() throws Exception {
		return new CartesianProductIterator(
			this.myNumItemsPerDimension
		);
	}
	// enumerate the cartesian product according to the specified spec
	// indices in the spec start from ZERO.
	// the number of items in the spec String array must be equal to the number of
	// dimensions specified in the constructor.
	public	int[][] product(
		String[] specs
	) throws Exception {
		if( specs.length != this.myNumDims ){ throw new Exception("Cartesian.java : product() : the number of dimensions set at the constructor ("+this.myNumDims+") must be the same as the items in the given spec ("+specs.length+") : spec has invalid number of items."); }

		// first of all parse all the spec strings (one for each dimension)
		// save the results and get the total size of the cartesian product: total_size
		int	total_size = 1, i;
		int specs_parsed[][] = SpecsParser.parse_specs(specs, this.myNumItemsPerDimension);
		for(i=this.myNumDims;i-->0;){ total_size *= specs_parsed[i].length; }

		// allocate space for the array to hold the cartesian product
		this.scratch = new int[total_size][this.myNumDims];
		this.myScratchFilledSize = 0;
		int	num_items_in_product_so_far = 0;
		for(i=this.myNumDims;i-->0;){
			// enumerate the cartesian product
			// result is the total number of items in the product SO FAR (so not: +=)
			num_items_in_product_so_far = p1(specs_parsed[i], i);
		}
		// at the end, what we calculated above as total_size of the product
		// must be equal to the size of the array which stores the product
		assert( num_items_in_product_so_far == this.scratch.length) : "Cartesian.java : product() : ASSERT ERROR: "+num_items_in_product_so_far+" != "+this.scratch.length;
		return this.scratch;
	}
	private	int	p1(
		int[] toCombine, // with scratch
		int   atDim // which dimension to insert newly items? (i.e. at List<int[aDim]>)
	){
		int	num_combine = toCombine.length, i, j, w;
		if( this.myScratchFilledSize == 0 ){
			// first iteration
			for(i=num_combine;i-->0;){
				// in java an array of integers is automatically initialised to ZERO
				int[] shit = new int[this.myNumDims];
				shit[this.myNumDims-1] = toCombine[i];
				this.scratch[i] = shit;
			}
			this.myScratchFilledSize = num_combine;
			//System.out.println("first:\n"+Cartesian.toString_arraylist(this.scratch));
			return this.myScratchFilledSize;
		}

		// not the first time:
		int	new_total_num_items = num_combine * this.myScratchFilledSize;
		//System.out.println("already has: "+this.myScratchFilledSize+" and comb="+num_combine);
		//if( new_total_num_items > this.myScratchFilledSize ){ System.out.println("ensuring capacity : "+new_total_num_items+", dim="+atDim+", numCombi="+num_combine); }
		int	I, acopy[] = null, ori[] = null, at;
		for(i=0;i<this.myScratchFilledSize;i++){
			ori = this.scratch[i];
			if( num_combine > 1 ){
				acopy = Arrays.copyOf(ori, this.myNumDims);
			}
			//System.out.println("based on "+Arrays.toString(ori));
			ori[atDim] = toCombine[0];
			//System.out.println("inserting at "+i+" : "+Arrays.toString(ori));
			for(j=1;j<num_combine;j++){
				acopy = Arrays.copyOf(acopy, this.myNumDims);
				acopy[atDim] = toCombine[j];
				at = i+j*this.myScratchFilledSize;
				//System.out.println("i="+i+", j="+j+", => inserting at "+at+" "+Arrays.toString(acopy));
				this.scratch[at] = acopy;
			}
			//System.out.println("so far:\n"+Cartesian.toString_arraylist(this.scratch));
		}
		this.myScratchFilledSize = new_total_num_items;
		// and return the number in the WHOLE cartesian product set SO FAR
		return this.myScratchFilledSize;
	}

	// shitty convenient to print shitty arraylist
	public static String	toString_arraylist(int[][] ar){
		StringBuffer	sb = new StringBuffer();
		int	L = ar.length;
		for(int i=0;i<L;i++){
			sb.append(i+"): "+Arrays.toString(ar[i]));
			sb.append("\n");
		}
		return sb.toString();
	}

	// as 'scratch' array may get large, allow user to explicitly empty the large array
	// and call the GC instead of just waiting or even forgetting the humongous object
	public	void	delete(){
		for(int i=this.myNumDims;i-->0;){ this.scratch[i] = null; }
		this.scratch = null;
		System.gc();
	}
	public	int	num_dimensions(){ return this.myNumDims; }
}
