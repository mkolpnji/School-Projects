package multiGrid;

public class Bomber extends Tile{
	
	public Bomber(int type) {
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
