package io.sspinc.api;

import io.sspinc.api.FSM.Input;

public class BusinessLogicTest {

    /**
     * Main -- For testing purpose
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        BusinessLogic businessLogic = new BusinessLogic();

        String response = businessLogic.processInput(Input.open_fp, null, "null")   ;
        System.out.println(response);

        ScreenResponseData responseObj = ScreenResponseData.deserializeScreenResponse(response);
        String sessionID = responseObj.sessionID;
        
        if (responseObj.data.screenResponse.screenId.equals("screen_5")) {
            if (Math.random() * 100 > 50) {
                response = businessLogic.processInput(Input.add_seed, sessionID, "null");
            }
            else {
                response = businessLogic.processInput(Input.remove_seed, sessionID, "null");
            }
            System.out.println(response);
        }

        for (int i=0; i < 10000; i++)
        {
            Input input = FSM.getRandomInput();
            response = businessLogic.processInput(input, sessionID, "null");
            System.out.println(response);
        }

    }
}
