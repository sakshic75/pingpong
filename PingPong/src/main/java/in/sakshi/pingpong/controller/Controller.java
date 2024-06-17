package in.sakshi.pingpong.controller;

import in.sakshi.pingpong.utils.PortScanner;
import java.util.List;

public class Controller {

    public void init(final int n){
        List<Integer> availablePorts = PortScanner.getAvailablePorts();
        int refereePort = availablePorts.get(0);
        execReferee(refereePort);
        for(int i=1;i<n;++i){
            execPlayer(availablePorts.get(i),refereePort);
        }
       

    }
    private void execPlayers(){
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec();
    }

}
