package sortProject01;

import java.lang.reflect.Array;

public class SortClass<T> {
	
	public enum SortType { MERGE_SORT, QUICK_SORT, BUBBLE_SORT, INSERTION_SORT, NONE };
	private SortType lastSortType;

	public SortType getSortType() {
		return this.lastSortType;
	}
	
	private void setSortType(SortType t) {
		this.lastSortType = t;
	}

	
	private long sortTimeStart,sortTimeEnd;
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
	public double getSortTime() {
		//Returns a decimal value in miliseconds, converted from nanoseconds
		return (sortTimeEnd - sortTimeStart) / 1000000.0;
	}
		
	private T[] sortList;
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
	
	public void swap(int source, int target) {
		T[] data = this.getList();
		T ele = data[target];
		data[target] = data[source];
		data[source] = ele;
		manipulationCounter+=2;
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
		sortTimeStart = System.nanoTime();
		recursiveSortListInsertionA(1,this.getList().length);
		sortTimeEnd = System.nanoTime();
		}
	private void recursiveSortListInsertionA(int l, int r) {
		
			iterationCounter++;	
			T[] buffer = this.getList();
			if(l < r)
			{				
					T temp = buffer[l];			
					int j = l-1;
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
				recursiveSortListInsertionA(l+1,r);
			}			
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
					
					swap(j+1,j);					
					iterationCounter++;	
				}
			}
		}
		sortTimeEnd = System.nanoTime();
	}
	
	@SuppressWarnings("unchecked")
	private T[] merge(T[] input, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        T[] L = (T[]) Array.newInstance(input.getClass().getComponentType(),n1);
        T[] R = (T[]) Array.newInstance(input.getClass().getComponentType(),n2);

        for (int i = 0; i < n1; ++i)
            L[i] = input[left + i];
        	manipulationCounter++;
        	iterationCounter++;
        for (int j = 0; j < n2; ++j)
            R[j] = input[mid + 1 + j];
        	manipulationCounter++;
        	iterationCounter++;
        int i = 0, j = 0;
        
        int k = left;
        while (i < n1 && j < n2) {
            if (comparison(L[i],R[j]) <= 0) {
                input[k] = L[i];
                i++;
            }
            else {
            	input[k] = R[j];
                j++;
            }
            k++;
            manipulationCounter++;
        	iterationCounter++;
        }
        
        while (i < n1) {
        	input[k] = L[i];
            i++;
            k++;
            
            manipulationCounter++;
        	iterationCounter++;
        	if(j < n2) {
        		
        	}
        }

        while (j < n2) {
        	input[k] = R[j];
            j++;
            k++;
            manipulationCounter++;
        	iterationCounter++;
        }
        
		return input;
	}

	
	private void sortListMergeA(int l, int r) {
		T[] input = this.getList();
		if(l < r) {
			int mid = l + (r - l) / 2;
			sortListMergeA(l, mid);
			sortListMergeA(mid+1, r);				
			merge(input, l, mid, r);
		}
	}
	public void sortListMerge() {
		setSortType(SortType.MERGE_SORT);
		sortTimeStart = System.nanoTime();
		sortListMergeA(0,this.getList().length-1);
		sortTimeEnd = System.nanoTime();
		
	}
	
	public void sortListQuick() {
		setSortType(SortType.QUICK_SORT);
		sortTimeStart = System.nanoTime();
		sortListQuickX(0, this.getList().length - 1);
		sortTimeEnd = System.nanoTime();
	}
	public void sortListQuickX(int l, int r) {
		if(l < r)
		{
			int pi = quickSortPartition(l,r);
			sortListQuickX(l,pi-1);
			sortListQuickX(pi+1,r);
			iterationCounter+=2;
		}
	}
	private int quickSortPartition(int l, int r) {
		T[] buffer = this.getList();
		
		//TODO: improve this
		int pp = r;
		T pivot = buffer[pp]; 
		
		int i = l - 1;
		for(int j = l; j <= r - 1; j++) {
			if(comparison(buffer[j],pivot) < 0) {
				i++;
				this.swap(i,j);
			}
			iterationCounter++;
		}
		i++;
		this.swap(i,r);
		return i;
		
	}

}
