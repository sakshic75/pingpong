package in.sakshi.pingpong.refereeapp.controllers;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardController {
    private String championName;
    private String championId;
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
}
