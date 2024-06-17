package in.sakshi.pingpong.refereeapp.controllers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardController  implements Serializable {
    private String championName;
    private String championId;
    private int championScore;
    private List<GameController.Scorecard> scorecards;
    public ScoreboardController(){
        scorecards = new ArrayList<>();
    }
    public void addScorecard(GameController.Scorecard scorecard){
        scorecards.add(scorecard);
    }
    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public int getChampionScore() {
        return championScore;
    }

    public void setChampionScore(int championScore) {
        this.championScore = championScore;
    }
    public void saveScoreboard(final String filename)  {
        try(FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
            outputStream.flush();
            outputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
