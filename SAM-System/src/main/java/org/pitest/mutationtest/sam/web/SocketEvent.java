package org.pitest.mutationtest.sam.web;

import java.util.EventObject;

public class SocketEvent extends EventObject {
    private String _mood;

    public SocketEvent(Object source, String mood ) {
        super( source );
        _mood = mood;
    }
    public String mood() {
        return _mood;
    }

}
