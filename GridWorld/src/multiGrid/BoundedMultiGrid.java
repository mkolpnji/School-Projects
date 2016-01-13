package multiGrid;

import java.util.ArrayList;

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

public class BoundedMultiGrid<E> extends AbstractGrid<E>{
	
	private Object[][][] occupantArray;
	private int [][] maxValue;
	
	public BoundedMultiGrid(int rows, int cols){
		if (rows <= 0)
			throw new IllegalArgumentException("rows <= 0");
		if (cols <= 0)
			throw new IllegalArgumentException("cols <= 0");
		occupantArray = new Object[rows][cols][999];
		maxValue = new int[rows][cols];
	}
	
	public E get(Location loc) {
		if (!isValid(loc))
			throw new IllegalArgumentException("Location " + loc + " is not valid");
		Object lastObject = occupantArray[loc.getRow()][loc.getCol()][maxValue[loc.getRow()][loc.getCol()]];
		return (E) lastObject;
	}

	/*@Override
	public ArrayList<E> getNeighbors(Location arg0) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public int getNumCols() {
		return occupantArray[0].length;
	}

	public int getNumRows() {
		return occupantArray.length;
	}

	public ArrayList<Location> getOccupiedLocations() {
		ArrayList<Location> theLocations = new ArrayList<Location>();
		
		for (int r = 0; r < getNumRows(); r++)
		{
			for (int c = 0; c < getNumCols(); c++){
				Location loc = new Location(r, c);
				if (get(loc) != null)
					theLocations.add(loc);
			}
		}
		
		return theLocations;
	}


	public boolean isValid(Location loc) {
		return 0 <= loc.getRow() && loc.getRow() < getNumRows() 
				&& 0 <= loc.getCol() && loc.getCol() < getNumCols();
	}

	public E put(Location loc, E obj) {
		if (!isValid(loc))
			throw new IllegalArgumentException("Location " + loc + " is not valid");
		if (obj == null)
			throw new NullPointerException("obj == null");
		
		E oldOccupant = get(loc);
		maxValue[loc.getRow()][loc.getCol()]++;
		occupantArray[loc.getRow()][loc.getCol()][maxValue[loc.getRow()][loc.getCol()]] = obj;
		return oldOccupant;
	}

	
	public E remove(Location loc) {
		if (!isValid(loc))
			throw new IllegalArgumentException("Location " + loc + " is not valid");
		E lastObject = get(loc);
		maxValue[loc.getRow()][loc.getCol()]--;
		return lastObject;
	}
	
}
