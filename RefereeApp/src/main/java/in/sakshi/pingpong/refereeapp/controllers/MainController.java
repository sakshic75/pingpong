package in.sakshi.pingpong.refereeapp.controllers;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.models.Chance;
import in.sakshi.pingpong.refereeapp.models.Player;
import in.sakshi.pingpong.refereeapp.server.JoinRequestHandler;
import in.sakshi.pingpong.refereeapp.server.RefereeServer;
import in.sakshi.pingpong.refereeapp.service.RefereeServiceImpl;
import in.sakshi.pingpong.refereeapp.utils.events.EventListener;
import in.sakshi.pingpong.refereeapp.utils.parser.JsonParser;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class MainController implements EventListener {
    private final RefereeServer server;
    private final RefereeServiceImpl service;
    private final JoinRequestHandler joinRequestHandler;
    private final JsonParser jsonParser;
    private final Queue<Player> players;
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
        System.out.println("Game Begun!");
        Player player1 = players.poll();
        Player player2 = players.poll();
        try{
            GameController controller = new GameController(UUID.randomUUID(),player1,player2,service,5);
            String a=controller.sendChanceNotificationRequest(player1, Chance.FIRST,0);
            System.out.println("First Request Sent!");
            String b=controller.sendChanceNotificationRequest(player2,Chance.SECOND,5);
            System.out.println(a);
            System.out.println(b);
        }catch (IOException e) {
            System.out.println("Request Failed: IOException");
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Request Failed: InterruptedException");
        }
    }

}
