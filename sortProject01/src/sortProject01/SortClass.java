package sortProject01;

public class SortClass<T> {
	private int operationCounter = 0;
	private T[] sortList;
	private boolean reverse_order = false;
	private boolean is_comparable = false;
	private ComparableMethod<T> compareFunction = null;
	public T[] getList() {		
		return (T[])sortList;
	}
	
	// Our sort class can take an object that implements the Comparable interface and work with it right away.
	public SortClass(Comparable[] input) {
		this.sortList = (T[])input;
		is_comparable = true;
	}	
	
	// It can also work with other types, but you must specify a comparison method for something it can't infer

	public SortClass(T[] input, ComparableMethod<T> comparator) {
		this.compareFunction = comparator;
	}
	
	public int getLastOperationCounter() {
		return this.operationCounter;
	}
	
	
	//This just cleans up our logic for running comparator functions to determine whether or not to swap two particular items.
	@SuppressWarnings("unchecked")
	private boolean swap(int j, T[] buffer, T test) {
		int comparison = is_comparable
				? ( ((Comparable<T>)test).compareTo((T)buffer[j]) )
				: ( compareFunction.compareTo(test, (T)buffer[j]));
		return reverse_order ? ( comparison > 0) 
				: (comparison  < 0);
	}
	//Insertion sort method 1 (Iterative). 
	//Two nested loops means a worst case scenario of n * n iterations through these loops or big O(n^2)
	
	public T[] sortListInsertion() {
		operationCounter=0;
		T[] buffer = this.getList();
		int j = 0;
		for(int i = 1; i < buffer.length; i++) {
			T temp = buffer[i];			
			j = i-1;
			// the first logical check will ensure the second does not run into a buffer overflow on the first element.
			while((j >= 0) && swap(j,buffer, temp)) {
				// sorted elements <- unsorted elements 
				// we are moving backwards into an already sorted list when we find our insertion point for the held element
				buffer[j+1] = buffer[j];
				j--;
				operationCounter++;				
			}
			buffer[j+1]=temp;
		}
		return (T[])buffer;
	}
	
	//Insertion sort method 2 (Recursive). 
	//Two nested loops means a worst case scenario of n * n iterations through these loops or big O(n^2)
	public T[] recursiveSortListInsertion() {
			operationCounter=0;
			T[] buffer = sortList.clone();
			int j = 0;
			for(int i = 1; i < this.getList().length; i++) {
				T temp = buffer[i];			
				j = i-1;	
				// the first logical check will ensure the second does not run into a buffer overflow on the first element.
				while((j >= 0) && swap(j,buffer,temp)) {
					// sorted elements <- unsorted elements 
					// we are moving backwards into an already sorted list when we find our insertion point for the held element
					buffer[j+1] = buffer[j];			
					j--;
					operationCounter++;
				}
				buffer[j+1]=temp;
			}
			return (T[])buffer;
		}
	
	
	
	
	// Very similar in approach to insertion sort, we have bubble sort.

	public T[] sortListBubble() {
		operationCounter=0;
		T[] buffer = this.getList();
		
		for(int i = 0; i < this.getList().length - 1; i++) {	
			for(int j=0; j < this.getList().length - i  - 1;j++) {
			T temp = buffer[j];		
			
			//use our swap function to clean up the logic determining whether we swap or not. Note that in this one I did not pass the currently held item as the test
				if (swap(j,buffer,buffer[j+1])) {
					// unsorted elements -> sorted elements 
					// we are moving forwards into an already sorted list when we find our insertion point for the held element
					buffer[j] = buffer[j+1];	
					buffer[j+1] = temp;
					operationCounter++;
				}
			}
		}
		return (T[])buffer;
	}
	
	//Binary insertion sort method 1.
	//Caveats include problems with duplicate IDs in an array. But for arrays without duplicate ID's, it is very nice ok great thanks
	public T[] sortListBinaryInsertion() {
		operationCounter=0;
		T[] buffer = this.getList()`;
		int j;
		int ln = buffer.length;
		for(int i = 1; i < ln; ++i) {	
			j = i - 1;
			T temp = buffer[i];				
			int loc = is_comparable 
					? (new BinarySearch<T>()).search((Comparable<T>[])buffer, (Comparable<T>)temp)
				: new BinarySearch<T>().search(buffer,temp, this.compareFunction);
				
			while(j >= loc) {
				
				buffer[j+1] = buffer[j];
				operationCounter++;
				j--;
			}
			buffer[j+1]=temp;
		}
		return (T[])buffer;
	}

}
