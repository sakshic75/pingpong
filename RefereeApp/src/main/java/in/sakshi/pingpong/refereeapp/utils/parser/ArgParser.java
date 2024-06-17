package in.sakshi.pingpong.refereeapp.utils.parser;

import in.sakshi.pingpong.refereeapp.config.Constants;

import java.util.HashMap;
import java.util.Map;

public class ArgParser {
    public static Map<String,String> parse(String[] args){
        Map<String,String> map = new HashMap<>();
        for(int i=0; i<args.length;i+=2){
            String key = args[i];
            String value = args[i+1];
            switch(key){
                case "-n":
                    map.put(Constants.PARSER_KEY_PLAYER_COUNT,value);
                break;
                case "-u":
                    map.put(Constants.PARSER_KEY_SERVER_URL,value);
                    break;
                case "-p":
                    map.put(Constants.PARSER_KEY_PORT,value);
                    break;
            }

        }
        if(map.size()!=3){
            System.out.println("All command-line arguments were not passed!");
            System.exit(-1);
        }
        return map;
    }
}
