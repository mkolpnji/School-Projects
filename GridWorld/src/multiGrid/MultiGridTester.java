package multiGrid;

import java.awt.Color;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;


public class MultiGridTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BomberWorld world = new BomberWorld(new BoundedMultiGrid<Tile>(9, 9));
		world.show();
	}
	
	

}
