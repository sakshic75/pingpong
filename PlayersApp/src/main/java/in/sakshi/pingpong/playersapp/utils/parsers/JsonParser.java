package in.sakshi.pingpong.playersapp.utils.parsers;

import in.sakshi.pingpong.playersapp.config.ConfigStore;
import in.sakshi.pingpong.playersapp.config.Constants;
import in.sakshi.pingpong.playersapp.model.Player;
import org.json.JSONObject;

import java.util.Optional;
import java.util.UUID;

public class JsonParser implements Parser<JSONObject, Player> {

   public JsonParser(){}
    @Override
    public JSONObject parse(Player object) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_NAME),object.getName());
        jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_ID),object.getPlayerId().toString());
        if(object.getGameId()!=null) {
            jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_GAME_ID), object.getGameId().toString());
        }
        if(object.getOpponentId()!=null) {
            jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_OPPONENT_ID), object.getOpponentId().toString());
        }
        jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_SCORE),object.getScore());
        jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_IP),object.getUrlOrIpWithoutHttp());
        jsonObject.put(ConfigStore.loadPreferences(Constants.KEY_PLAYER_PORT),object.getPortUsed());
        return jsonObject;
   }

    @Override
    public Player parse(JSONObject object) {
        return null;
    }
}
