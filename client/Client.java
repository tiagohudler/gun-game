package client;
import resources.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class Client {

    public static void main(String[] args) {
        ObjectOutputStream output;
        ObjectInputStream input;
        Message message;

        try {
            Socket serverSocket = new Socket("localhost", 6543);

            output = new ObjectOutputStream(serverSocket.getOutputStream());
            input = new ObjectInputStream(serverSocket.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            message = (Message) input.readObject();
            System.out.println(message.message);

            message.message = "Ready to play";
            output.writeObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

}
