package in.sakshi.pingpong.refereeapp.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import in.sakshi.pingpong.refereeapp.models.Chance;
import org.json.JSONObject;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.models.Player;
import in.sakshi.pingpong.refereeapp.server.JoinRequestHandler;
import in.sakshi.pingpong.refereeapp.server.RefereeServer;
import in.sakshi.pingpong.refereeapp.service.RefereeServiceImpl;
import in.sakshi.pingpong.refereeapp.utils.events.EventListener;
import in.sakshi.pingpong.refereeapp.utils.parser.JsonParser;

public class MainController implements EventListener {
    private final RefereeServer server;
    private final RefereeServiceImpl service;
    private final JoinRequestHandler joinRequestHandler;
    private final JsonParser jsonParser;
    private final Queue<Player> players;
    private final ScoreboardController scoreboardController;
    private final int playerCount;
    private boolean gameStarted;
    public MainController(String url,int port,int playerCount){
        players = new LinkedList<>();
        server = new RefereeServer();
        service = new RefereeServiceImpl();
        server.init(url, port);
        joinRequestHandler = new JoinRequestHandler();
        joinRequestHandler.addEventListener(this);
        server.addContext(ConfigStore.loadPreference(Constants.JOIN_GAME_REQUEST_URI),joinRequestHandler);
        this.gameStarted = false;
        this.playerCount = playerCount;
        jsonParser = new JsonParser();
        scoreboardController = new ScoreboardController();

    }
    private void setGameStarted(boolean state){
        this.gameStarted = state;
    }
    public boolean getGameStarted(){
        return this.gameStarted;
    }
    public int getPlayerCount(){
        return playerCount;
    }
    public void init(){
        server.start();
    }
    public void suspend(){
        joinRequestHandler.removeEventListener(this);
        server.close();
        System.exit(0);
    }

    @Override
    public void update(String eventType, InputStream data) {
        switch(eventType){
            case Constants.JOIN_GAME_REQUEST_ID:
                try {
                    System.out.println("Request Received!");
                    String playerData = new String(data.readAllBytes());
                    JSONObject jsonObject = new JSONObject(playerData);
                    Player player = jsonParser.parse(jsonObject);
                    if(players.size()==playerCount-1){
                        System.out.println("All players are in!");
                        players.add(player);
                        if(!getGameStarted()){
                            playGame();
                            setGameStarted(true);
                        }
                    }else if(players.size()<playerCount) {
                        System.out.println("Some player are remaining!");
                        System.out.println(player);
                        players.add(player);
                    }
                    joinRequestHandler.setResponseBody(ResponseHelper.joinGameResponse("SUCCESS",
                            String.format("Player %s (%s) has joined the Room!",player.getName(),player.getPlayerId().toString())));
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
                break;

            default:
                break;
        }
    }

    public void playGame(){
        try {
            System.out.println("Game Begun!");
            System.out.println(players.size());
            while (players.size()>1) {
                System.out.println("Inside While Loop");
                Player defender = players.poll();
                Player opponent = players.poll();
                GameController gameController = new GameController(UUID.randomUUID(), opponent, defender, service,
                        Integer.parseInt(ConfigStore.loadPreference(Constants.SENTINEL_SCORE)));
                System.out.println("After Constructing!");
                GameController.Scorecard scorecard = gameController.playGame();
                scoreboardController.addScorecard(scorecard);
                players.add(scorecard.getWinner());

             }
            Player champion = players.poll();
            scoreboardController.setChampionId(champion.getPlayerId().toString());
            scoreboardController.setChampionName(champion.getName());
            scoreboardController.setChampionScore(champion.getPlayerScore());
            scoreboardController.saveScoreboard(String.format(ConfigStore.loadPreference(Constants.GAME_REPORT_FILENAME), LocalDateTime.now().toString()));
        }catch(IOException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            System.out.println("IOException:"+e.getMessage());
        }catch(ExecutionException e){
            System.out.println("ExecutionException:"+e.getMessage());
        }catch(TimeoutException e){
            System.out.println("TimeoutException: "+e.getMessage());
        }

    }

}
