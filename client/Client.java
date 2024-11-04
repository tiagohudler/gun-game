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
        int bullets = 1;

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

                if(message.code == 2 || message.code == 1) {
                    System.out.println(message.message);
                    System.out.println("Please enter your action");
                    while(true) {
                        playerInput = scanner.nextLine();

                        switch(playerInput) {
                            case "r" : message.action = Actions.RELOAD; bullets +=1; break;
                            case "s" :
                                if(bullets > 0) {
                                    message.action = Actions.SHOOT;
                                    bullets -= 1;
                                } else {
                                    System.out.println("You have no bullets"); continue;
                                }
                                break;
                            case "d" : message.action = Actions.DEFEND; break;
                            default: System.out.println("Invalid action, please try again"); continue;
                        }
                        break;
                    }
                    message.code = 2;
                    output.writeObject(message);
                }
                else if(message.code == 4) {
                    bullets = 1;

                    System.out.println(message.message);
                    System.out.println("Please enter your action");
                    while(true) {
                        playerInput = scanner.nextLine();

                        switch(playerInput) {
                            case "r" : message.action = Actions.RELOAD; bullets +=1; break;
                            case "s" : message.action = Actions.SHOOT; bullets -= 1; break;
                            case "d" : message.action = Actions.DEFEND; break;
                            default: System.out.println("Invalid action, please try again"); continue;
                        }
                        break;
                    }
                    message.code = 2;
                    output.writeObject(message);
                }
                else if(message.code == 3) {
                    bullets = 1;

                    System.out.println(message.message);
                    System.out.println("Would you like to play again?");
                    while(true) {
                        playerInput = scanner.nextLine();

                        switch(playerInput) {
                            case "y" : message.code = 1; break;
                            case "n" : message.code = 3; break;
                            default: System.out.println("Invalid response, please try again"); continue;
                        }
                        break;
                    }

                    output.writeObject(message);

                    message = (Message) input.readObject();

                    System.out.println(message.message);

                    if(message.code == 3) {
                        break;
                    }
                    else {
                        System.out.println("Please enter your action");
                        while(true) {
                            playerInput = scanner.nextLine();

                            switch(playerInput) {
                                case "r" : message.action = Actions.RELOAD; bullets +=1; break;
                                case "s" : message.action = Actions.SHOOT; bullets -= 1; break;
                                case "d" : message.action = Actions.DEFEND; break;
                                default: System.out.println("Invalid action, please try again"); continue;
                            }
                            break;
                        }

                        output.writeObject(message);
                    }
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }

}
