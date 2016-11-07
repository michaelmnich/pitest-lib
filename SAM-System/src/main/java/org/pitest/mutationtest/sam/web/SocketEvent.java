package org.pitest.mutationtest.sam.web;

import java.util.EventObject;

public class SocketEvent extends EventObject {
    private String _info;

    public SocketEvent(Object source, String info ) {
        super( source );
        _info = info;
    }
    public String info() {
        return _info;
    }

}
