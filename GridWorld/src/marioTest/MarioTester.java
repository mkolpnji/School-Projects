package marioTest;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

public class MarioTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MarioWorld world = new MarioWorld(new BoundedGrid<Tile>(6, 10));
		world.show();
	}

}
