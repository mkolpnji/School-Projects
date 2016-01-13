package carnivorebug;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Flower;

import java.awt.Color;


public class DeadBug extends Bug{

	private int decay;
	private static final double DARKENING_FACTOR = 0.05;
	
	public DeadBug(int direction, Color bugColor)
	{
		setDirection(direction);
		setColor(null);
		decay = 15;
	}
	
	public void act()
	{
		if (decay > 0)
		{
			Color c = getColor();
		    int red = (int) (c.getRed() * (1 - DARKENING_FACTOR));
		    int green = (int) (c.getGreen() * (1 - DARKENING_FACTOR));
		    int blue = (int) (c.getBlue() * (1 - DARKENING_FACTOR));

		    setColor(new Color(red, green, blue));
			decay--;
		}
		else
		{
			Grid<Actor> gr = getGrid();
            if (gr == null)
                return;
            Location loc = getLocation();
			removeSelfFromGrid();
			Flower flower = new Flower(getColor());
		    flower.putSelfInGrid(gr, loc);
		}
	}
}
