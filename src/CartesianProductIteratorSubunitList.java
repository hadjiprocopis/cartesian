package	ahp.org.Cartesians;

import	java.util.Arrays;

public class CartesianProductIteratorSubunitList extends CartesianProductIteratorSubunit {
	private	int	myList[],
			myListLength;

	public	CartesianProductIteratorSubunitList(int list[]){
		this.myList = list;
		this.myListLength = list.length;
		this.reset();
		//System.out.println("CartesianProductIteratorSubunitList() : constructor called with list: "+Arrays.toString(list));
	}
	// this is the total number of states we will produce:
	public	int	span(){ return this.myList.length; }

	public	int[]	list(){ return this.myList; }
	public	void	reset(){
		this.myCurrentIndex = 0;
		this.myNeedsRecalculateHasNext = true;
	}
	// SPEED: pass-by-value integer
	public	Integer	next(){
		// current index points at the 'next'. we return that and then we increment it
		if( this.hasNext() == true ){
			// this returns this.myList[this.myCurrentIndex]
			// and then increments the index
			this.myNeedsRecalculateHasNext = true;
			return new Integer(this.myList[this.myCurrentIndex++]);
		} else { return null; }
	}
	public	Integer	current(){
		return new Integer(this.myList[this.myCurrentIndex]);
	}
	public	boolean	hasNext(){
		if( this.myNeedsRecalculateHasNext ){
			this.myHasNext = (this.myCurrentIndex < this.myListLength)
			&& (this.myCurrentIndex >= 0);
			this.myNeedsRecalculateHasNext = false;
		}
		return this.myHasNext;
	}
	public	String	toString(){
		StringBuilder sb = new StringBuilder("CartesianProductIteratorSubunitList : (");
		for(int i=0;i<this.myListLength;i++){
			sb.append(this.myList[i]);
			sb.append(',');
		}
		sb.setCharAt(sb.length()-1, ')');
		sb.append(", current index: ");
		sb.append(this.myCurrentIndex);
		sb.append(", span: ");
		sb.append(this.span());
		sb.append(" states");
		sb.append(", has next:");
		sb.append(this.hasNext()?"Yes.":"No.");
		return sb.toString();
	}
}
