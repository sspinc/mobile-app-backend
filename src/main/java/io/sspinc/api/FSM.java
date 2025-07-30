package io.sspinc.api;

import java.util.Random;

public class FSM {

    enum State {
        Init,
        Calculating_Prediction_A,
        Calculating_Prediction_B,
        Calculator_A,
        Calculator_B,
        Calculator_C,
        Clearing_Fit_Profile,
        Delete_Seed_Confirmation,
        Fit_Prediction_A,
        Fit_Prediction_B,
        Fit_Profile_Cleared,
        Fit_Profile,
        How_does_it_work,
        Manual_Seed_Saved,
        Privacy_Policy,
        What_is_my_Fit_Profile,
        Undefined
    }

    public static State getRandomState() {
        State[] states = State.values();
        int idx = new Random().nextInt(states.length);
        return states[idx];
    }


    enum Input {
        approved,
        add_seed,
        back,
        brand_selected,
        category_selected,
        declined,
        done,
        got_it,
        how_it_works,
        next,
        open_fp,
        privacy_policy,
        ready,
        remove_seed,
        size_selected,
        size_type_selected,
        what_is_my_fit_profile,
        undefined
    }

    public static Input getRandomInput() {
        Input[] inputs = Input.values();
        int idx = new Random().nextInt(inputs.length);
        return inputs[idx];
    }
}
