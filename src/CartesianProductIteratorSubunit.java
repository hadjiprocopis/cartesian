package	ahp.org.Cartesians;

import	java.util.Iterator;

public abstract class CartesianProductIteratorSubunit implements Iterator<Integer> {
	// current index always points to the item to
	// be given via a next() call.
	protected	int	myCurrentIndex;

	protected	boolean	myHasNext = false;
	protected	boolean	myNeedsRecalculateHasNext = true;

	public	void	reset(){
		System.err.println("CartesianProductIteratorSubunit.java : reset() is abstract!");
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()){
			System.err.println(ste);
		}
		System.exit(1);
	}
	public	Integer	next(){
		System.err.println("CartesianProductIteratorSubunit.java : next() is abstract!");
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()){
			System.err.println(ste);
		}
		System.exit(1);
		return null;
	}
	// These methods need not be implemented:
	public	int	current_index(){ return this.myCurrentIndex; }
	public	Integer	current(){
		System.err.println("CartesianProductIteratorSubunit.java : current() is abstract!");
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()){
			System.err.println(ste);
		}
		System.exit(1);
		return null;
	}
	public	boolean	hasNext(){
		System.err.println("CartesianProductIteratorSubunit.java : hasNext() is abstract!");
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()){
			System.err.println(ste);
		}
		System.exit(1);
		return false;
	}
	// this returns the number of states this subunit yields...
	public	int	span(){
		System.err.println("CartesianProductIteratorSubunit.java : next() is abstract!");
		for(StackTraceElement ste : Thread.currentThread().getStackTrace()){
			System.err.println(ste);
		}
		System.exit(1);
		return -1;
	}
	public	String	toString(){ return new String("CartesianProductIteratorSubunit : abstract"); }
}
