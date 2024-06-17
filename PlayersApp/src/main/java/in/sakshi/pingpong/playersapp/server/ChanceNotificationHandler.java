package in.sakshi.pingpong.playersapp.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import in.sakshi.pingpong.playersapp.config.Constants;
import in.sakshi.pingpong.playersapp.utils.events.EventListener;
import in.sakshi.pingpong.playersapp.utils.events.EventPublisher;

import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;

public class ChanceNotificationHandler implements HttpHandler, EventPublisher<InputStream> {
    private final List<EventListener<InputStream>> listeners = new ArrayList<>();
    private String responseBody;
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        notifyAll(Constants.CHANCE_NOTIFICATION_SUBJECT,exchange.getRequestBody());
        exchange.sendResponseHeaders(200,getResponseBody().length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(getResponseBody().getBytes());
        exchange.setStreams(exchange.getRequestBody(),outputStream);
    }

    @Override
    public void addListener(EventListener<InputStream> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(EventListener<InputStream> listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyAll(String eventType, InputStream inputStream) {
        for(EventListener<InputStream> listener: listeners){
            listener.update(eventType,inputStream);
        }
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
}
