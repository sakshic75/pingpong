package in.sakshi.pingpong.refereeapp.controllers;


import org.json.JSONObject;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;

public class ResponseHelper {
   public static String joinGameResponse(String status,String message){
       JSONObject object = new JSONObject();
        final String jsonString = object.put(ConfigStore.loadPreference(Constants.RESPONSE_STATUS),status)
       .put(ConfigStore.loadPreference(Constants.RESPONSE_MESSAGE),message)
                .toString();
       return jsonString;
   }

}
