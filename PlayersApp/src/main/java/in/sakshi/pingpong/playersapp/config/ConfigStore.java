package in.sakshi.pingpong.playersapp.config;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ConfigStore {
    private static final String PREF="pingpongapp";
    private static final Preferences prefs = Preferences.userRoot().node(PREF);
    public static boolean storePreferences(){
        try{
            prefs.put(Constants.KEY_GAME_ID,"gameid");
            prefs.put(Constants.KEY_RESPONSE_ID,"responseid");
            prefs.put(Constants.KEY_PLAYER_ID,"playerId");
            prefs.put(Constants.KEY_PLAYER_GAME_ID,"playerGameId");
            prefs.put(Constants.KEY_PLAYER_OPPONENT_ID,"playerOpoonentId");
            prefs.put(Constants.KEY_PLAYER_SCORE,"playerScore");
            prefs.put(Constants.KEY_PLAYER_PORT,"playerPort");
            prefs.put(Constants.PLAYER_JOIN_REQUEST_URI,"/joingame/");
            prefs.put(Constants.PLAYER_CHANCE_RESPONSE_URI,"/chanceUrl/");
            prefs.put(Constants.KEY_CHANCE_MOVE_VALUE,"chanceMoveValue");
            prefs.put(Constants.KEY_PLAYER_CHANCE,"chanceValue");
            prefs.put(Constants.KEY_PLAYER_IP, "playerIp");
            prefs.put(Constants.KEY_PLAYER_NAME,"playerName");
            prefs.put(Constants.KEY_FOUND_VALUE,"foundValue");
            prefs.put(Constants.EXIT_REQUEST_HANDLER_URI,"/exitRequest/");
            prefs.put(Constants.KEY_EXIT_REQUEST,"exitValue");
            prefs.put(Constants.OPPONENT_REQUEST_HANDLER_URI,"/opponent/");
            prefs.put(Constants.KEY_OPPONENT_ID_REQUEST,"opponentId");
            prefs.put(Constants.KEY_OPPONENT_NAME_REQUEST,"opponentName");
            prefs.put(Constants.SCORE_REQUEST_HANDLER_URI,"/score/");
            prefs.put(Constants.GAME_NOTIFICATION_RESPONSE_URI,"/game/");
            prefs.put(Constants.RESPONSE_STATUS,"responseStatus");
            prefs.put(Constants.RESPONSE_MESSAGE,"responseMessage");
            prefs.flush();
        }catch(BackingStoreException e) {
           return false;
        }
        return true;
    }

    public static String loadPreferences(String prefKey){
        return prefs.get(prefKey,Constants.DEFAULT);
    }

}
