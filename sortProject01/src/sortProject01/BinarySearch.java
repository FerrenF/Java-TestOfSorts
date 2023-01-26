package sortProject01;

public class BinarySearch<T>{
	private ComparableMethod<T> compareFunction = null;
	private int search(T[] input, T test, int lo, int hi) {
		while(lo <= hi) {
			int mid = (lo + hi) / 2;
			int comparison = (compareFunction == null) 
					? ((Comparable<T>)input[mid]).compareTo(test)
					:  compareFunction.compareTo(input[mid],test);
			if(comparison == 0) {
				return mid;
			}
			else if(comparison < 0) {
				lo = mid + 1;
			} else {
				hi = mid - 1;
			}
		}	
		return lo;
	}
	public int search(T[] input, T test, ComparableMethod<T> comparator) {
		compareFunction = comparator;
		return search(input, test, 0, input.length-1);
	}
	public int search(Comparable<T>[] input, Comparable<T> test) {
		return search((T[])input, (T)test, 0, input.length-1);
	}
	
}