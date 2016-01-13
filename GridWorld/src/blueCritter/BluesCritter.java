package blueCritter;

import java.awt.Color;
import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class BluesCritter extends Critter {
	
	private Location rockLocation = null;
	
	public ArrayList<Actor> getActors(){
		ArrayList<Actor> actors = new ArrayList<Actor>();
		Grid<Actor> grid = getGrid();
		
		for (int x = getLocation().getRow() - 10; x < getLocation().getRow() + 10; x++)
			for (int y = getLocation().getCol() - 10; y < getLocation().getCol() + 10; y++)
				if(grid.isValid(new Location(x, y)) && grid.get(new Location(x, y)) instanceof Rock)
				{
					Rock rock = (Rock) grid.get(new Location (x,y));
					if (rock.getColor() == Color.BLUE)
					{
						actors.add(rock);
						actors.addAll(grid.getNeighbors(new Location (x, y)));
					}
				}
		return actors;
	}
	
	public void processActors(ArrayList<Actor> actors) {
		ArrayList<Actor> blueRocks = new ArrayList<Actor>();
		for (int i = 0; i < actors.size(); i++)
			if (actors.get(i) instanceof Rock && !blueRocks.contains(actors.get(i)))
				blueRocks.add(actors.get(i));
		
		if (blueRocks.size() != 0) {
			int r = (int)(Math.random() * blueRocks.size());
			rockLocation = blueRocks.get(r).getLocation();
			ArrayList<Actor> neighbor = getGrid().getNeighbors(rockLocation);
			for (int i = 0; i < neighbor.size(); i++)
				neighbor.get(i).removeSelfFromGrid();
		}
	}
	
	public ArrayList<Location> getMoveLocations() {
		ArrayList<Location> moveLoc = new ArrayList<Location>();
		if (rockLocation != null)
			moveLoc.add(rockLocation);
		return moveLoc;
	}
	
	

}
