package resources;

import java.io.Serializable;

public class Message implements Serializable {
    public int code;
    public int action;
    public String message;
    public int bullets;

    public Message(int code, int action, String message, int bullets) {

    }
}
