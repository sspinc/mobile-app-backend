package io.sspinc.api;

import io.sspinc.api.FSM.State;

public class ScreenForStateNotFound extends Exception {
    public ScreenForStateNotFound(State state) {
        super("No corresponding screen found for state '" + state + "'");
    }
}
