import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;
import client.CLIENT;
import server.SERVER;
import client.SPIELFELD;
public class LOBBY {

    private CLIENT client = new CLIENT();
    private SERVER server = null;
    boolean gameRunning = false;

    /**
     * Hauptprogramm zum Erzeugen des Serverobjekts
     * @param args keine Parameter beim Programmaufruf erforderlich
     */
    public static void main(String[] args)throws UnknownHostException, IOException, InterruptedException {

        Scanner reader = new Scanner(System.in); // Reading from System.in
        System.out.println("Enter 0 to start a new server or 1 to connect to an existing one: ");
        int n = reader.nextInt();
        //reader.close();
        LOBBY lobby = new LOBBY();
        if(n==0){
            lobby.createServer(6000);
        }else{
            System.out.println("Enter the hostname: ");
            String hostname = "127.0.0.1";//reader.nextLine();
            lobby.joinServer(hostname,6000);
        }

    }

    public void createServer(int port)throws UnknownHostException, IOException, InterruptedException{
        System.out.println("0");    
        gameRunning = true;
        Runnable serverRun = new Runnable() {

                @Override
                public void run() {
                    try {
                        server = new SERVER(port);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }				
                }
            };
        Runnable gameState = new Runnable() {
                @Override
                public void run() {		
                    try{
                        Thread.sleep(10000);
                    }catch(InterruptedException e){

                    }
                    while (gameRunning) {
                        if (client.GameEnded()) {
                            System.out.println("GameEnd");
                            server.endGame();
                        }
                    }
                }
            };
        Thread serverThread = new Thread(serverRun);
        Thread gameThread = new Thread(gameState);
        System.out.println("Creating Server...");
        serverThread.start();
        gameThread.start();
        System.out.println("Server created...");
        Thread.sleep(3000);
        client.connect("127.0.0.1",port);		
        gameRunning = false;
    }

    public void joinServer(String hostname,int port) throws UnknownHostException, IOException {
        client.connect(hostname,port);
    }

    /** 
     * Will return the SPIELFELD object of the client.
     * Changing the returned object will result in changes in the clients object
     *@return The SPIELFELD object of the client
     */
    public SPIELFELD getFields(){
        return client.getFields();
    }

}

