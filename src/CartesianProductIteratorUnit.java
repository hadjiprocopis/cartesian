package	ahp.org.Cartesians;

import	java.util.ArrayList;
import	java.util.Iterator;

/*
a unit consists of 1 or more sub-units.
A sub unit corresponds to the selection of a spec 1:3 or 1,2,3,4,5
In future a spec can consist of <1:3><1,2,3,4,5><14:15>
that's 3 subunits. And so these 3 subunits make one unit (for the specified dimension)
and there will be a single 'next()' moving within the subunit and then going to the next subunit etc.
*/
public class CartesianProductIteratorUnit implements Iterator<Integer> {
	private	ArrayList<CartesianProductIteratorSubunit>	mySubunits;
	private	int myNumSubunits;
	private	CartesianProductIteratorSubunit myCurrentSubunit;
	private	int	myCurrentSubunitIndex;
	private	int	mySpan = 0; // total number of states we can produce
	private	boolean	hasNext;

	public	CartesianProductIteratorUnit(CartesianProductIteratorSubunit asubunit){
		this.init();
		this.add_a_unit(asubunit);

		this.reset();
	}
	private	void	init(){
		this.mySubunits = new ArrayList<CartesianProductIteratorSubunit>(1);
	}
	public	void	add_a_unit(CartesianProductIteratorSubunit asubunit){
		this.mySubunits.add(asubunit);
		this.recalculate();
	}
	private	void	recalculate(){
		// now the subunits are enumerated one after the other
		// so the total span is the sum of the subunits spans
		// (not the product as is in when we put many units in parallel)
		this.mySpan = 0;
		for(CartesianProductIteratorSubunit asub : this.mySubunits){
			this.mySpan += asub.span();
		}
	}
	// return the total number of states we produce
	public	int	span(){ return this.mySpan; }
	public	void	reset(){
		for(int i=this.mySubunits.size();i-->0;){
			this.mySubunits.get(i).reset();
		}
		this.myCurrentSubunitIndex = 0;
		this.myCurrentSubunit = mySubunits.get(this.myCurrentSubunitIndex);
	}
	public	boolean	hasNext(){
		return (this.myCurrentSubunit != null)
			&& this.myCurrentSubunit.hasNext()
		;
	}

	// returns NULL if has no next state otherwise an integer from 0 up
	public	Integer	next(){
		// we have reached the end:
		if( this.hasNext() == false ){ return null; }

		// we have a next, question is if we have to change subunits?

		// we have a next in the current subunit and we return it, no need to change subunits
		if( this.myCurrentSubunit.hasNext() ){
			return this.myCurrentSubunit.next();
		}

		// we need to move to the next subunit if any:
		if( (this.myCurrentSubunitIndex+1) < this.myNumSubunits ){
			this.myCurrentSubunitIndex += 1;
			this.myCurrentSubunit = this.mySubunits.get(this.myCurrentSubunitIndex);
			return this.myCurrentSubunit.next();
		} else {
			// no more subunits
			this.myCurrentSubunit = null;
		}
		return null;
	}
	public	Integer	current(){
		if( this.myCurrentSubunit == null ){ return null; }
		return this.myCurrentSubunit.current();
	}
	public	String	toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("CartesianProductIteratorUnit: span: ");
		sb.append(this.span());
		sb.append(" states\nthe "+this.mySubunits.size()+" subunits:\n");
		int index = 1;
		for(CartesianProductIteratorSubunit shit : this.mySubunits){
			sb.append('\t');
			sb.append(index);
			sb.append(") : ");
			sb.append(shit.toString());
			sb.append('\n');
			index++;
		}
		sb.append("CartesianProductIteratorUnit: end.");
		return sb.toString();
	}
}
