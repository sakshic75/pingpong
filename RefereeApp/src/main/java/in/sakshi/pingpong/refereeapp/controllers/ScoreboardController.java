package in.sakshi.pingpong.refereeapp.controllers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try{
            File file = new File("C:\\GameReports\\");
            if(!file.exists()){
                boolean isCreated = file.mkdir();
                if(isCreated){
                    System.out.println("Game Directory created at C:\\GameReports\\");
                }else{
                    System.out.println("Unable to create Game Directory  at C:\\GameReports\\");
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("C:\\GameReports\\"+filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
           // System.out.println("Report is saved on the following path: "+path.toString());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (NullPointerException e){
            System.out.println("NullPointerException: "+e.getMessage());
        }
    }

}
