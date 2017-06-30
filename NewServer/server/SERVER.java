package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SERVER {

	/**
	 * LÖist contianign all client5s
	 */
	private Socket[] clients = new Socket[2];
	private ServerSocket serverSocket;
	private int currentPlayerId;
	private boolean gameEnded = false;

	public SERVER(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		// Host gets accepted	
		clients[0] = serverSocket.accept();
		System.out.println("first client connected");
		// waits for opponent	
		clients[1] = serverSocket.accept();
		System.out.println("second client connected");
		this.currentPlayerId = 0;
		//waits for the inputs
		clients[0].getInputStream().read();
		clients[1].getInputStream().read();
		//maximum time for inputs
		clients[0].setSoTimeout(60000);
		clients[1].setSoTimeout(60000);
		startGame();
	}

	public int getPort() {
		return serverSocket.getLocalPort();
	}

	public void startGame() throws IOException {
		//writes to the clients who is first and who is second
		clients[currentPlayerId].getOutputStream().write(1);
		clients[1-currentPlayerId].getOutputStream().write(2);
		while (gameEnded == false) {
			makeTurn();
			if (currentPlayerId == 0) {
				currentPlayerId = 1;
			} else {
				currentPlayerId = 0;
			}
		}
		shutdown();
	}
	
	private void shutdown()throws IOException {
	    System.out.println("Game has ended");
	    System.out.println("The winner is Player "+ currentPlayerId+1);
	    clients[0].close();
	    clients[1].close();
	 }
	/**
	 * will end the game and disconnect both clients
	 */
	public void endGame() {
		gameEnded = true;
	}

	/**
	 * Returns the id of the player that has to make its move now
	 * 
	 * @return id
	 */
	public int getCurrentPlayer() {
		return currentPlayerId;
	}

	public void makeTurn() throws IOException {
		int otherPlayerId = 0;
				if (currentPlayerId == 0) {
			otherPlayerId = 1;
		} else {
			otherPlayerId = 0;
		}
		OutputStream outputStream1 = clients[currentPlayerId].getOutputStream();
		OutputStream outputStream2 = clients[otherPlayerId].getOutputStream();
		InputStream inputStream1 = clients[currentPlayerId].getInputStream();
		InputStream inputStream2 = clients[otherPlayerId].getInputStream();		
		int x = inputStream1.read();
		int y = inputStream1.read();
		

		
		//sends data to other player
		outputStream2.write(x);
		outputStream2.write(y);
		//gets 1 for hit and 0 for water
		int hit = inputStream2.read();
		outputStream1.write(hit);
	}

}
