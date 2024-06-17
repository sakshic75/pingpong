package in.sakshi.pingpong.refereeapp.controllers;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.models.Chance;
import in.sakshi.pingpong.refereeapp.models.HttpVerb;
import in.sakshi.pingpong.refereeapp.models.Player;
import in.sakshi.pingpong.refereeapp.service.RefereeService;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GameController {
  private final Player opponent;
  private final Player defender;
  private final RefereeService<String, String> refereeService;
  private final int sentinelScore;
  private final UUID gameId;
  public GameController(final UUID gameId,final Player opponent,final Player defender,
                        RefereeService<String,String> service,
                        final int sentinelScore){
      this.gameId = gameId;
      this.opponent = opponent;
      this.defender = defender;
      this.refereeService = service;
      this.sentinelScore = sentinelScore;
  }
  public Scorecard playGame(){
    Scorecard scorecard;

    scorecard = new Scorecard(gameId,defender,opponent);
    return scorecard;
  }
  public String sendOpponentNotificationRequest(Player defender, Player opponent) throws IOException, InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_ID_REQUEST),opponent.getPlayerId().toString());
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_NAME_REQUEST),opponent.getName());
        return refereeService.serve("http://"+defender.getPlayerIp()+":"+defender.getPlayerPort()+ConfigStore.loadPreference(Constants.OPPONENT_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.PUT);
  }
  public String sendScoreUpdateRequest(Player player) throws IOException, InterruptedException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_ID),player.getPlayerId());
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_SCORE),player.getPlayerScore());
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.SCORE_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.PUT);
  }
  public String sendChanceNotificationRequest(Player player,Chance chance,int value) throws IOException, InterruptedException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE),chance.name());
        if(chance==Chance.SECOND){
            jsonObject.put(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE),value);
        }
        return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.PLAYER_CHANCE_REQUEST_URI),jsonObject.toString(),HttpVerb.POST);
  }

  public String sendExitNotificationRequest(Player player) throws IOException, InterruptedException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_EXIT_REQUEST),true);
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.EXIT_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.POST);
  }
  public String sendGameNotificationRequest(Player player) throws IOException, InterruptedException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_GAME_ID),gameId.toString());
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.GAME_NOTIFICATION_RESPONSE_URI),jsonObject.toString(),HttpVerb.PUT);
  }
    public static class Scorecard{
      private final UUID gameId;
      private final Player winner;
      private final Player looser;
      public Scorecard(UUID gameId, Player winner, Player looser){
          this.gameId = gameId;
          this.winner = winner;
          this.looser = looser;
      }
      public final Player getWinner(){
          return this.winner;
      }
      public final Player getLooser(){
          return this.looser;
      }

    }

}
