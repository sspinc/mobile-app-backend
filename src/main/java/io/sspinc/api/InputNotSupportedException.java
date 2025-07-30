package io.sspinc.api;
import io.sspinc.api.FSM.State;
import io.sspinc.api.FSM.Input;

public class InputNotSupportedException extends Exception {
    public InputNotSupportedException(State state, Input input) {
        super("Input '" + input + "' is not supported in state '" + state + "'");
    }
}
