package sortProject01;

import java.lang.reflect.Array;

public class SortClass<T> {


	private int iterationCounter = 0;
	private int comparisonCounter = 0;
	private int manipulationCounter = 0;
	
	public int getIterationCounter() {
		return iterationCounter;
	}
	public int getComparisonCounter() {
		return comparisonCounter;
	}
	public int getManipulationCounter() {
		return manipulationCounter;
	}
	
	private long sortTimeStart,sortTimeEnd;
	public long getSortTime() {
		return sortTimeEnd - sortTimeStart;
	}
		
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
	

	public void resetStats() {
		iterationCounter = 0;
	    comparisonCounter = 0;
		manipulationCounter = 0;
	}
	//This just cleans up our logic for running comparator functions to determine whether or not to swap two particular items.
	//It also centralizes the location of comparison tracking.
		@SuppressWarnings("unchecked")
	private int comparison(T testA, T testB) {
			comparisonCounter++;
		return is_comparable
				? ( ((Comparable<T>)testA).compareTo(testB) )
				: ( compareFunction.compareTo(testA, testB));
	}
	

	//Insertion sort method 1 (Iterative). 
	//Two nested loops means a worst case scenario of n * n iterations through these loops or big O(n^2)
	
	public void sortListInsertion() {
		resetStats();
		sortTimeStart = System.nanoTime();
		T[] buffer = this.getList();
		int j = 0;
		for(int i = 1; i < buffer.length; i++) {
			T temp = buffer[i];			
			j = i-1;
			// the first logical check will ensure the second does not run into a buffer overflow on the first element.
			while((j >= 0) && (comparison(temp,buffer[j])<0)) {
				// sorted elements <- unsorted elements 
				// we are moving backwards into an already sorted list when we find our insertion point for the held element
				buffer[j+1] = buffer[j];
				j--;
				iterationCounter++;	
				manipulationCounter++;
			}
			buffer[j+1]=temp;
			manipulationCounter++;
		}
		sortTimeEnd = System.nanoTime();
	}
	
	//Insertion sort method 2 (Recursive). 
	//Two nested loops means a worst case scenario of n * n iterations through these loops or big O(n^2)
	public void recursiveSortListInsertion() {
		resetStats();
			T[] buffer = this.getList();
			
			
		}
	
	
	
	
	// Very similar in approach to insertion sort, we have bubble sort.
	public void sortListBubble() {
		resetStats();
		sortTimeStart = System.nanoTime();
		T[] buffer = this.getList();
		int l = this.getList().length;
		for(int i = 0; i < l - 1; i++) {	
			for(int j=0; j < l - i  - 1;j++) {
			T temp = buffer[j];		
			//use our swap function to clean up the logic determining whether we swap or not. Note that in this one I did not pass the currently held item as the test
				if((comparison(buffer[j+1],temp)<0)) {
					// unsorted elements -> sorted elements 
					// we are moving forwards into an already sorted list when we find our insertion point for the held element
					buffer[j] = buffer[j+1];	
					buffer[j+1] = temp;
					iterationCounter++;	
					manipulationCounter+=2;
				}
			}
		}
		sortTimeEnd = System.nanoTime();
	}
	
	private <T> T[] concatenate(T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	@SuppressWarnings("unchecked")
	private T[] merge(T[] buffer, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        T[] L = (T[]) Array.newInstance(buffer.getClass().getComponentType(),n1);
        T[] R = (T[]) Array.newInstance(buffer.getClass().getComponentType(),n2);

        for (int i = 0; i < n1; ++i)
            L[i] = buffer[l + i];
        	manipulationCounter++;
        	iterationCounter++;
        for (int j = 0; j < n2; ++j)
            R[j] = buffer[m + 1 + j];
        	manipulationCounter++;
        	iterationCounter++;
        int i = 0, j = 0;
        
        int k = l;
        while (i < n1 && j < n2) {
            if (comparison(L[i],R[j]) <= 0) {
                buffer[k] = L[i];
                i++;
            }
            else {
            	buffer[k] = R[j];
                j++;
            }
            k++;
            manipulationCounter++;
        	iterationCounter++;
        }
        
        while (i < n1) {
        	buffer[k] = L[i];
            i++;
            k++;
            manipulationCounter++;
        	iterationCounter++;
        }

        while (j < n2) {
        	buffer[k] = R[j];
            j++;
            k++;
            manipulationCounter++;
        	iterationCounter++;
        }
        
		return buffer;
	}

	
	private void sortListMergeA(T[] input, int l, int r) {
		if(l < r) {
			int mid = l + (r - l) / 2;
			sortListMergeA(input, l, mid);
			sortListMergeA(input, mid+1, r);				
			merge(input, l, mid, r);
		}
	}
	public void sortListMerge() {
		sortTimeStart = System.nanoTime();
		sortListMergeA(this.getList(),0,this.getList().length-1);
		sortTimeEnd = System.nanoTime();
		
	}

}
