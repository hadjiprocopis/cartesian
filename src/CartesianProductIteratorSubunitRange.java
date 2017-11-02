package	ahp.org.Cartesians;

public class CartesianProductIteratorSubunitRange extends CartesianProductIteratorSubunit {
	private	int	myFrom,
			myTo;
	public	CartesianProductIteratorSubunitRange(int from, int to){
		this.myFrom = from;
		this.myTo = to;
		this.reset();
	}
	public	void	reset(){
		this.myCurrentIndex = this.myFrom;
		this.myNeedsRecalculateHasNext = true;
	}
	public	Integer	current(){
		return new Integer(this.myCurrentIndex);
	}
	public	Integer	next(){
		// current index points at the 'next'. we return that and then we increment it
		if( this.hasNext() == true ){
			this.myNeedsRecalculateHasNext = true;
			return new Integer(this.myCurrentIndex++);
		} else { return null; }
	}
	public boolean	hasNext(){
		if( this.myNeedsRecalculateHasNext ){
			this.myHasNext = (this.myCurrentIndex <= this.myTo)
				&& (this.myCurrentIndex >= this.myFrom);
			this.myNeedsRecalculateHasNext = false;
		}
		return this.myHasNext;
	}
	// this is the total number of states we will produce:
	public	int	span(){ return this.myTo - this.myFrom + 1; }
	public	int	from(){ return this.myFrom; }
	public	int	to(){ return this.myTo; }

	public	String	toString(){
		StringBuilder sb = new StringBuilder("CartesianProductIteratorSubunitRange : ");
		sb.append("(");
		sb.append(this.from());
		sb.append(':');
		sb.append(this.to());
		sb.append("), current index: ");
		sb.append(this.myCurrentIndex);
		sb.append(", span: ");
		sb.append(this.span());
		sb.append(" states");
		sb.append(", has next:");
		sb.append(this.hasNext()?"Yes.":"No.");
		return sb.toString();
	}
}
