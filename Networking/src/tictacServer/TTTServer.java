package tictacServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;



public class TTTServer {
	
	private static ArrayList<TTTClientConn> clients;
	private static ArrayList<String> messages;
	private static int numPlayers;
	private static String[] board;
	private static String gamestate;
	private static int turn;
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		ServerSocket ss = new ServerSocket(8888);
		clients = new ArrayList<TTTClientConn>();
		numPlayers = 0;
		board = new String[9];
		turn = 0;
		
		for (int i = 0; i < board.length; i++){
			board[i] = "N";
		}
		gamestate = "C";
		
		final int NANO = 1000000000;
		long endAt = System.nanoTime()/NANO + 480;
		ss.setSoTimeout(100);
		while (true){
			try{
				Socket s = ss.accept();
				clients.add(new TTTClientConn(s));
				System.out.print("Client connected");
			}
			catch (SocketTimeoutException e){
			}
		

		messages = new ArrayList<String>();
		
		for (TTTClientConn c: clients){
			
			InputStream inStream = c.getInputStream();
			String command = read(inStream);
			
			if(command != null)
			{
				if (command.startsWith("GAME")){
					if (!c.joined()){
						if (getParam("GAME", command).equals("P")){
							if (numPlayers == 0){
								c.join("X");
								numPlayers++;
								c.printMessage("200 X");
							}
							else if (numPlayers == 1){
								c.join("O");
								numPlayers++;
								c.printMessage("200 O");
								turn = 1;
							}
							else
								c.printMessage("403");
						}
						else if (getParam("GAME", command).equals("S")){
							c.join("S");
							c.printMessage("200");
						}
						else{
							c.printMessage("403");
						}
					}
					else
						c.printMessage("403");
				}
				
				else if (command.startsWith("MOVE")){
					if (gamestate.equals("C") && c.joined() && !c.getPlayerType().equals("S")){
								int index = Integer.parseInt(getParam("MOVE", command));
								if (index > 8 || index < 0)
									c.printMessage("400");
								else
								{
									if ((c.getPlayerType().equals("X") && turn == 1) || (c.getPlayerType().equals("O") && turn == 2)){
										if (board[index].equals("N")){
											board[index] = c.getPlayerType();
											checkGame(c);
											String boardString = "";
											for (int i = 0; i < board.length; i++){
												boardString += board[i];
											}
											c.printMessage("200");
											messages.add("001 " + boardString + " " + gamestate);
											
											if (turn == 1)
												turn = 2;
											else
												turn = 1;
										}
										else
											c.printMessage("403");
									}
									else
										c.printMessage("403");
								}
							}
							else
								c.printMessage("403");
						
					}
				else
					c.printMessage("400");
				

			}
		}
		
		
		if (messages.size() > 0)
			for (TTTClientConn c: clients)
				for (String message: messages)
					if(c.joined())
						c.printMessage(message);
		
		}
	}
	
	public static void checkGame(TTTClientConn c){
		if (board[0] == board[1] && board[1] == board[2] && board[0] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[0] == board[3] && board[3] == board[6] && board[0] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[0] == board[4] && board[4] == board[8] && board[0] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[1] == board[4] && board[4] == board[7] && board[1] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[2] == board[5] && board[5] == board[8] && board[2] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[2] == board[4] && board[4] == board[6] && board[2] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[3] == board[4] && board[4] == board[5] && board[3] == c.getPlayerType())
			gamestate = c.getPlayerType();
		else if (board[6] == board[7] && board[7] == board[8] && board[6] == c.getPlayerType())
			gamestate = c.getPlayerType();
		
		for (int i = 0; i < board.length; i++){
			if (board[i].equals("N"))
				return;
		}
		
		gamestate = "D";
	}
	
	public static String getParam(String command, String totalCommand){
		return totalCommand.substring(command.length() + 1, totalCommand.length()-2);
	}
	
	
	public static String read(InputStream in) throws IOException
	{
		int bytesAvailable = in.available();
		if(bytesAvailable > 0)
		{
			byte[] bytes = new byte[bytesAvailable];
			in.read(bytes);
			return new String(bytes);
		}
		else
			return null;
		
	}
}
