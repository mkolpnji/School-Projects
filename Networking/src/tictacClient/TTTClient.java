package tictacClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TTTClient {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	
	private static InputStream instream;
	private static PrintWriter out;
	private static String player;
	private static String gamestate;
	private static int numTurns;
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		String host;
		String resource;
		player = "";
		gamestate = "C";
		
		if (args.length == 2){
			host = args[0];
			resource = args[1];
		}
		else{
			host = "localhost";
			resource = "/";
		}
		
		final int PORT = 8888;
		Socket s = new Socket(host, PORT);
		instream = s.getInputStream();
		out = new PrintWriter(s.getOutputStream());
		Scanner text = new Scanner(System.in);
		Scanner in = new Scanner(instream);
		
		while(player.equals("")){
		
			System.out.println("Enter as a player or spectator? ENTER: P/S");
			
			while(!text.hasNext()){
			}
			out.println("GAME " + text.nextLine());
			out.flush();
			
			while (!in.hasNext()){
			}
			
			String command = in.nextLine();
			if (command.startsWith("200")){
				if (command.equals("200"))
					player = "S";
				else
					player = getParam("200", command);
				if (player.equals("S"))
					System.out.println("Joined as spectator.");
				else
					System.out.println("Joined as player " + player);
			}
			else if (command.startsWith("403")){
				System.out.println("Cannot join game.");
			}
			else if (command.startsWith("400")){
				System.out.println("Invalid command.");
			}
		}
		
		numTurns = 1;
		
		if (!player.equals("S")){
			System.out.println("Numbers representing positions on the board:" );
			System.out.println("0 1 2");
			System.out.println("3 4 5");
			System.out.println("6 7 8");
		}
		
		System.out.println("------------------------------");
		System.out.println();
		
		while (gamestate.equals("C")){
			if (!player.equals("S")){
			if ((numTurns % 2) == 1){
				System.out.println("It's X's turn.");
				if (player.equals("X")){
					boolean moveMade = false;
					while(!moveMade){
						System.out.println("Please make a move. ENTER: 0-8");
						while(!text.hasNext()){
						}
					
						out.println("MOVE " + text.nextLine());
						out.flush();
						
						while (!in.hasNextLine()){
						}
					
						String command = in.nextLine();
						if (command.startsWith("200")){
							moveMade = true;
						}
						else if (command.startsWith("403")){
							System.out.println("Invalid move.");
						}
						else if (command.startsWith("400")){
							System.out.println("Invalid command.");
						}
					}
				}
			}
			else{
				System.out.println("It's O's turn.");
				if (player.equals("O")){
					boolean moveMade = false;
					while(!moveMade){
						System.out.println("Please make a move. ENTER: 0-8");
						while(!text.hasNext()){
						}
					
						out.println("MOVE " + text.nextLine());
						out.flush();
						
						while (!in.hasNext()){
						}
					
						String command = in.nextLine();
						if (command.startsWith("200")){
							moveMade = true;
						}
						else if (command.startsWith("403")){
							System.out.println("Invalid move.");
						}
						else if (command.startsWith("400")){
							System.out.println("Invalid command.");
						}
					}
				}
			}
			}
				
			
			while (!in.hasNextLine()){
			}
			
			String command = in.nextLine();
			if (command.startsWith("001")){
					String param = getParam("001", command);
					String board = param.substring(0, 9);
					gamestate = getParam(board, param);
					
					System.out.println();
					System.out.println("Gameboard");
					System.out.println("------------------");
					for (int i = 0; i < board.length(); i++){
						char display = board.charAt(i);;
						if (board.charAt(i) == 'N')
							display = '_';
						
						if (i == 2 || i == 5 || i == 8)
							System.out.println(display);
						else
							System.out.print(display + " ");
					}
					System.out.println();
					
					
					if (gamestate.equals("D"))
						System.out.println("The game has ended with a draw.");
					else if (gamestate.equals("X"))
						System.out.println("X has won the game.");
					else if (gamestate.equals("O"))
						System.out.println("O has won the game.");
			}
			
			numTurns++;
		}
		
		s.close();
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
	
	public static String getParam(String command, String totalCommand){
		return totalCommand.substring(command.length() + 1, totalCommand.length());
	}
	

}
