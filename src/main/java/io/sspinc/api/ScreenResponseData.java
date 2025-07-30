package io.sspinc.api;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ScreenResponseData {
    public String sessionID;
    public Data data;

    public static class Data {
        public ScreenResponse screenResponse;
    }

    public static class ScreenResponse {
        public String screenId;
    }

    public static ScreenResponseData deserializeScreenResponse(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, ScreenResponseData.class);
    }

}
