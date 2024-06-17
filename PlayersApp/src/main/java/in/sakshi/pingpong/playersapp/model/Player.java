package in.sakshi.pingpong.playersapp.model;

import in.sakshi.pingpong.playersapp.utils.RandomGenerator;

import java.util.ArrayList;
import java.util.UUID;

public class Player {
    private String name;
    private RandomGenerator randomGenerator;
    private final UUID playerId;
    private UUID gameId;
    private UUID opponentId;
    private int score;
    private int defenseArrayLength;
    private String urlOrIpWithoutHttp;
    private int portUsed;
    private ArrayList<Integer> defenseArray;
    private String opponentName;
    private Player(final UUID playerId){
        this.playerId = playerId;
        setScore(0);
        defenseArray = new ArrayList<>();
    }
    public Player(final UUID playerId,final String name,final int arrayLength){
        this(playerId);;
        setDefenseArrayLength(arrayLength);
        randomGenerator = new RandomGenerator(1,10);
        this.name = name;
        createDefenseArray(arrayLength, randomGenerator);
    }

    private void createDefenseArray(final int arrayLength, RandomGenerator randomGenerator) {
        for(int i=0;i<arrayLength;++i){
            defenseArray.add(randomGenerator.getRandomNumber());
        }
    }
    public boolean contains(int number){
        return defenseArray.contains(number);
    }

    public UUID getPlayerId(){
        return this.playerId;
    }
    public UUID getGameId(){
        return this.gameId;
    }
    public void setGameId(final UUID gameId){
        this.gameId = gameId;
    }
    public int getScore(){
        return this.score;
    }
    public UUID getOpponentId(){
        return this.opponentId;
    }
    public void setOpponentId(final UUID opponentId){
        this.opponentId = opponentId;
    }
    public void setScore(final int score){
        this.score = score;
    }
    public void updateScore(final int newScore){
        setScore(this.getScore()+newScore);
    }
    public int getDefenseArrayLength(){
        return this.defenseArrayLength;
    }
    public void setDefenseArrayLength(final int defenseArrayLength){
        this.defenseArrayLength = defenseArrayLength;
    }
    private RandomGenerator getRandomGenerator(){
        return this.randomGenerator;
    }
    public int nextMove(){
        return getRandomGenerator().getRandomNumber();
    }
    public void setPortUsed(final int port){
        this.portUsed = port;
    }
    public int getPortUsed() {
        return portUsed;
    }
    public String getName(){ return this.name;}
    public String getUrlOrIpWithoutHttp(){
        return this.urlOrIpWithoutHttp;
    }
    public void setUrlOrIpWithoutHttp(String address){
        this.urlOrIpWithoutHttp = address;
    }
    public String getOpponentName(){
        return this.opponentName;
    }
    public void setOpponentName(String name){
        this.opponentName = name;
    }
}
