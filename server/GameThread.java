package server;
import java.net.*;

class GameThread extends Thread{
    Game game = new Game();
    Socket socket1 = null;
    Socket socket2 = null;

    GameThread(Socket s1, Socket s2) {
        this.socket1 = s1;
        this.socket2 = s2;
    }

    public void run() {

    }
}
