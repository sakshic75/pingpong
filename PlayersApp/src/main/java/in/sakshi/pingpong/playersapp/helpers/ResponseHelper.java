package in.sakshi.pingpong.playersapp.helpers;

import in.sakshi.pingpong.playersapp.config.ConfigStore;
import in.sakshi.pingpong.playersapp.config.Constants;
import org.json.JSONObject;

public class ResponseHelper {
    public static String respondWithNextMove(final int move){
        JSONObject object = new JSONObject();
        object.put(ConfigStore.loadPreferences(Constants.KEY_CHANCE_MOVE_VALUE),Integer.toString(move));
        return object.toString();
    }
    public static String respondWithIsPresent(final boolean status){
        JSONObject object = new JSONObject();
        object.put(ConfigStore.loadPreferences(Constants.KEY_FOUND_VALUE),Boolean.toString(status));
        return object.toString();
    }
    public static String respondWithSuccess(final String status,final String message){
        JSONObject object = new JSONObject();
        object.put(ConfigStore.loadPreferences(Constants.RESPONSE_STATUS),status);
        object.put(ConfigStore.loadPreferences(Constants.RESPONSE_MESSAGE),message);
        return object.toString();
    }


}
