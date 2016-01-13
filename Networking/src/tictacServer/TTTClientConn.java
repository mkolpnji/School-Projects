package tictacServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TTTClientConn {

	private Socket s;
	private String player;
	private PrintWriter out;
	private InputStream in;	
	
	public TTTClientConn(Socket s) throws IOException{
		this.s = s;
		player = "";
		out = new PrintWriter(s.getOutputStream());
		in = this.s.getInputStream();
	}
	
	public InputStream getInputStream(){
		return in;
	}
	
	public void join(String playertype){
		player = playertype;
	}
	
	public boolean joined(){
		return !player.equals("");
	}
	
	public String getPlayerType(){
		return player;
	}
	
	public void printMessage(String message){
		out.println(message);
		out.flush();
	}
	
	public void close() throws IOException{
		s.close();
	}
}
