package application.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private final String HOST = "localhost";
	private final int PORT = 4004;
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	
	public void start() throws UnknownHostException, IOException {
		s = new Socket(HOST,PORT);
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream());

		while (true){
			String inMsg = in.readLine();
			if (inMsg.equals("name?")) {
				String s = this.getName();
				out.println(s);
				out.flush();
			}else if (inMsg.contains("What are we doing next?") || inMsg.contains("Pleaser enter again: ")) {
				String s = this.getCommand(inMsg);
				out.println(s);
				out.flush();
			}else if (inMsg.equals("game end")) {
				this.endGame();
				return;
			}else System.out.println(inMsg);
		}
	}
	
	private String getName() {
		System.out.print("Enter your name: ");
		return new Scanner(System.in).nextLine();
	}
	
	private String getCommand(String prompt) {
		System.out.println(prompt);
		return new Scanner(System.in).nextLine();
	}
	
	private void endGame() throws IOException {
		System.out.println("The game has ended. Have a nice day.");
		s.shutdownInput();
		s.shutdownOutput();
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		Client c = new Client();
		c.start();
	}
}
