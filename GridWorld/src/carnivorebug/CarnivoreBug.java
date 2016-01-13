package carnivorebug;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Bug;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;


public class CarnivoreBug extends Bug
{
	private int hunger;
	public CarnivoreBug()
	{
		setColor(null);
		hunger = 30;
	}
	
    public CarnivoreBug(int initHunger)
    {
    	hunger = initHunger;
    }
    
    public void move()
    {
    	if (adjacentBugCheck())
			hunger += 10;
    	
    	 Grid<Actor> gr = getGrid();
         if (gr == null)
             return;
         Location loc = getLocation();
         Location next = loc.getAdjacentLocation(getDirection());
         if (gr.isValid(next))
             moveTo(next);
         else
             removeSelfFromGrid();
         
         hunger--;
         
         if (hunger < 0)
         {
        	 removeSelfFromGrid();
        	 DeadBug deadbug = new DeadBug(getDirection(), getColor());
             deadbug.putSelfInGrid(gr, loc);
         }
    }
    
    
    public boolean canMove()
    {
    	Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower) || (neighbor instanceof Bug);
    }
    
    
    private boolean adjacentBugCheck()
    {
    	Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor instanceof Bug);
    }

}
