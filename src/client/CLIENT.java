package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CLIENT {
	private Socket socket;
	/**
	 * is true if the last turn has had a hit
	 */
	private boolean hit;
	/**
	 * True if the current turn is your turn
	 */
	private boolean yourTurn;
	private SPIELFELD spielfeld;

	public CLIENT() {
		this.spielfeld = new SPIELFELD();
	}

	/**
	 * Connects to the given server
	 * 
	 * @param hostname
	 *            the ip of the server (127.0.0.1 for internal)
	 * @param port
	 *            the port of the server
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect(String hostname, int port) throws UnknownHostException, IOException {
		socket = new Socket(hostname, port);
		int place = socket.getInputStream().read();
		System.out.println("Your place: " + place);
		if (place == 1) {
			this.yourTurn = true;
		} else {
			this.yourTurn = false;
		}
		StartGame();
	}

	/**
	 * Call this method to start the game.
	 * <p>
	 * Both clients have to call this method in order for the server to start
	 * the game
	 * 
	 * @throws IOException
	 */
	public void Start() throws IOException {
		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Enter 1: ");
		int n = reader.nextInt();
		if (n == 1) {
			socket.getOutputStream().write(1);
		}
	}

	private void StartGame() {
		Runnable serverRun = new Runnable() {
			@Override
			public void run() {
				int turnCount = 0;
				while (GameEnded() == false) {
					turnCount++;
					System.out.println("Turn " + turnCount);
					if (yourTurn) {
						System.out.println("It is your turn");
						try {
							sendTurn(getYourTurnX(), getYourTurnY());
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						System.out.println("It is your opponents turn");
						try {
							recieveTurn();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (yourTurn) {
						yourTurn = false;
					} else {
						yourTurn = true;
					}
					System.out.println("----------------------------------------------");
				}

			}
		};
		Thread serverThread = new Thread(serverRun);
		serverThread.start();
	}

	/**
	 * TODO
	 * 
	 * @return true if the game is over
	 */
	public boolean GameEnded() {
		return false;
	}

	private int getYourTurnX() {
		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Enter x: ");
		int n = reader.nextInt();
		// reader.close();
		return n;
	}

	private int getYourTurnY() {
		Scanner reader = new Scanner(System.in); // Reading from System.in
		System.out.println("Enter y: ");
		int n = reader.nextInt();
		// reader.close();
		return n;
	}

	/**
	 * Sends the turn made by the user to the opponent
	 * 
	 * @param x
	 * @param y
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void sendTurn(int x, int y) throws IOException, InterruptedException {
		OutputStream outputStream = socket.getOutputStream();
		// outputStream.write(x*10+y);
		outputStream.write(x);
		outputStream.write(y);
		InputStream inputStream = socket.getInputStream();
		int hit2 = inputStream.read();
		this.hit = false;
		if (hit2 == 1) {
			System.out.println("You hit an opponents ship");
			this.hit = true;
		} else {
			System.out.println("You hit the water");
		}
	}

	/**
	 * gets the enemy turn and changes the SPIELFELD accordingly
	 * 
	 * @throws IOException
	 */
	private void recieveTurn() throws IOException {
		InputStream inputStream = socket.getInputStream();
		int x = inputStream.read();

		int y = inputStream.read();
		System.out.println("Opponent chose (" + x + "/" + y + ")");
		OutputStream outputStream = socket.getOutputStream();
		boolean hit = spielfeld.setEnemyHit(x, y);

		int h = 0;
		if (hit) {
			System.out.println("Opponent hit one of your ships");
			h = 1;
		} else {
			System.out.println("Opponent hit the water");
		}
		outputStream.write(h);

	}

	/**
	 * 
	 * @return true if the last of your turn has been a hit
	 */
	public boolean getHit() {
		return hit;
	}

	/**
	 * 
	 * @return the SPIELFELD object of this class
	 */
	public SPIELFELD getFields() {
		return spielfeld;
	}
}
