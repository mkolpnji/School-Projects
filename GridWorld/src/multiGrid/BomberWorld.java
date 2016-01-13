package multiGrid;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;


public class BomberWorld extends World<Tile>{

	public int score;
	public int time;
	public int bombs;
	public Bomber bomber = new Bomber(1);
	public int bomberR;
	public int bomberC;
	public int level;
	
	public BomberWorld(BoundedMultiGrid<Tile> grid){
		setGrid(grid);
		setMessage("This is a semi-clone of Bomberman made in Gridworld. \nUse the WASD keys to move and the space key to drop a bomb. \nGet points from blowing up "
				+ "bricks and head for the exit before time runs out. \nClick Run to begin!");
		System.setProperty("info.gridworld.gui.selection", "hide");
        System.setProperty("info.gridworld.gui.tooltips", "hide");
        System.setProperty("info.gridworld.gui.frametitle", "Bomberman");
		score = 0;
		
		level = 1;
		setLevel();
	}
	
	public void setLevel(){
		for (int r = 0; r < getGrid().getNumRows(); r++)
			for (int c = 0; c < getGrid().getNumCols(); c++){
				add(new Location(r, c), new Tile(1));
				if (r % 2 == 1 && c % 2 == 1)
					add(new Location(r, c), new Tile(2));
			}
		
		int i = 0;
		
		while (i < (int)(level * 1.3) + 4){
			int r = (int)(Math.random() * getGrid().getNumRows());
			int c = (int)(Math.random() * getGrid().getNumCols());
			if (!(r == 0 && c == 1) && !(r == 0 && c == 0) && !(r == 1 && c == 0) && !(r == getGrid().getNumRows() - 1 && c == getGrid().getNumCols()))
			{
				if (getGrid().get(new Location(r, c)).getType() == 1){
					add(new Location(r, c), new Tile(3));
					i++;
				}
			}
		}
		
		add(new Location(getGrid().getNumRows() - 1, getGrid().getNumCols() - 1), new Door(1));
		
		add(new Location (0, 0), bomber);
		bomberR = 0;
		bomberC = 0;
		time = 51;
		bombs = 21 - (int)(level * 1.2);
		if (bombs < 5)
			bombs = 5;
	}
	
	public void step(){
		if (time > 0)
			time--;
		for (int r = 0; r < getGrid().getNumCols(); r++)
			for (int c = 0; c< getGrid().getNumRows(); c++)
			{
				Location curr = new Location(r, c);
				if (getGrid().get(curr) instanceof Bomb){
					Bomb bomb = (Bomb)getGrid().get(curr);
					if (bomb.getType() == 1){
						if (bomb.getTick() < 2)
							bomb.addTick();
						else{
							bomb.setType(2);
							Location bombLoc = bomb.getLoc();
							int bRow = bombLoc.getRow();
							int bCol = bombLoc.getCol();
							if (getGrid().isValid(new Location(bRow + 1, bCol)) && getGrid().get(new Location(bRow + 1, bCol)).getType() != 2){
								Bomb newBomb = new Bomb(bRow + 1, bCol, 2);
								add(new Location(bRow + 1, bCol), newBomb);
							}
								
							if (getGrid().isValid(new Location(bRow - 1, bCol)) && getGrid().get(new Location(bRow - 1, bCol)).getType() != 2)
								add(new Location(bRow - 1, bCol), new Bomb(bRow - 1, bCol, 2));
							if (getGrid().isValid(new Location(bRow, bCol + 1)) && getGrid().get(new Location(bRow, bCol + 1)).getType() != 2){
								Bomb newBomb = new Bomb(bRow, bCol + 1, 2);
								add(new Location(bRow, bCol + 1), newBomb);
							}
								
							if (getGrid().isValid(new Location(bRow, bCol - 1)) && getGrid().get(new Location(bRow, bCol - 1)).getType() != 2)
								add(new Location(bRow, bCol - 1), new Bomb(bRow, bCol - 1, 2));
						}
					}
					else if (bomb.getType() < 5)
						bomb.setType(bomb.getType() + 1);
					else{
						Location bombLoc = bomb.getLoc();
						remove(bomb.getLoc());
						if (getGrid().get(bombLoc).getType() != 1 || getGrid().get(bombLoc) instanceof Bomber){
							Tile removedTile = remove(bombLoc);
							if (removedTile.getType() == 3)
								score += 50;
						}
					}
				}
			}
		setMessage("Level: " + level +  "\tScore: " + score + "\t" + "Time: " + time + "\tBombs: " + bombs);
		if (time == 0 && getGrid().get(new Location(bomberR, bomberC)) == bomber)
			getGrid().remove(new Location(bomberC, bomberR));
		if (getGrid().get(new Location(bomberR, bomberC)) != bomber)
			setMessage("Level: " + level +  "\tScore: " + score + "\t" + "Time: " + time + "\tBombs: " + bombs + "\nGAME OVER!");
	}
	
	public boolean locationClicked(Location loc)
	{
		return true;
	}
	
	public boolean keyPressed(String description, Location loc)
	{
		if (getGrid().get(new Location(bomberR, bomberC)) != bomber)
			return true;
		if (description.equals("D")){
			bomber.setType(4);
			if (bomberC < getGrid().getNumCols() - 1 )
				if (getGrid().get(new Location(bomberR, bomberC + 1)).getType() == 1)
					if (!(getGrid().get(new Location(bomberR, bomberC + 1)) instanceof Bomb))
						moveBomber(bomberR, bomberC + 1);
		}
		if (description.equals("A")){
			bomber.setType(2);
			if (bomberC > 0)
				if (getGrid().get(new Location(bomberR, bomberC - 1)).getType() == 1)
					if (!(getGrid().get(new Location(bomberR, bomberC - 1)) instanceof Bomb))
						moveBomber(bomberR, bomberC - 1);
		}
		
		if (description.equals("W")){
			bomber.setType(3);
			if (bomberR > 0)
				if (getGrid().get(new Location(bomberR - 1, bomberC)).getType() == 1)
					if (!(getGrid().get(new Location(bomberR - 1, bomberC)) instanceof Bomb))
						moveBomber(bomberR - 1, bomberC);
		}
			
		if (description.equals("S")){
			bomber.setType(1);
			if (bomberR < getGrid().getNumRows() - 1)
				if (getGrid().get(new Location(bomberR + 1, bomberC)).getType() == 1)
					if (!(getGrid().get(new Location(bomberR + 1, bomberC)) instanceof Bomb))
						moveBomber(bomberR + 1, bomberC);
		}
		
		if (description.equals("SPACE")){
			if (bombs > 0){
			remove(new Location(bomberR, bomberC));
			add(new Location(bomberR, bomberC), new Bomb(bomberR, bomberC, 1));
			add(new Location(bomberR, bomberC), bomber);
			bombs--;
			}
		}
				
		return true;
		
	}
	
	private void moveBomber(int newBomberR, int newBomberC){
		remove(new Location (bomberR, bomberC));
		bomberR = newBomberR;
		bomberC = newBomberC;
		if (getGrid().get(new Location(bomberR, bomberC)) instanceof Door){
			level++;
			score += time;
			setLevel();
			return;
		}
		add(new Location(bomberR, bomberC), bomber);
		
	}
}
