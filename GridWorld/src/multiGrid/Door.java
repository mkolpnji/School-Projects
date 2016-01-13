package multiGrid;

public class Door extends Tile{
	
	public Door(int type){
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
