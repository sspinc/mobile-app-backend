package io.sspinc.api;

import java.util.Arrays;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.sspinc.api.FSM.State;
import io.sspinc.api.FSM.Input;

public class BusinessLogic {
    /**
     * Stores the current state for each session
     */
    private HashMap<String, State> sessionState = new HashMap<>();

    /**
     * Stores the screen ID associated to each state
     */
    private HashMap<State, Integer> stateScreen = new HashMap<>();

    /**
     * Stores the GraphQL response required to populate to each screen
     */
    private HashMap<Integer, String> screenResponse = new HashMap<>();

    /**
     * Stores the valid inputs for each state
     */
    private HashMap<State, Input[]> validStateInputs = new HashMap<>();

    /**
     * Current session ID
     */
    private String sessionID;

    /**
     * Logger
     */
    private static final Logger logger = LogManager.getLogger(BusinessLogic.class);


    /**
     * Set the current state
     */
    public void setState(State state, Input input) {
        logger.info("\nSessionID: {} | {} --> {} --> {}", sessionID, getState(), input, state);
        sessionState.put(sessionID, state);
    }


    /**
     * Get the current state
     */
    public State getState() {
        return sessionState.get(sessionID);
    }
    
    
    /**
     * Constructor
     */
    public BusinessLogic() {
        initScreenValues();
        initStateScreens();
        initValidStateInput();
    }


    /**
     * Extracts the sessionID from the parameters
     */
    private String getSessionID() {
        // FIXME: Use user's info etc.

        return sessionID;
    }


    /**
     * Sets the session ID for the current execution
     * @param sessionID
     */
    private void setOrCreateSessionID(String sessionID) {
        if (null != sessionID) {
            this.sessionID = sessionID;
        }
        else {
            this.sessionID = java.util.UUID.randomUUID().toString();
            sessionState.put(this.sessionID, State.Init);            
        }
    }


    /**
     * Initializes the values to return for each screen
     */
    private void initScreenValues() {
        // FIXME: Get this from DB

        for (int screenId=1; screenId < 15; screenId++) {
            screenResponse.put(screenId, 
                                "    \"data\": {\n" +
                                "        \"screenResponse\": {\n" +
                                "            \"screenId\": \"screen_" + screenId + "\"\n" +
                                "        }\n" +
                                "    }\n");
        }
    }


    /**
     * Initializes the association between states and screens
     */
    private void initValidStateInput() {
        // FIXME: Get this from DB

        validStateInputs.put(State.Init, new Input[] {Input.open_fp});
        validStateInputs.put(State.Calculating_Prediction_A, new Input[] {Input.ready});
        validStateInputs.put(State.Calculating_Prediction_B, new Input[] {Input.ready});
        validStateInputs.put(State.Calculator_A, new Input[] {Input.size_selected});
        validStateInputs.put(State.Calculator_B, new Input[] {Input.size_selected, Input.back});
        validStateInputs.put(State.Calculator_C, new Input[] {Input.size_selected});
        validStateInputs.put(State.Clearing_Fit_Profile, new Input[] {Input.ready});
        validStateInputs.put(State.Delete_Seed_Confirmation, new Input[] {Input.approved, Input.declined});
        validStateInputs.put(State.Fit_Prediction_A, new Input[] {Input.next});
        validStateInputs.put(State.Fit_Prediction_B, new Input[] {Input.done});
        validStateInputs.put(State.Fit_Profile_Cleared, new Input[] {Input.done});
        validStateInputs.put(State.Fit_Profile, new Input[] {Input.add_seed, Input.remove_seed});
        validStateInputs.put(State.How_does_it_work, new Input[] {Input.what_is_my_fit_profile, Input.privacy_policy});
        validStateInputs.put(State.Manual_Seed_Saved, new Input[] {Input.done});
        validStateInputs.put(State.Privacy_Policy, new Input[] {Input.what_is_my_fit_profile, Input.how_it_works});
        validStateInputs.put(State.What_is_my_Fit_Profile, new Input[] {Input.privacy_policy, Input.how_it_works});
    }


