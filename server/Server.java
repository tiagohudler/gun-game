package server;

import java.io.IOException;
import java.net.*;

public class Server {
    private final int PORT = 6543;
    private int connectingClients = 0;

    public Server() {

        try {

            ServerSocket server = new ServerSocket(this.PORT);
            Socket socket1, socket2;

            while(true) {

                socket1 = server.accept();
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
