package io.sspinc.api;

import io.sspinc.api.FSM.State;

public class ResponseForStateScreenNotFound extends Exception {
    public ResponseForStateScreenNotFound(int screen) {
        super("No corresponding screen found for screen_'" + screen + "'");
    }
}
