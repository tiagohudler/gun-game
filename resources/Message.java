package resources;

import java.io.Serializable;

public class Message implements Serializable {
    public int code;
    public int action;
    public String message;
    public boolean playerHit;

    public Message(int code, int action, String message) {
        this.code = code;
        this.action = action;
        this.message = message;
    }
}
