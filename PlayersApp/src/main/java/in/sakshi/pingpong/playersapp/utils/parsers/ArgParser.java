package in.sakshi.pingpong.playersapp.utils.parsers;

import in.sakshi.pingpong.playersapp.config.Constants;

import java.util.HashMap;
import java.util.Map;

public class ArgParser {
   public static Map<String,String> parse(String[] args){
       Map<String,String> map = new HashMap<>();
       for(int i=0;i<args.length;i+=2){
           String name = args[i];
           String value = args[i+1];
           switch (name){
               case "-n":
                   map.put(Constants.PARSER_KEY_NAME,value);
                   break;
               case "-r":
                   map.put(Constants.PARSER_KEY_REFEREE_PORT,value);
                   break;
               case "-p":
                   map.put(Constants.PARSER_KEY_PLAYER_PORT,value);
                   break;
               case "-d":
                   map.put(Constants.PARSER_KEY_DEFENSE_ARRAY_LENGTH,value);
                   break;
               case "-c":
                   map.put(Constants.PARSER_KEY_REFEREE_URL,value);
               case "-s":
                   map.put(Constants.PARSER_KEY_PLAYER_URL,value);
                   break;
           }
       }
       if(map.size()!=6){
           System.out.println("All command-line arguments are not passed!");
           System.exit(-1);
       }
       return map;
   }

}
