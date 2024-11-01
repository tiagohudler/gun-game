package client;
import resources.Actions;
import resources.Message;
import java.util.Scanner;

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

            message = (Message) input.readObject();
            System.out.println(message.message);

            message.message = "Ready to play";
            output.writeObject(message);


            Scanner scanner = new Scanner(System.in);
            String playerInput;

            while(true) {
                message = (Message) input.readObject();

                if(message.code == 2) {
                    System.out.println(message.message);
                    System.out.println("Please enter your action");
                    playerInput = scanner.nextLine();

                    switch(playerInput) {
                        case "r" : message.action = Actions.RELOAD; break;
                        case "s" : message.action = Actions.SHOOT; break;
                        case "d" : message.action = Actions.DEFEND; break;
                    }

                    output.writeObject(message);
                    continue;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

}