    /**
     * Initializes the list of valid inputs for each state
     */
    private void initStateScreens() {
        // FIXME: Get this from DB

        stateScreen.put(State.Init, 0);
        stateScreen.put(State.Calculating_Prediction_A, 2);
        stateScreen.put(State.Calculating_Prediction_B, 2);
        stateScreen.put(State.Calculator_A, 1);
        stateScreen.put(State.Calculator_B, 6);
        stateScreen.put(State.Calculator_C, 14);
        stateScreen.put(State.Clearing_Fit_Profile, 8);
        stateScreen.put(State.Delete_Seed_Confirmation, 7);
        stateScreen.put(State.Fit_Prediction_A, 2);
        stateScreen.put(State.Fit_Prediction_B, 13);
        stateScreen.put(State.Fit_Profile_Cleared, 9);
        stateScreen.put(State.Fit_Profile, 5);
        stateScreen.put(State.How_does_it_work, 10);
        stateScreen.put(State.Manual_Seed_Saved, 4);
        stateScreen.put(State.Privacy_Policy, 12);
        stateScreen.put(State.What_is_my_Fit_Profile, 11);
    }


    /**
     * Processes the input (aka action) to move to the next state and output the values for next screen
     * @param input The input received from the app
     * @param sessionID The ID of the session
     * @param paramString The additional information
     * @return The GraphQL structure containing the next screen ID and values to populate it
     */
    public String processInput(Input input, String sessionID, String paramString) {
        String output = "{}";
        setOrCreateSessionID(sessionID);

        try {
            switch (getState()) {
                case Init:
                    output = execute_Init(input, paramString);
                    break;
                case Calculating_Prediction_A:
                    output = execute_Calculating_Prediction_A(input, paramString);
                    break;
                case Calculating_Prediction_B:
                    output = execute_Calculating_Prediction_B(input, paramString);
                    break;
                case Calculator_A:
                    output = execute_Calculator_A(input, paramString);
                    break;
                case Calculator_B:
                    output = execute_Calculator_B(input, paramString);
                    break;
                case Calculator_C:
                    output = execute_Calculator_C(input, paramString);
                    break;
                case Clearing_Fit_Profile:
                    output = execute_Clearing_Fit_Profile(input, paramString);
                    break;
                case Delete_Seed_Confirmation:
                    output = execute_Delete_Seed_Confirmation(input, paramString);
                    break;
                case Fit_Prediction_A:
                    output = execute_Fit_Prediction_A(input, paramString);
                    break;
                case Fit_Prediction_B:
                    output = execute_Fit_Prediction_B(input, paramString);
                    break;
                case Fit_Profile:
                    output = execute_Fit_Profile(input, paramString);
                    break;
                case Fit_Profile_Cleared:
                    output = execute_Fit_Profile_Cleared(input, paramString);
                    break;
                case How_does_it_work:
                    output = execute_How_does_it_work(input, paramString);
                    break;
                case Manual_Seed_Saved:
                    output = execute_Manual_Seed_Saved(input, paramString);
                    break;
                case Privacy_Policy:
                    output = execute_Privacy_Policy(input, paramString);
                    break;
                case What_is_my_Fit_Profile:
                    output = execute_What_is_my_Fit_Profile(input, paramString);
                    break;
                default:
                    output = "{\"Error\":\"Undefined state\"}";
            }
        } catch(Exception e) {
            output = "{\"Error\":" + e.getMessage() + "}";
        }
    
        return output;
    }


    /**
     * Helper to validate the input is supported by the current state
     * @param input The input received from the app
     * @throws InputNotSupportedException If the input is not supported
     */
    private void assertInputValid(Input input) throws InputNotSupportedException {
        if (validStateInputs.containsKey(getState())) {
            if (Arrays.asList(validStateInputs.get(getState())).contains(input)) {
                // Input is valid
                return;
            }
        }
        throw new InputNotSupportedException(getState(), input);
    }


