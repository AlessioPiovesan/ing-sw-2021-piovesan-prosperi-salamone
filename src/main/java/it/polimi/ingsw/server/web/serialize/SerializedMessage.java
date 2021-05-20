package it.polimi.ingsw.server.web.serialize;

import java.io.Serializable;

public class SerializedMessage implements Serializable {
    public final Message message;
    public final Action action;

    public SerializedMessage(Message message) {
        this.message = message;
        this.action = null;
    }

    public SerializedMessage(Action action) {
        this.action = action;
        this.message = null;
    }
}
