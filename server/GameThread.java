package server;
import resources.Message;

import java.net.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class GameThread extends Thread{
    private final Game game = new Game();
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
        String p1Action, p2Action;
        int gameResult;

        try {
            output1 = new ObjectOutputStream(socket1.getOutputStream());
            output2 = new ObjectOutputStream(socket2.getOutputStream());
            input1 = new ObjectInputStream(socket1.getInputStream());
            input2 = new ObjectInputStream(socket2.getInputStream());

            // Define and first communication with players

            output1.writeObject(new Message(0, 0, "You are player 1"));
            input1.readObject();

            output2.writeObject(new Message(1, 0, "You are player 2"));
            input2.readObject();

            output1.writeObject(new Message(2, 0, "Game is starting"));
            output2.writeObject(new Message(2, 0, "Game is starting"));

            while(!this.isInterrupted()) {
                p1message = (Message) input1.readObject();
                p2message = (Message) input2.readObject();

                if (p2message.code == 2 && p1message.code == 2) {
                    gameResult = game.action(p1message.action, p2message.action);

                    p1message.code = gameResult;
                    p2message.code = gameResult;

                    p1message.playerHit = p2message.playerHit = game.playerHit;

                    if (gameResult == 2) {
                        p1Action = switch (p1message.action) {
                            case 1 -> "SHOOT";
                            case 2 -> "DEFEND";
                            case 3 -> "RELOAD";
                            default -> "";
                        };
                        p2Action = switch (p2message.action) {
                            case 1 -> "SHOOT";
                            case 2 -> "DEFEND";
                            case 3 -> "RELOAD";
                            default -> "";
                        };

                        // each player receives the other player's action
                        p1message.message = "You used " + p1Action + " and Player 2 used " + p2Action + ".\n" +
                            "You have " + game.p1Lives + " lives.\n" + "Player 2 has " + game.p2Lives + " lives.";

                        output1.writeObject(p1message);

                        p2message.message = "You used " + p2Action + " and Player 1 used " + p1Action + ".\n" +
                                "You have " + game.p2Lives + " lives.\n" + "Player 1 has " + game.p1Lives + " lives.";

                        output2.writeObject(p2message);
                    }
                    else if (gameResult == 1) {
                        p1message.message = "Round ended.\nYou (" + game.p1Points + ") x (" + game.p2Points + ") Player 2";
                        p2message.message = "Round ended.\nYou (" + game.p2Points + ") x (" + game.p1Points + ") Player 1";

                        output1.writeObject(p1message);
                        output2.writeObject(p2message);
                    }
                    else if (gameResult == 4) {
                        p2message.message = p1message.message = "Both players died. Lives and bullets are set to 1";

                        output1.writeObject(p1message);
                        output2.writeObject(p2message);
                    }
                    else if (gameResult == 3) {
                        p1message.message = "Game is over.\nScore: You (" + game.p1Games + ") x (" + game.p2Games + ") Player 2";
                        p2message.message = "Game is over.\nScore: You (" + game.p2Games + ") x (" + game.p1Games + ") Player 1";

                        output1.writeObject(p1message);
                        output2.writeObject(p2message);

                        p1message = (Message) input1.readObject();
                        p2message = (Message) input2.readObject();

                        if(p1message.code == 3 || p2message.code == 3) {
                            p1message.message = p2message.message = "One player doesn't want a rematch. Game terminated.";
                            p1message.code = p2message.code = 3;
                            output1.writeObject(p1message);
                            output2.writeObject(p2message);
                            socket1.close();
                            socket2.close();
                            this.interrupt();
                        }
                        else {
                            p1message.message = p2message.message = "Both players want a rematch. Starting another game.";
                            p1message.code = p2message.code = 2;
                            output1.writeObject(p1message);
                            output2.writeObject(p2message);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




    }
}
