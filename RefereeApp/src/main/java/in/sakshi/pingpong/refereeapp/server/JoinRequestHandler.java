package in.sakshi.pingpong.refereeapp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import in.sakshi.pingpong.refereeapp.config.Constants;
import in.sakshi.pingpong.refereeapp.utils.events.EventListener;
import in.sakshi.pingpong.refereeapp.utils.events.EventPublisher;

public class JoinRequestHandler implements HttpHandler, EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();
    private String responseBody;

    public JoinRequestHandler(){
    }

    @Override
    public void notifyAll(InputStream data){
        for(EventListener listener: listeners){
            listener.update(Constants.JOIN_GAME_REQUEST_ID,data);
        }
    }

    @Override
    public void addEventListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    @Override
    public boolean removeEventListener(EventListener eventListener) {
        return listeners.remove(eventListener);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        notifyAll(exchange.getRequestBody());
        exchange.sendResponseHeaders(200,getResponseBody().length());
        OutputStream o = exchange.getResponseBody();
        o.write(this.getResponseBody().getBytes());
        exchange.setStreams(exchange.getRequestBody(),o);
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
