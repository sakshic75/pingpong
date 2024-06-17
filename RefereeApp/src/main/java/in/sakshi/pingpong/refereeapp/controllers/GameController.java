package in.sakshi.pingpong.refereeapp.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
    public Scorecard playGame() throws IOException, InterruptedException, ExecutionException, TimeoutException {
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
        Player winner = null;
        Player looser = null;
        while (defender.getPlayerScore() != sentinelScore || offensive.getPlayerScore() != sentinelScore) {
            if (toggled) {
                String data = sendChanceNotificationRequest(offensive, Chance.FIRST, 0);
                JSONObject jsonObject = new JSONObject(data);
                // {
                //  "chanceValue": someNumber
                // ]
                if(jsonObject.has(ConfigStore.loadPreference(Constants.KEY_CHANCE_MOVE_VALUE))) {
                    boolean foundValue = false;
                    do {
                        int move = jsonObject.getInt(ConfigStore.loadPreference(Constants.KEY_CHANCE_MOVE_VALUE));
                        String foundData = sendChanceNotificationRequest(defender, Chance.SECOND, move);
                        JSONObject jsonObject1 = new JSONObject(foundData);
                        if (jsonObject1.has(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE))) {
                            foundValue = jsonObject1.getBoolean(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE));
                            if (!foundValue) {
                                if(offensive.getPlayerScore()==sentinelScore){
                                    return new Scorecard(gameId,offensive,defender);
                                }
                                offensive.setPlayerScore(offensive.getPlayerScore() + 1);
                                System.out.println("Offensive Score:"+offensive.getPlayerScore());
                                System.out.println("Defensive Score:"+defender.getPlayerScore());

                            }else{
                                if(defender.getPlayerScore()==sentinelScore){
                                    return new Scorecard(gameId,defender,offensive);
                                }
                                defender.setPlayerScore(defender.getPlayerScore()+1);
                                System.out.println("Offensive Score:"+offensive.getPlayerScore());
                                System.out.println("Defensive Score:"+defender.getPlayerScore());
                                toggled = !toggled;
                                break;
                            }
                        } else {
                            System.out.println("Unable to Fetch the Found Value");
                        }
                    } while (foundValue);
                    if(offensive.getPlayerScore()==sentinelScore){
                        return new Scorecard(gameId,offensive,defender);
                    }else if(defender.getPlayerScore()==sentinelScore){
                        return new Scorecard(gameId,defender,offensive);
                    }

                } else {
                    System.out.println("Chance not fetched from the offensive player!");
                }
            } else {
                String data = sendChanceNotificationRequest(defender, Chance.FIRST, 0);
                JSONObject jsonObject = new JSONObject(data);
                if (jsonObject.has(ConfigStore.loadPreference(Constants.KEY_CHANCE_MOVE_VALUE))) {
                    boolean foundValue = false;
                    do {
                        int move = jsonObject.getInt(ConfigStore.loadPreference(Constants.KEY_CHANCE_MOVE_VALUE));
                        String data1 = sendChanceNotificationRequest(offensive, Chance.SECOND, move);
                        JSONObject jsonObject1 = new JSONObject(data1);
                        if (jsonObject1.has(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE))) {
                            foundValue = jsonObject1.getBoolean(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE));
                            if (!foundValue) {
                                if(defender.getPlayerScore()==sentinelScore){
                                    return new Scorecard(gameId,defender,offensive);
                                }
                                defender.setPlayerScore(defender.getPlayerScore() + 1);

                            }else{
                                if(offensive.getPlayerScore()==sentinelScore){
                                    return new Scorecard(gameId,offensive,defender);
                                }
                                offensive.setPlayerScore(offensive.getPlayerScore()+1);
                                toggled = !toggled;
                                break;
                            }
                        } else {
                            System.out.println("Unable to fetch Found Value from Defender!");

                        }
                    } while (foundValue);
                    if(defender.getPlayerScore()==sentinelScore){
                        return new Scorecard(gameId,defender,offensive);
                    }else if(offensive.getPlayerScore()==sentinelScore){
                        return new Scorecard(gameId,offensive,defender);
                    }
                } else {
                    System.out.println("Chance not fetched from the defensive player!");
                }
            }
        }
        return new Scorecard(gameId,null,null);
    }
    public String sendOpponentNotificationRequest(Player defender, Player opponent) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_ID_REQUEST),opponent.getPlayerId().toString());
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_OPPONENT_NAME_REQUEST), opponent.getName());
        return refereeService.serve("http://"+defender.getPlayerIp()+":"+defender.getPlayerPort()+ConfigStore.loadPreference(Constants.OPPONENT_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.POST);
  }
  public String sendScoreUpdateRequest(Player player) throws IOException, InterruptedException, ExecutionException, TimeoutException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_ID),player.getPlayerId());
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_SCORE),player.getPlayerScore());
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.SCORE_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.POST);
  }
  public String sendChanceNotificationRequest(Player player,Chance chance,int value) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(ConfigStore.loadPreference(Constants.KEY_PLAYER_CHANCE),chance.name());
        if(chance==Chance.SECOND){
            jsonObject.put(ConfigStore.loadPreference(Constants.KEY_FOUND_VALUE),value);
        }
        return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.PLAYER_CHANCE_REQUEST_URI),jsonObject.toString(),HttpVerb.POST);
  }

  public String sendExitNotificationRequest(Player player) throws InterruptedException, ExecutionException, TimeoutException, IOException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_EXIT_REQUEST),true);
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.EXIT_REQUEST_HANDLER_URI),jsonObject.toString(),HttpVerb.POST);
  }
  public String sendGameNotificationRequest(Player player) throws InterruptedException, ExecutionException, TimeoutException, IOException {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put(ConfigStore.loadPreference(Constants.KEY_GAME_ID),gameId.toString());
      return refereeService.serve("http://"+player.getPlayerIp()+":"+player.getPlayerPort()+ConfigStore.loadPreference(Constants.GAME_NOTIFICATION_RESPONSE_URI),jsonObject.toString(),HttpVerb.POST);
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
