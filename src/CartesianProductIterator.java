package	ahp.org.Cartesians;

import	java.util.Iterator;
import	java.util.Arrays;

public class CartesianProductIterator implements Iterator<int[]> {
	private	int	myNumDims;
	private	int	myNumItemsPerDimension[];
	private	String	mySpecs[];
	private	int	myEnumeratedBasis[][];
	private	int	myCounters[];
	private int	myNumEnumeratedBasisPerDimension[];
	private	int	mySpan = 0;

	private	boolean	myHasNext;
	private	boolean	debug = true;

	public	CartesianProductIterator(
		int	num_items_per_dimension[]
		// Default spec is ALL INCLUSIVE (i.e. all '*')
	) throws Exception {
		this.myNumDims = num_items_per_dimension.length;
		this.myNumItemsPerDimension = num_items_per_dimension.clone();

		// specs will be created as default i.e. all '*'
		this.mySpecs = new String[this.myNumDims];
		for(int i=this.myNumDims;i-->0;){ this.mySpecs[i] = new String("*"); }
		this.myNumEnumeratedBasisPerDimension = new int[this.myNumDims];
		this.init();
		this.reset();
	}
	public	CartesianProductIterator(
		int	num_items_per_dimension[],
		String[] specs
	) throws Exception {
		this.myNumDims = num_items_per_dimension.length;
		this.myNumItemsPerDimension = num_items_per_dimension.clone();
		this.mySpecs = specs;
		this.myNumEnumeratedBasisPerDimension = new int[this.myNumDims];
		this.init();
		this.reset();
	}
	public	void	reset(){
		this.myHasNext = true;
		// this.myCounters array must be initialised to zero:
		// in java all int arrays are initialised to zero
		this.myCounters = new int[this.myNumDims];
	}
	private	void	init() throws Exception {
		this.myEnumeratedBasis = SpecsParser.parse_specs(
			this.mySpecs,
			this.myNumItemsPerDimension
		);
		this.mySpan = 1;
		for(int i=this.myNumDims;i-->0;){
			this.myNumEnumeratedBasisPerDimension[i] = this.myEnumeratedBasis[i].length;
			this.mySpan *= this.myNumEnumeratedBasisPerDimension[i];
		}
	}
	// return number of states we can produce
	public int	span(){ return this.mySpan; }
	public String	lsb_spec(){ return this.mySpecs[this.myNumDims-1]; }
	public String	msb_spec(){ return this.mySpecs[0]; }
	public	boolean	hasNext(){ return this.myHasNext; }
	public	int[]	current(){
		int ret[] = new int[this.myNumDims];
		for(int atDim=this.myNumDims;atDim-->0;){
			ret[atDim] = this.myEnumeratedBasis[atDim][this.myCounters[atDim]];
		}
		return ret;
	}
	// fastest if you are doing it in a loop, it uses a user-pre-allocated array to pass results
	public	void	current(int ret[]){
		for(int atDim=this.myNumDims;atDim-->0;){
			ret[atDim] = this.myEnumeratedBasis[atDim][this.myCounters[atDim]];
		}
	}
	public	int[]	next(){
		// myCounter indices point to the current position which is quaranteed to be
		// valid (i.e. within the span of this Cartesian Product)
		// UNLESS hasNext() returns false
		if( this.hasNext() == false ){ return null; }
		// so now counters point to a valid point in the our cartesian space
		int ret[] = this.current();

		// this sets this.myHasNext to FALSE if we are at the end (after this move)
		this.move_counters_forward();
		return ret;
	}
	// fastest if you are doing it in a loop, it uses a user-pre-allocated array to pass results
	public	boolean	next(int ret[]){
		// myCounter indices point to the current position which is quaranteed to be
		// valid (i.e. within the span of this Cartesian Product)
		// UNLESS hasNext() returns false
		if( this.hasNext() == false ){ return false; }

		// so now counters point to a valid point in the our cartesian space
		this.current(ret);

		// this sets this.myHasNext to FALSE if we are at the end (after this move)
		this.move_counters_forward();
		return true;
	}
	// MSB is leftmost and therefore counters change more on the right (LSB)
	// sets this.myHasNext to FALSE if we can not move to a next step
	// because we are already pointing to the last step
	private	void	move_counters_forward(){
		this.move_counter_forward(this.myNumDims-1);
	}
	private	void	move_counter_forward(int atDim){
		if( ++this.myCounters[atDim] == this.myNumEnumeratedBasisPerDimension[atDim] ){
			this.myCounters[atDim] = 0;
			if( atDim > 0 ){
				move_counter_forward(atDim-1);
			} else {
				// we have reached the end!
				this.myHasNext = false;
			}
		}
	}
	public	int	num_dimensions(){ return this.myNumDims; }
	public	String	toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("CartesianProductIterator : spec: [");
		for(int i=0;i<this.myNumDims;i++){
			sb.append("\"");
			sb.append(this.mySpecs[i]);
			sb.append("\",");
		}
		sb.setCharAt(sb.length()-1, ']');
		sb.append(", counters: "+Arrays.toString(this.myCounters));
		sb.append(", span: "+this.span()+" states");
		sb.append(", at the end? ");
		sb.append(this.hasNext() ? "No." : "Yes.");
		return sb.toString();
	}
}
