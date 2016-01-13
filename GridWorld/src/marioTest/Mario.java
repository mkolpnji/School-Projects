package marioTest;

public class Mario extends Tile {

	public Mario(int type) {
		super(type);
	}
	
	public String getImageSuffix()
	{
		return "_" + type;
	}
	
	public void setType(int newType)
	{
		type = newType;
	}
}
