package marioTest;

public class Tile {

	public int type;
	
	public Tile(int type)
	{
		this.type = type;
	}
	
	public String getImageSuffix()
	{
		return "_" + type;
	}
	
	public int getType()
	{
		return type;
	}
}
