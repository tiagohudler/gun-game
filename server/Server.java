package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private static final int PORT = 6543;
    private int connectingClients = 0;

    public static void main(String[] args) {

        try {

            ServerSocket server = new ServerSocket(Server.PORT);
            Socket socket1, socket2;

            while(true) {
                System.out.println("Waiting for player 1");
                socket1 = server.accept();

                System.out.println("Waiting for player 2");
                socket2 = server.accept();

                GameThread newGame = new GameThread(socket1, socket2);
                newGame.start();

            }
        }
        catch(IOException e) {
            System.out.println(e);
        }



    }


}
