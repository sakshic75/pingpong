package in.sakshi.pingpong.refereeapp.utils.parser;

import java.util.UUID;

import org.json.JSONObject;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.models.Player;

public class JsonParser implements Parser<JSONObject, Player> {
    @Override
    public JSONObject parse(Player data) {
        return null;
    }

    @Override
    public Player parse(JSONObject data) {
        return  new Player(data.getString(ConfigStore.loadPreference(Constants.KEY_PLAYER_NAME)),
                UUID.fromString(data.getString(ConfigStore.loadPreference(Constants.KEY_PLAYER_ID))),
                data.getInt(ConfigStore.loadPreference(Constants.KEY_PLAYER_PORT)),
                data.getInt(ConfigStore.loadPreference(Constants.KEY_PLAYER_SCORE)),
                data.getString(ConfigStore.loadPreference(Constants.KEY_PLAYER_IP)));
    }
}
