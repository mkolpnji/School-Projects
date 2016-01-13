package multiGrid;

import info.gridworld.grid.Location;

public class Bomb extends Tile{
	
	private int tick;
	private Location loc;
	public Bomb(int r, int c, int type){
		super(type);
		tick = 0;
		loc = new Location(r, c);
	}
	
	public Bomb(int type) {
		super(type);
		tick = 0;
	}
	
	public String getImageSuffix()
	{
		return "_" + type;
	}
	
	public void setType(int newType)
	{
		type = newType;
	}
	
	public void addTick()
	{
		tick++;
	}
	
	public int getTick()
	{
		return tick;
	}
	
	public Location getLoc()
	{
		return loc;
	}
}
