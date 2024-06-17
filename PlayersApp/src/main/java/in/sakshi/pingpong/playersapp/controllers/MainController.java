package in.sakshi.pingpong.playersapp.controllers;


import in.sakshi.pingpong.playersapp.config.ConfigStore;
import in.sakshi.pingpong.playersapp.config.Constants;
import in.sakshi.pingpong.playersapp.helpers.ResponseHelper;
import in.sakshi.pingpong.playersapp.model.Chance;
import in.sakshi.pingpong.playersapp.model.HttpVerb;
import in.sakshi.pingpong.playersapp.model.Player;
import in.sakshi.pingpong.playersapp.server.*;
import in.sakshi.pingpong.playersapp.service.PlayerService;
import in.sakshi.pingpong.playersapp.service.PlayerServiceImpl;
import in.sakshi.pingpong.playersapp.utils.events.EventListener;
import in.sakshi.pingpong.playersapp.utils.parsers.JsonParser;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainController implements EventListener<InputStream> {
    private final String SOCKET;
    private final PlayerService<String, String> playerService;
    private final PlayersServer playersServer;
    private final ChanceNotificationHandler chanceNotificationHandler;
    private final ExitGameRequestHandler exitGameRequestHandler;
    private final OpponentNotificationHandler opponentNotificationHandler;
    private final ScoreUpdateRequestHandler scoreUpdateRequestHandler;
    private final GameNotificationHandler gameNotificationHandler;
    private final JsonParser parser;
    private final Player player;

    public MainController(final String playerName, final String serviceUrl, final int servicePort, final int defenseArrayLength) {
        SOCKET = serviceUrl + ":" + servicePort;
        player = new Player(UUID.randomUUID(), playerName, defenseArrayLength);
        playerService = new PlayerServiceImpl();
        playersServer = new PlayersServer();
        chanceNotificationHandler = new ChanceNotificationHandler();
        exitGameRequestHandler = new ExitGameRequestHandler();
        opponentNotificationHandler = new OpponentNotificationHandler();
        scoreUpdateRequestHandler = new ScoreUpdateRequestHandler();
        gameNotificationHandler = new GameNotificationHandler();
        parser = new JsonParser();
    }

    public void initServer(final String serverUrl, final int serverPort) {
        chanceNotificationHandler.addListener(this);
        exitGameRequestHandler.addListener(this);
        opponentNotificationHandler.addListener(this);
        scoreUpdateRequestHandler.addListener(this);
        gameNotificationHandler.addListener(this);
        playersServer.init(serverUrl, serverPort);
        playersServer.addContext(ConfigStore.loadPreferences(Constants.PLAYER_CHANCE_RESPONSE_URI), chanceNotificationHandler);
        playersServer.addContext(ConfigStore.loadPreferences(Constants.EXIT_REQUEST_HANDLER_URI), exitGameRequestHandler);
        playersServer.addContext(ConfigStore.loadPreferences(Constants.OPPONENT_REQUEST_HANDLER_URI), opponentNotificationHandler);
        playersServer.addContext(ConfigStore.loadPreferences(Constants.SCORE_REQUEST_HANDLER_URI),scoreUpdateRequestHandler);
        playersServer.addContext(ConfigStore.loadPreferences(Constants.GAME_NOTIFICATION_RESPONSE_URI),gameNotificationHandler);
        playersServer.connect();
        player.setUrlOrIpWithoutHttp(serverUrl);
        player.setPortUsed(serverPort);
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome Player, " + player.getName() + " on http://" + player.getUrlOrIpWithoutHttp() + ":" + player.getPortUsed());
    }

    public void initServices() {
        String playerJsonString = parser.parse(player).toString();
        sendJoinGameRequest(playerJsonString);
    }

    public void sendJoinGameRequest(String data) {
        try {
            String responseBody = playerService.serve(SOCKET + ConfigStore.loadPreferences(Constants.PLAYER_JOIN_REQUEST_URI), data, HttpVerb.POST);
            if (!responseBody.isEmpty()) {
                JSONObject jsonObject = new JSONObject(responseBody);
                if(jsonObject.has(ConfigStore.loadPreferences(Constants.RESPONSE_STATUS)) &&
                jsonObject.has(ConfigStore.loadPreferences(Constants.RESPONSE_MESSAGE))){
                    String status = jsonObject.getString(ConfigStore.loadPreferences(Constants.RESPONSE_STATUS));
                    String message = jsonObject.getString(ConfigStore.loadPreferences(Constants.RESPONSE_MESSAGE));
                    System.out.println("Joining Request State: "+status);
                    System.out.println(message);
                }
            } else {
                System.out.println("Join Game Request Failed!");
            }
        } catch (InterruptedException e) {
            System.out.println("Join Request Failed: Interruption Occurred!");
        } catch (IOException e) {
            System.out.println("Join Request Failed: IOException Occurred!");
            System.out.println(e.getCause().toString());
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(String eventType, InputStream inputStream) {
        String requestBody = null;
        JSONObject jsonObject = null;
        try {
            requestBody = new String(inputStream.readAllBytes());
            jsonObject = new JSONObject(requestBody);
        } catch (IOException e) {
            System.out.println(e.getCause().toString());
        }
        switch (eventType) {
            case Constants.CHANCE_NOTIFICATION_SUBJECT:

                Chance chance;
                if (jsonObject != null && jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_PLAYER_CHANCE))) {
                    chance = Chance.valueOf(jsonObject.getString(ConfigStore.loadPreferences(Constants.KEY_PLAYER_CHANCE)));
                    if (chance == Chance.FIRST) {
                        int move = player.nextMove();
                        System.out.printf("Opponent %s has chosen the move as %d\n",player.getName(),move);
                        chanceNotificationHandler.setResponseBody(ResponseHelper.respondWithNextMove(move));
                    } else if (chance == Chance.SECOND) {
                        int number = Integer.parseInt(jsonObject.getString(ConfigStore.loadPreferences(Constants.KEY_FOUND_VALUE)));
                        boolean found = player.contains(number);
                        System.out.printf("Defender %s checked the opponent move %d and %s it.\n",player.getName(),number,(found?"found":"not found"));
                        chanceNotificationHandler.setResponseBody(ResponseHelper.respondWithIsPresent(player.contains(number)));
                    }
                } else {
                    System.out.println("Corrupted Data for Player Move Request");
                }
                break;
            case Constants.EXIT_REQUEST_HANDLER_SUBJECT:
                if (jsonObject != null && jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_EXIT_REQUEST))) {
                    boolean exit = jsonObject.getBoolean(ConfigStore.loadPreferences(Constants.KEY_EXIT_REQUEST));
                    if (exit) {
                        exitGameRequestHandler.setResponseBody(ResponseHelper.respondWithSuccess("SUCCESS", player.getName() + " left the game!"));
                    }
                } else {
                    System.out.println("Corrupted Data For Player Exit Request!");
                }
                break;
            case Constants.OPPONENT_REQUEST_HANDLER_SUBJECT:
                if (jsonObject != null && (jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_OPPONENT_ID_REQUEST))
                        && jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_OPPONENT_NAME_REQUEST)))) {
                    player.setOpponentId(UUID.fromString(jsonObject.getString(ConfigStore.loadPreferences(Constants.KEY_OPPONENT_ID_REQUEST))));
                    player.setOpponentName(jsonObject.getString(ConfigStore.loadPreferences(Constants.KEY_OPPONENT_NAME_REQUEST)));
                    System.out.printf("Player %s, has the got the Opponent %s(%s)\n", player.getName(), player.getOpponentName(), player.getOpponentId().toString());
                    opponentNotificationHandler.setResponseBody(ResponseHelper.respondWithSuccess("SUCCESS", "Opponent " + player.getOpponentName() + " is assigned to Player " + player.getName()));
                } else {
                    System.out.println("Corrupted Data For Player's Opponent Assignment!");
                }
                break;
            case Constants.SCORE_REQUEST_HANDLER_SUBJECT:
                if (jsonObject != null && jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_PLAYER_ID)) &&
                        jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_PLAYER_SCORE))) {
                    var playerId = jsonObject.get(ConfigStore.loadPreferences(Constants.KEY_PLAYER_ID));
                    var playerScore = jsonObject.getInt(ConfigStore.loadPreferences(Constants.KEY_PLAYER_SCORE));
                    if (player.getPlayerId().equals(playerId)) {
                        player.setScore(playerScore);
                        scoreUpdateRequestHandler.setResponseBody(ResponseHelper.respondWithSuccess("SUCCESS", "Player " + player.getName() + " has scored " + player.getScore() + " points.z"));
                        System.out.printf("Player %s has the score %d points\n", player.getName(), player.getScore());
                    } else {
                        System.out.println("Player ID doesn't match with current Player!");
                    }

                } else {
                    System.out.println("Corrupted Data For Player's Score Update!");
                }
                break;

            case Constants.GAME_NOTIFICATION_SUBJECT:
                if(jsonObject!=null && jsonObject.has(ConfigStore.loadPreferences(Constants.KEY_GAME_ID))){
                    player.setGameId(UUID.fromString(jsonObject.getString(ConfigStore.loadPreferences(Constants.KEY_GAME_ID))));
                    System.out.printf("Player %s has joined the Game %s.\n",player.getName(),player.getGameId().toString());
                    gameNotificationHandler.setResponseBody(ResponseHelper.respondWithSuccess("SUCCESS",String.format("Player %s has joined the Game %s.\n",player.getName(),player.getGameId().toString())));
                }else{
                    System.out.println("Corrupted Data for Player's Game ID");
                }

        }
    }

}
