package	ahp.org.Cartesians;

import	java.util.Iterator;
import	java.util.Arrays;

public class CartesianProductIteratorCompressed implements Iterator<int[]> {
	private	int	myNumDims;
	private	int	myNumItemsPerDimension[];
	private	String	mySpecs[];
	// each dimension has an array of 'Units'.
	// A unit represents something like 1:3 or 1,2,3,4,5
	// In the future we may support many units as per 1:3, 1,2,3,4 etc.
	// that's why we have an array of units for each dimension
	private	CartesianProductIteratorUnit	myUnits[/*dim*/];

	private	boolean	myHasNext;
	private	boolean	debug = true;

	// how many states we can produce
	private	int mySpan = 0;

	public	CartesianProductIteratorCompressed(
		int	num_items_per_dimension[]
		// Default specs is ALL INCLUDED (i.e. all '*')!!!
	) throws Exception {
		this.myNumDims = num_items_per_dimension.length;
		this.myNumItemsPerDimension = num_items_per_dimension.clone();

		// specs will be created as default i.e. all '*'
		this.mySpecs = new String[this.myNumDims];
		for(int i=this.myNumDims;i-->0;){
			// default spec: from 0 till max items
			this.mySpecs[i] = new String("*");
		}
		this.init();
		this.reset();
	}
	public	CartesianProductIteratorCompressed(
		int	num_items_per_dimension[],
		String[] specs
	) throws Exception {
		this.myNumDims = num_items_per_dimension.length;
		this.myNumItemsPerDimension = num_items_per_dimension.clone();
		this.mySpecs = specs;
		this.init();
		this.reset();
	}
	public	String	lsb_spec(){ return this.mySpecs[this.myNumDims-1]; }
	public	String	msb_spec(){ return this.mySpecs[0]; }
	public	CartesianProductIteratorUnit	lsb(){ return this.myUnits[this.myNumDims-1]; }
	public	CartesianProductIteratorUnit	msb(){ return this.myUnits[0]; }
	public	void	reset(){
		for(int atDim=this.myNumDims;atDim-->0;){
			this.myUnits[atDim].reset();
		}
		this.myHasNext = this.lsb().hasNext();
	}
	private	void	init() throws Exception {
		this.myUnits = SpecsParser.parse_specs_compressed_form(
			this.mySpecs,
			this.myNumItemsPerDimension
		);
		this.mySpan = 1;
		for(CartesianProductIteratorUnit aunit : this.myUnits){
			this.mySpan *= aunit.span();
		}
	}
	public	int	span(){ return this.mySpan; }
	public	boolean	hasNext(){ return this.myHasNext; }
	public	int[]	current(){
		int ret[] = new int[this.myNumDims];
		for(int atDim=this.myNumDims;atDim-->0;){
			ret[atDim] = this.myUnits[atDim].current();
		}
		return ret;
	}		
	public	void	current(int ret[]){
		for(int atDim=this.myNumDims;atDim-->0;){
			ret[atDim] = this.myUnits[atDim].current();
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
		// start moving from the LSB
		// if there is need (i.e. it reaches the end)
		// it will also call to move for LSB+1 etc.
		this.move_counter_forward(this.myNumDims-1);
		this.myHasNext = this.msb().hasNext();
	}
	private	void	move_counter_forward(int atDim){
		CartesianProductIteratorUnit	aunit = this.myUnits[atDim];

		aunit.next();
		
		if( aunit.hasNext() == false ){
			// if it is the last counter (MSB), don't reset, don't advance
			// hasNext of this class depends on MSB's hasnext.
			if( atDim > 0 ){
				// we have reached the end of this counter, so we do:
				// 1) reset us:
				aunit.reset();

				// 2) we do have a counter on the left
				//    move-forward counter on the left:
				move_counter_forward(atDim-1);
			}				
		}
	}
	public	int	num_dimensions(){ return this.myNumDims; }
	public	String	toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("CartesianProductIteratorCompressed : spec: [");
		for(int i=0;i<this.myNumDims;i++){
			sb.append("\"");
			sb.append(this.mySpecs[i]);
			sb.append("\",");
		}
		sb.setCharAt(sb.length()-1, ']');
		sb.append(", units: "+Arrays.toString(this.myUnits));
		sb.append(", span: "+this.span()+" states");
		sb.append(", at the end? ");
		sb.append(this.hasNext() ? "No." : "Yes.");
		return sb.toString();
	}
}
