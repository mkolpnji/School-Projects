package heaps;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An unbounded min-heap. A MinHeap orders elements according to their natural order.
 * 
 * Null elements are not permitted. All elements are required to be of the same type.
 * 
 * A MinHeap is unbounded, but has an internal capacity governing the size of an array used to store
 * the elements. It is always at least as large as the heap size. As elements are added, the
 * capacity grows automatically. The details of the growth policy are not specified.
 * 
 * Guarantees O(log n) run time for add() and remove(). Guarantees O(1) running time for peek().
 */
public class MinHeap<E extends Comparable<E>>
{
	private ArrayList<E> x;

	/**
	 * Creates a MinHeap with the default initial capacity (11).
	 */
	public MinHeap()
	{
		x = new ArrayList<E>(11);
	}

	/**
	 * Creates a MinHeap with the specified initial capacity.
	 * 
	 * @parameter initialCapacity the initial capacity for this min-heap.
	 * @throws IllegalArgumentException
	 *             if initialCapacity is negative.
	 */
	public MinHeap(int initialCapacity)
	{
		if (initialCapacity < 0)
			throw new IllegalArgumentException();
		x = new ArrayList<E>(initialCapacity);
	}

	/**
	 * Adds the specified element to this min-heap.
	 * 
	 * @parameter obj the element.
	 * @throws NullPointerException
	 *             if the specified element is null.
	 */
	public void add(E obj)
	{
		if (obj == null)
			throw new NullPointerException();
		
		x.add(obj);
		
		int currIndex = x.size() - 1;
		while (getParentIndex(currIndex) >= 0 && x.get(getParentIndex(currIndex)).compareTo(obj) >= 0){
			Collections.swap(x, getParentIndex(currIndex), currIndex);
			currIndex = getParentIndex(currIndex);
		}
	}

	/**
	 * Retrieves and removes the minimum element from this min-heap, or null if this heap is empty.
	 */
	public E remove()
	{
		if (x.isEmpty())
			return null;
		else if (x.size() == 1)
			return x.remove(0);
		
		Collections.swap(x, 0, x.size() - 1);
		E removed = x.remove(x.size() - 1);
		fixHeap(0);
		
		return removed;
	}

	/**
	 * Retrieves, but does not remove, the minimum element from this min-heap, or null if this heap
	 * is empty.
	 */
	public E peek()
	{
		return x.get(0);
	}

	/**
	 * Fixes a min-heap that is violated only by its root.
	 * 
	 * Preconditions: The (sub)heap rooted at rootIndex adheres to the min-heap property except for the
	 * element at rootIndex itself.
	 * 
	 * Postcondition: The (sub)heap rooted at rootIndex adheres to the min-heap property.
	 */
	private void fixHeap(int rootIndex)
	{
		/*int leftCompare = -1;
		int rightCompare = -1;
		
		if (getChildIndex(rootIndex, true) < x.size())
			leftCompare = x.get(rootIndex).compareTo(x.get(getChildIndex(rootIndex, true)));
		if (getChildIndex(rootIndex, false) < x.size())
			rightCompare = x.get(rootIndex).compareTo(x.get(getChildIndex(rootIndex, false)));
		
		
		if (leftCompare < 0 && rightCompare < 0)
			return;
		else if (leftCompare >= rightCompare){
			Collections.swap(x, getChildIndex(rootIndex, true), rootIndex);
			fixHeap(getChildIndex(rootIndex, true));
		}
		else {
			Collections.swap(x, getChildIndex(rootIndex, false), rootIndex);
			fixHeap(getChildIndex(rootIndex, false));
		}*/
		
		int newIndex = rootIndex;
		
		if (getChildIndex(rootIndex, true) < x.size() && x.get(newIndex).compareTo(x.get(getChildIndex(rootIndex, true))) >= 0)
			newIndex = getChildIndex(rootIndex, true);
		
		if (getChildIndex(rootIndex, false) < x.size() && x.get(newIndex).compareTo(x.get(getChildIndex(rootIndex, false))) >= 0)
			newIndex = getChildIndex(rootIndex,false);
			
		if (newIndex != rootIndex){
			Collections.swap(x, rootIndex, newIndex);
			fixHeap(newIndex);
		}
	}
	
	private int getParentIndex(int childIndex)
	{
		return (childIndex + 1)/2 - 1;
	}
	
	private int getChildIndex(int parentIndex, boolean left)
	{
		if (left)
			return (parentIndex * 2) + 1;
		else
			return parentIndex * 2 + 2;
		
	}
}