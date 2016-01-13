
public class Piece {
	
	private boolean O;
	
	public Piece(boolean type){
		O = type;
	}
	public boolean getType(){
		return O;
	}
	
	public String getText(){
		if (O == true)
			return "matters";
		return "NOTHING";
	}
}
