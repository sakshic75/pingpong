package in.sakshi.pingpong.refereeapp;

import in.sakshi.pingpong.refereeapp.config.ConfigStore;
import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.controllers.MainController;
import in.sakshi.pingpong.refereeapp.utils.parser.ArgParser;

public class Driver {
    static{
      if(ConfigStore.storePreferences()){
          System.out.println("Configuration Stored");
      }else{
          System.out.println("Configuration storage failed!");
      }

    }
    public static void main(String[] args) {
        System.out.println("Referee App!");
        String url;
        int port;
        int playerCount;
        try{
            var argsMap = ArgParser.parse(args);
            url =  argsMap.get(Constants.PARSER_KEY_SERVER_URL);
            port = Integer.parseInt(argsMap.get(Constants.PARSER_KEY_PORT));
            playerCount = Integer.parseInt(argsMap.get(Constants.PARSER_KEY_PLAYER_COUNT));
            var mainController = new MainController(url,port,playerCount);
            mainController.init();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
