package server;
import resources.Message;

import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class GameThread extends Thread{
    private Game game = new Game();
    private Socket socket1 = null;
    private Socket socket2 = null;

    GameThread(Socket s1, Socket s2) {
        this.socket1 = s1;
        this.socket2 = s2;
    }

    public void run() {
        ObjectOutputStream output1;
        ObjectOutputStream output2;
        ObjectInputStream input1;
        ObjectInputStream input2;
        Message p1message, p2message;

        try {
            output1 = new ObjectOutputStream(socket1.getOutputStream());
            output2 = new ObjectOutputStream(socket2.getOutputStream());
            input1 = new ObjectInputStream(socket1.getInputStream());
            input2 = new ObjectInputStream(socket2.getInputStream());

            // Define and first communication with players

            output1.writeObject(new Message(0, 0, "You are player 1", 1));
            p1message = (Message) input1.readObject();

            output2.writeObject(new Message(1, 0, "You are player 2", 1));
            p2message = (Message) input1.readObject();


            output1.writeObject(new Message(2, 0, "Game is starting", 1));
            output2.writeObject(new Message(2, 0, "Game is starting", 1));

            while(true) {
                p1message = (Message) input1.readObject();
                p2message = (Message) input2.readObject();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
