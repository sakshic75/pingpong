package in.sakshi.pingpong.playersapp.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PlayersServer implements Server {
    private HttpServer server;
    @Override
    public void init(String uri, int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(uri, port), 0);
        }catch (IOException e){
            System.err.println(e.getCause().toString());
        }
    }

    @Override
    public void connect() {
        server.start();
    }

    public void addContext(String context, HttpHandler handler){
        server.createContext(context,handler);
    }

    @Override
    public void close()  {
        server.stop(1000);
    }

}
