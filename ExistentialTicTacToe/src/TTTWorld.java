
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import info.gridworld.world.World;
import info.gridworld.grid.*;

public class TTTWorld extends World<Piece>{
	
	private boolean playerType; 
	private int turnNum;
	
	public TTTWorld() throws IOException {
		setGrid(new BoundedGrid<Piece>(3, 3));
		setUpGame();
	}
	
	//7 = x, 4 = o
	
	public void step(){
		try {
			makeMove(getRandomEmptyLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setUpGame() throws IOException{
		turnNum = 0;
		int rand = (int)(Math.random() * 2 + 1);
		
		if (rand == 1)
			playerType = true;
		else
			playerType = false;
		
		saveBoard();
		
		if (playerType)
			enemyMove();
		updateMessage();
	}
	
	public boolean locationClicked(Location loc){
		try {
			makeMove(loc);
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {

		}
		return true;
	}
	
	private void makeMove(Location loc) throws IOException{
		if (checkBoard() != 0){
			for (int x = 0; x < 3; x++)
				for (int y = 0; y < 3; y++)
					getGrid().remove(new Location(x,y));
				setUpGame();
			return;
		}
		
		if (this.getGrid().get(loc) == null && checkBoard() == 0){
			this.getGrid().put(loc, new Piece(playerType));
			turnNum++;
			if (checkBoard() == 0)
				enemyMove();
			else
				saveBoard();
			
			saveBoard();
			updateMessage();
		}
	}
	
	private void updateMessage(){
		String message = "";
		if (playerType)
			message += "You are playing as 'YOURSELF.' (MATTERS) ";
		else
			message += "You are playing as 'YOURSELF.' (NOTHING) ";
		
		if (checkBoard() == 0)
			message += "It's your turn.";
		else if (checkBoard() == 1)
			message += "You lose. (Matters wins)";
		else if (checkBoard() == 2)
			message += "You lose. (Nothing wins)";
		else
			message += "Everyone loses.";
		
		setMessage(message);
	}
	
	private void enemyMove() throws IOException{
		if (saveBoard()){
			turnNum++;
			this.getGrid().put(getRandomEmptyLocation(), new Piece(!playerType));
		}
		else
		{
			ArrayList<String> aiMoves = new ArrayList<String>();
			File f = new File((turnNum + 1) + ".txt");
			Scanner s = new Scanner(f);
			while(s.hasNextLine())
				aiMoves.add(s.nextLine());
				
			s.close();
			
			String boardString = boardString()[0];
			
			ArrayList<String> movesToRemove = new ArrayList<String>();
			for (String aiMove : aiMoves){
				boolean futureMove = true;
				for (int i = 0; i < boardString.length() - 1; i++){
					if (boardString.charAt(i) != '0')
						if(boardString.charAt(i) != aiMove.charAt(i))
							futureMove = false;
				}
				if(!futureMove)
					movesToRemove.add(aiMove);
			}
			
			for (String removeMove : movesToRemove)
				aiMoves.remove(removeMove);
			
			for (String aiMove: aiMoves){
				if((!playerType && aiMove.endsWith("1")) || (playerType && aiMove.endsWith("2"))){
					for (int i = 0; i < boardString.length() - 1; i++)
						if(boardString.charAt(i) != aiMove.charAt(i))
							this.getGrid().put(new Location(i/3, i%3), new Piece(!playerType));
					System.out.println("test");
					turnNum++;
					return;
				}
			}
			
			for (String aiMove: aiMoves){
				ArrayList<String> playerMoves = new ArrayList<String>();
				File f2 = new File((turnNum + 2) + ".txt");
				Scanner s2 = new Scanner(f2);
				while(s2.hasNextLine())
					playerMoves.add(s2.nextLine());
				
				s2.close();
				
				ArrayList<String> movesToRemove2 = new ArrayList<String>();
				for (String playerMove : playerMoves){
					boolean futureMove = true;
					for (int i = 0; i < aiMove.length() - 1; i++){
						if (aiMove.charAt(i) != '0')
							if(aiMove.charAt(i) != playerMove.charAt(i))
								futureMove = false;
					}
					if(!futureMove)
						movesToRemove2.add(playerMove);
				}
				
				for (String removeMove : movesToRemove2)
					playerMoves.remove(removeMove);
				
				for (String playerMove: playerMoves){
					if((playerType && playerMove.endsWith("1")) || (!playerType && playerMove.endsWith("2"))){
						for (int i = 0; i < aiMove.length() - 1; i++)
							if(aiMove.charAt(i) != playerMove.charAt(i))
								this.getGrid().put(new Location(i/3, i%3), new Piece(!playerType));
						System.out.println("test2");
						turnNum++;
						return;
					}
				}
			}
			
			turnNum++;
			this.getGrid().put(getRandomEmptyLocation(), new Piece(!playerType)); //temp
		}
	}
	
	private boolean saveBoard() throws IOException{
		boolean containsString = false;
		try {
			File f = new File(turnNum + ".txt");
			Scanner s = new Scanner(f);
		} 
		catch (FileNotFoundException e) {
			PrintWriter printWriter = new PrintWriter(new File(turnNum + ".txt"));
			printWriter.print("");
			printWriter.close();
		}
		
		Scanner s = new Scanner(new File(turnNum + ".txt"));
		while(s.hasNextLine()){
			String line = s.nextLine();
			if (line.equals(boardString()[0]))
				containsString = true;
		}
		
		if (containsString)
			return false;
		
		FileWriter file = new FileWriter(turnNum + ".txt", true);
		PrintWriter printWriter = new PrintWriter (file);
		
		String[] rotString = boardString();
		for (int i = 0; i < 8; i++){
			
			boolean containsStringTwo = false;
			for (int y = 0; y < i; y++)
				if (rotString[y].equals(rotString[i]))
					containsStringTwo = true;
			
			if(!containsStringTwo)
				printWriter.println(rotString[i]);
		}
		
		printWriter.close();
		return true;
	}
	
	private String[] boardString(){
		String[] rotatedStrings = new String[8];
		
		String boardString = "";
		for (int i = 0; i < 9; i++)
			boardString += getPieceValue(i);
		boardString += checkBoard();
		rotatedStrings[0] = boardString;
		
		boardString = "";
		for (int i = 8; i >= 0; i--)
			boardString += getPieceValue(i);
		boardString += checkBoard();
		rotatedStrings[1] = boardString;
		
		boardString = "";
		for (int i = 0; i < 3; i++)
			boardString += getPieceValue(i) + "" + getPieceValue(i + 3) + "" + getPieceValue(i + 6);
		boardString += checkBoard();
		rotatedStrings[2] = boardString;
		
		boardString = "";
		for (int i = 2; i >= 0; i--)
			boardString += getPieceValue(i)+ "" + getPieceValue(i + 3)+ "" + getPieceValue(i + 6);
		boardString += checkBoard();
		rotatedStrings[3] = boardString;
		
		boardString = "";
		for (int i = 0; i < 3; i++)
			boardString += getPieceValue(i+ 6)+ "" + getPieceValue(i + 3)+ "" + getPieceValue(i);
		boardString += checkBoard();
		rotatedStrings[4] = boardString;
		
		boardString = "";
		for (int i = 2; i >= 0; i--)
			boardString += getPieceValue(i+ 6)+ "" + getPieceValue(i + 3)+ "" + getPieceValue(i);
		boardString += checkBoard();
		rotatedStrings[5] = boardString;
		
		boardString = "";
		for (int i = 2; i >= 0; i--)
			boardString += getPieceValue(i*3)+ "" + getPieceValue(i*3 + 1)+ "" + getPieceValue(i*3 + 2);
		boardString += checkBoard();
		rotatedStrings[6] = boardString;
		
		boardString = "";
		for (int i = 0; i < 3; i++)
			boardString += getPieceValue(i*3 + 2)+ "" + getPieceValue(i*3 + 1)+ "" + getPieceValue(i*3);
		boardString += checkBoard();
		rotatedStrings[7] = boardString;
		
		return rotatedStrings;
	}
	
	private int checkBoard(){
		for (int i = 0; i < 3; i++)
			if (horizontal(i) == 12 || vertical(i) == 12)
				return 1;
			else if (horizontal(i) == 21 || vertical(i) == 21)
				return 2;
		if (diagonal(true) == 12 || diagonal(false) == 12)
			return 1;
		if (diagonal(true) == 21 || diagonal(false) == 21)
			return 2;
		
		boolean emptyPiece = false;
		for (int i = 0; i < 9; i++)
			if (getPieceValue(i) == 0)
				emptyPiece = true;
		if(!emptyPiece)
			return 3;
		
		return 0;
	}
	
	
	
	private int horizontal(int row){
		return getPieceValue(row*3) + getPieceValue(row*3 + 1) + getPieceValue(row*3 + 2);
	}
	
	private int vertical(int column){
		return getPieceValue(column) + getPieceValue(column + 3) + getPieceValue(column + 6);
	}
	
	private int diagonal(boolean diag){
		if (diag)
			return getPieceValue(0) + getPieceValue(4) + getPieceValue(8);
		return getPieceValue(2) + getPieceValue(4) + getPieceValue(6);
	}
	
	private int getPieceValue(int piece){
		if (getGrid().get(new Location(piece/3, piece%3)) == null)
			return 0;
		if (getGrid().get(new Location(piece/3, piece%3)).getType())
			return 4;
		return 7;
	}
}
