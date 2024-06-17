package in.sakshi.pingpong.refereeapp.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RefereeServer implements Server {
    private HttpServer server;

    @Override
    public void init(final String uri, final int port) {
        try {
            this.server = HttpServer.create(new InetSocketAddress(uri,port),0);
            System.out.println("Listening on Address:"+this.server.getAddress());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void addContext(String context, HttpHandler handler){
        server.createContext(context,handler);
    }

    @Override
    public void close() {
       server.stop(1000);
    }

    @Override
    public void start() {
        server.start();
    }

}
