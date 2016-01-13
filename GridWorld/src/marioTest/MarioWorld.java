package marioTest;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;

public class MarioWorld extends World<Tile>{

	public int pos;
	public int ypos;
	public int jump;
	public int score;
	public Mario mario = new Mario(1);
	
	public MarioWorld(Grid<Tile> grid)
	{
		setGrid(grid);
		setMessage("");
		score = 0;
		pos = 0;
		ypos = 4;
		jump = 0;
		updateTiles();
	}
	
	public boolean locationClicked(Location loc)
	{
		return true;
	}
	private void updateTiles()
	{
		for (int r = 0; r < getGrid().getNumCols(); r++)
			for (int c = 0; c< getGrid().getNumRows(); c++)
			{
				add(new Location(c, r), new Tile(tiles[r + pos][c]));
				if ((tiles[r + pos][c] >= 7 && tiles[r + pos][c] <= 8) || tiles[r + pos][c] == 11)
					tiles[r + pos][c]++;
				else if (tiles[r + pos][c] == 9)
					tiles[r + pos][c] = 7;
				else if (tiles[r + pos][c] == 12)
					tiles[r + pos][c] = 11;
				else if (tiles[r + pos][c] == 13)
					tiles[r + pos][c] = 1;
				/*if ((tiles[r + pos][c] >= 11 && tiles[r + pos][c] <= 12))
				{
					if(tiles[r + pos][c] > 0 && tiles[r + pos - 1][c] == 1)
					{
						tiles[r + pos - 1][c] =  tiles[r + pos][c];
						tiles[r + pos][c] = 0;
					}
				}*/
			}
		add(new Location(ypos, 1), mario);
	}
	
	public void step()
	{
		if (!getGrid().isValid(new Location (ypos +1, 1)))
		{
			die();
		}
		if (checkCoinBlockTile(new Location(ypos - 1, 1)))
		{
			score += 200;
			tiles[pos + 1][ypos - 1] = 10;
		}
		if (checkEnemyTile(new Location(ypos + 1, 1)))
		{
			score += 500;
			tiles[pos + 1][ypos + 1] = 13;
		}
		if (checkAirTile(new Location (ypos + 1, 1)))
			ypos++;
		else
		{
			mario.setType(1);
			jump = 0;
		}
		
		updateTiles();
		
		setMessage("Score: " + score);
	}
	public boolean keyPressed(String description, Location loc)
	{
		if (description.equals("D"))
			if (pos + 10 < tiles.length)
				if (checkAirTile(new Location (ypos, 2)))
				{
					mario.setType(2);
					pos++;
					if (tiles[pos + 1][ypos] == 14)
					{
						tiles[pos + 1][ypos] = 1;
						score += 200;
					}
				}
				else if (checkEnemyTile(new Location(ypos, 2)))
					die();
				
		if (description.equals("A"))
			if (pos > 0)
				if (tiles[pos][ypos] == 1)
				{
					mario.setType(4);
					pos--;
					if (tiles[pos + 1][ypos] == 14)
					{
						tiles[pos + 1][ypos] = 1;
						score += 200;
					}
				}
				else if (tiles[pos][ypos] == 11 || tiles[pos][ypos] == 12)
					die();
		if (description.equals("SPACE"))
			if (checkAirTile(new Location (ypos - 1, 1)))
			{
				jump++;
				if (jump < 3)
				{
					mario.setType(3);
					ypos--;
				}
			}
		updateTiles();
		return true;
	}
	
	private boolean checkAirTile(Location loc)
	{
		return getGrid().isValid(loc) && (getGrid().get(loc).getType() == 1 || getGrid().get(loc).getType() == 14);
	}
	
	private boolean checkCoinBlockTile(Location loc)
	{
		return getGrid().isValid(loc) && (getGrid().get(loc).getType() == 7 || getGrid().get(loc).getType() == 8 || getGrid().get(loc).getType() == 9);
	}
	
	private boolean checkEnemyTile(Location loc)
	{
		return getGrid().isValid(loc) && (getGrid().get(loc).getType() == 11 || getGrid().get(loc).getType() == 12);
	}
	
	
	private void die()
	{
		ypos = 4;
		pos = 0;
		score = 0;
	}
	
	private int[][] tiles = {{2, 1, 1, 1, 1, 2
	},{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 11, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 7, 1, 1, 2}
	,{2, 1, 7, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 2, 2}
	,{2, 1, 1, 1, 2, 2}
	,{2, 1, 1, 1, 2, 2}
	,{2, 1, 1, 1, 2, 2}
	,{2, 1, 1, 1, 2, 2}
	,{2, 1, 1, 2, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 5, 3, 2}
	,{2, 1, 1, 6, 4, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 5, 3, 3, 2}
	,{2, 1, 6, 4, 4, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 11, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 1}
	,{2, 1, 1, 1, 1, 1}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 14, 2}
	,{2, 1, 1, 1, 14, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}
	,{2, 1, 1, 1, 1, 2}};
}
