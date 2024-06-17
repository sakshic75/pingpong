package in.sakshi.pingpong.refereeapp.config;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class ConfigStore {
    private static final String PREF_NODE = "pingpongapp";
    private static final Preferences prefs = Preferences.userRoot().node(PREF_NODE);

    public static boolean storePreferences() {
        try {
            prefs.put(Constants.JOIN_GAME_REQUEST_URI, "/joingame/");
            prefs.put(Constants.KEY_GAME_ID, "gameid");
            prefs.put(Constants.KEY_PLAYER_NAME,"playerName");
            prefs.put(Constants.KEY_PLAYER_ID, "playerId");
            prefs.put(Constants.KEY_PLAYER_SCORE, "playerScore");
            prefs.put(Constants.KEY_PLAYER_PORT, "playerPort");
            prefs.put(Constants.PLAYER_CHANCE_REQUEST_URI, "/chanceUrl/");
            prefs.put(Constants.KEY_PLAYER_CHANCE, "chanceValue");
            prefs.put(Constants.KEY_PLAYER_IP, "playerIp");
            prefs.put(Constants.OPPONENT_REQUEST_HANDLER_URI,"/opponent/");
            prefs.put(Constants.KEY_OPPONENT_ID_REQUEST, "opponentId");
            prefs.put(Constants.KEY_OPPONENT_NAME_REQUEST,"opponentName");
            prefs.put(Constants.SCORE_REQUEST_HANDLER_URI,"/score/");
            prefs.put(Constants.GAME_NOTIFICATION_RESPONSE_URI,"/game/");
            prefs.put(Constants.KEY_EXIT_REQUEST,"exitValue");
            prefs.put(Constants.KEY_FOUND_VALUE,"foundValue");
            prefs.put(Constants.RESPONSE_STATUS,"responseStatus");
            prefs.put(Constants.RESPONSE_MESSAGE,"responseMessage");
            prefs.flush();
        }catch (BackingStoreException e) {
            System.err.println(e.getCause().toString());
            return false;
        }
        return true;


    }


    public static String loadPreference(final String prefKey) {
        return prefs.get(prefKey, Constants.DEFAULT);
    }

}
