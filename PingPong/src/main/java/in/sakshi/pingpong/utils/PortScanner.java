package in.sakshi.pingpong.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PortScanner {
    private static final int NUMBER_OF_PORTS = 65535;
    private static final String IP_ADDRESS="127.0.0.1";
    private static final int CONNECTION_TIMEOUT=1000;
    private static final int THREAD_TIMEOUT = 1000;
    private static final TimeUnit THREAD_TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    public static List<Integer> getAvailablePorts(){
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        List<Integer> availablePorts = new ArrayList<Integer>();
        try (ExecutorService executor = Executors.newFixedThreadPool(100)) {
            AtomicInteger port = new AtomicInteger();
            while(port.get()<=NUMBER_OF_PORTS){
                final int currentPort = port.getAndIncrement();
                executor.submit(()->{
                   try(Socket socket = new Socket()){
                        socket.connect(new InetSocketAddress(IP_ADDRESS,currentPort),CONNECTION_TIMEOUT);
                        queue.add(currentPort);
                   }catch(IOException e){
                       System.err.println(e.getCause().toString());
                   }
                });
            }
            executor.shutdown();
            if(!executor.isTerminated()){
                try{
                    boolean terminated = executor.awaitTermination(THREAD_TIMEOUT, THREAD_TIMEOUT_UNIT);
                }catch(InterruptedException e){
                    System.err.println(e.getCause().toString());
                }
            }
           while(!queue.isEmpty()){
               availablePorts.add(queue.poll());
           }
        }catch(Exception e){
            System.err.println(e.getCause().toString());
        }
        return availablePorts;
    }
}
