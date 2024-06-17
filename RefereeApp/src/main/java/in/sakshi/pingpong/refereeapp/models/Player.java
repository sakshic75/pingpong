package in.sakshi.pingpong.refereeapp.models;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
        private String name;
        private UUID playerId;
        private int playerPort;
        private int playerScore;
        private String playerIp;
    public Player(String name,UUID playerId, int playerPort, int playerScore, String playerIp) {
        this.name = name;
        this.playerId = playerId;
        this.playerPort = playerPort;
        this.playerScore = playerScore;
        this.playerIp = playerIp;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public int getPlayerPort() {
        return playerPort;
    }

    public void setPlayerPort(int playerPort) {
        this.playerPort = playerPort;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", playerId=" + playerId +
                ", playerPort=" + playerPort +
                ", playerScore=" + playerScore +
                ", playerIp='" + playerIp + '\'' +
                '}';
    }

    public String getPlayerIp() {
        return playerIp;
    }

    public void setPlayerIp(String playerIp) {
        this.playerIp = playerIp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
