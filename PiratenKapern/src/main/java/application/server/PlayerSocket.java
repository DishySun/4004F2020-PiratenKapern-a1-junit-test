package application.server;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class PlayerSocket {
	private Socket s;
	private BufferedReader in;
	private PrintWriter out;
	
	public PlayerSocket(Socket s) throws IOException {
		this.s = s;
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream());
	}
	
	public String askName() throws IOException {
		out.println("name?");
		out.flush();
		String s = in.readLine();
		return s;
	}

	public String getCommand(String string) throws IOException {
		out.println(string);
		out.flush();
		return in.readLine();
	}

	public void send(String msg) {
		out.println(msg);
		out.flush();
	}

	public void shutdown() {
		try {
			this.s.shutdownInput();
			this.s.shutdownOutput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
