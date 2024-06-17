package in.sakshi.pingpong.playersapp;

import in.sakshi.pingpong.playersapp.config.ConfigStore;
import in.sakshi.pingpong.playersapp.config.Constants;
import in.sakshi.pingpong.playersapp.controllers.MainController;
import in.sakshi.pingpong.playersapp.utils.parsers.ArgParser;

import java.util.Map;

public class Driver {
    static {
        if(ConfigStore.storePreferences()){
            System.out.println("Configuration Stored Successfully!");
        }else{
            System.out.println("Configuration storage failed!");
        }
    }
    //
    public static void main(String[] args) {
        // -n name
        // -r referee port
        // -c  referee url (should be with http:// or https://)
        // -p player port
        // -s player server url(without http:// or https://)
        // -d defense array length
        String refereeUrl;
        String playerUrl;
        String playerName;
        int refereePort;
        int playerPort;
        int defenseArrayLength;
        try{
            Map<String,String> argsMap = ArgParser.parse(args);
            refereeUrl = argsMap.get(Constants.PARSER_KEY_REFEREE_URL);
            refereePort = Integer.parseInt(argsMap.get(Constants.PARSER_KEY_REFEREE_PORT));
            playerUrl = argsMap.get(Constants.PARSER_KEY_PLAYER_URL);
            playerPort = Integer.parseInt(argsMap.get(Constants.PARSER_KEY_PLAYER_PORT));
            defenseArrayLength = Integer.parseInt(argsMap.get(Constants.PARSER_KEY_DEFENSE_ARRAY_LENGTH));
            playerName = argsMap.get(Constants.PARSER_KEY_NAME);
            MainController controller = new MainController(playerName,refereeUrl,refereePort,defenseArrayLength);
            controller.initServer(playerUrl,playerPort);
            controller.printWelcomeMessage();
            controller.initServices();
        }catch(Exception e){
            System.err.println(e.getMessage());
            System.exit(-1);
        }

    }
}
