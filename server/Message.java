package server;

import java.io.Serializable;

public class Message implements Serializable {
    int code;
    int action;
    String message;
    int bullets;

    public Message(int code, int action, String message, int bullets) {

    }
}
