package in.sakshi.pingpong.refereeapp.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import org.json.JSONObject;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.models.Chance;
import in.sakshi.pingpong.refereeapp.models.HttpVerb;
import in.sakshi.pingpong.refereeapp.models.Player;
import in.sakshi.pingpong.refereeapp.service.RefereeService;

public class GameController {
  private final Player offensive;
  private final Player defender;
  private final RefereeService<String, String> refereeService;
  private final int sentinelScore;
  private final UUID gameId;
  public GameController(final UUID gameId,final Player opponent,final Player defender,
                        RefereeService<String,String> service,
                        final int sentinelScore){
      this.gameId = gameId;
      this.offensive = opponent;
      this.defender = defender;
      this.refereeService = service;
      this.sentinelScore = sentinelScore;
  }
    public Scorecard playGame() throws IOException, InterruptedException {
        //Scorecard scorecard = new Scorecard(null,null,null);
        String gameMessage1 = sendGameNotificationRequest(offensive);
        System.out.println(gameMessage1);
        String gameMessage2 = sendGameNotificationRequest(defender);
        System.out.println(gameMessage2);
        String message1 = sendOpponentNotificationRequest(defender, offensive);
        System.out.println(message1);
        String message2 = sendOpponentNotificationRequest(offensive, defender);
        System.out.println(message2);
        boolean toggled = true;
        while (defender.getPlayerScore() < sentinelScore || offensive.getPlayerScore() < sentinelScore) {
            if (toggled) {
                System.out.println("Inside toggle Condition!");
                String data = sendChanceNotificationRequest(offensive, Chance.FIRST, 0);
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.has(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE))) {
                    boolean foundValue = true;
                    do {
                        int move = jsonObject.getInt(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE));
                        String foundData = sendChanceNotificationRequest(defender, Chance.SECOND, move);
                        JSONObject jsonObject1 = new JSONObject(foundData);
                        if (jsonObject1.has(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE))) {
                            foundValue = jsonObject1.getBoolean(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE));
                            if (foundValue) {
                                offensive.setPlayerScore(offensive.getPlayerScore() + 1);
                            }
                        } else {
                            System.out.println("Unable to Fetch the Found Value");
                        }
                    } while (!foundValue);
                    toggled = !toggled;
                } else {
                    System.out.println("Chance not fetched from the offensive player!");
                }
            } else {
                String data = sendChanceNotificationRequest(defender, Chance.FIRST, 0);
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.has(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE))) {
                    boolean foundValue = true;
                    do {
                        int move = jsonObject.getInt(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE));
                        String data1 = sendChanceNotificationRequest(offensive, Chance.SECOND, move);
                        JSONObject jsonObject1 = new JSONObject(data1);
                        if (jsonObject1.has(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE))) {
                            foundValue = jsonObject1.getBoolean(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE));
                            if (foundValue) {
                                defender.setPlayerScore(defender.getPlayerScore() + 1);
                            }
                        } else {
                            System.out.println("Unable to fetch Found Value from Defender!");

                        }
                    } while (!foundValue);
                    toggled = !toggled;
                } else {
                    System.out.println("Chance not fetched from the defensive player!");
                }
            }
        }
        return new Scorecard();
    }
    public String sendOpponentNotificationRequest(Player defender, Player opponent) throws IOException, InterruptedException {
    	try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_ID_REQUEST),opponent.getPlayerId().toString());
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_NAME_REQUEST),opponent.getName());
        return refereeService.serve("http://"+defender.getPlayerIp()+":"+defender.getPlayerPort()+ConfigStore.loadPreference(Constants.OPPONENT_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.PUT);
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	return "";
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
    public static class Scorecard implements Serializable {
      private final UUID gameId;
      private final Player winner;
      private final Player looser;
      public Scorecard(UUID gameId, Player winner, Player looser){
          this.gameId = gameId;
          this.winner = winner;
          this.looser = looser;
      }
      public Scorecard(){
          gameId = UUID.randomUUID();
          winner = null;
          looser = null;
      }
      public final Player getWinner(){
          return this.winner;
      }
      public final Player getLooser(){
          return this.looser;
      }

        public UUID getGameId() {
            return gameId;
        }
    }

}