    /**
     * Retrieves whether the user has seeds.
     * @param paramString The parameters received from the app
     * @return true of the user has seeds, false otherwise
     */
    private boolean userHasSeeds(String paramString) {
        // FIXME: Implement

        return Math.random() * 100 > 50;
    }


    /**
     * Helper to get the corresponding screen ID for a given state
     * @param state The state
     * @throws ScreenForStateNotFound If no screen is found for the state
     */
    private int getScreenForState(State state) throws ScreenForStateNotFound {
        if (stateScreen.containsKey(state)) {
            return stateScreen.get(state);
        }
        throw new ScreenForStateNotFound(state); 
    }


    /**
     * Helper to get the corresponding response for the screen of a given state
     * @param state The state
     * @throws ResponseForStateScreenNotFound If no response is found for the state screen
     * @throws ScreenForStateNotFound If no screen is found for the state
     */
    private String getResponseForStateScreen(State state) throws ScreenForStateNotFound, ResponseForStateScreenNotFound {
        int screen = getScreenForState(state);
        if (screenResponse.containsKey(screen)) {
            return "{\n" +
                   "    \"sessionID\": \"" + getSessionID() + "\",\n" +
                   screenResponse.get(screen) +
                   "}\n";

        }
        throw new ResponseForStateScreenNotFound(screen); 
    }


    /**
     * Determine next state and screen output. Sets the next state and returns the output.
     * @param input The input received from the app
     * @param paramString The parameters received from the app
     * @throws Exception Forwards any kind of exception to the caller
     */
    private String execute_Init(Input input, String paramString) throws Exception {
        assertInputValid(input);
        State nextState = State.Undefined;
        
        if (userHasSeeds(paramString)) {
            nextState = State.Fit_Profile;
        }
        else {
            nextState = State.Calculator_A;
        }

        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Calculating_Prediction_A(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.ready == input) {
            nextState = State.Fit_Prediction_A;
        }

        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Calculating_Prediction_B(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.ready == input) {
            nextState = State.Fit_Prediction_B;
        }

        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Calculator_A(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.size_selected == input) {
            nextState = State.Calculating_Prediction_A;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Calculator_B(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.back) {
            nextState = State.Fit_Profile;
        }
        else if (input == Input.size_selected) {
            nextState = State.Calculating_Prediction_A;
        }     
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Calculator_C(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.size_selected == input) {
            nextState = State.Calculating_Prediction_A;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Clearing_Fit_Profile(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.ready == input) {
            nextState = State.Fit_Profile_Cleared;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Delete_Seed_Confirmation(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (Input.approved == input) {
            if (userHasSeeds(paramString)) {
                nextState = State.Calculating_Prediction_B;
            }
            else {
                nextState = State.Clearing_Fit_Profile;
            }
        }
        else if (Input.declined == input) {
            nextState = State.Fit_Profile;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Fit_Prediction_A(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.next) {
            nextState = State.Manual_Seed_Saved;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Fit_Prediction_B(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.done) {
            nextState = State.Fit_Profile;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Fit_Profile(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.remove_seed) {
            nextState = State.Delete_Seed_Confirmation;
        }
        else if (input == Input.add_seed) {
            nextState = State.Calculator_B;
        }     

        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Fit_Profile_Cleared(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.done) {
            nextState = State.Calculator_C;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_How_does_it_work(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        // FIXME: Add business logic
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Manual_Seed_Saved(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        if (input == Input.done) {
            nextState = State.Fit_Profile;
        }
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_Privacy_Policy(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        // FIXME: Add business logic
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

    private String execute_What_is_my_Fit_Profile(Input input, String paramString) throws InputNotSupportedException, ScreenForStateNotFound, ResponseForStateScreenNotFound {
        assertInputValid(input);
        State nextState = State.Undefined;

        // FIXME: Add business logic
        
        setState(nextState, input);
        return getResponseForStateScreen(nextState);
    }

}
