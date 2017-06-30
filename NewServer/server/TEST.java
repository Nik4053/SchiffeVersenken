package server;

import java.io.IOException;

public class TEST {
	private SERVER server;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		SERVER server = new SERVER(6000);
		server.startGame();
	}

}
