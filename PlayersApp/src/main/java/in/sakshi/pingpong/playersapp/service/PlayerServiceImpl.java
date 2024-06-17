package in.sakshi.pingpong.playersapp.service;

import in.sakshi.pingpong.playersapp.model.HttpVerb;
import in.sakshi.pingpong.playersapp.model.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class PlayerServiceImpl implements  PlayerService<String,String>{
    private final HttpClient client;
    public PlayerServiceImpl(){
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(20))
                .build();
    }
    @Override
    public String serve(String uri, final String payload, HttpVerb method) throws IOException, InterruptedException {
        HttpRequest request;
         switch (method) {
             case HttpVerb.GET :
                request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .uri(URI.create(uri))
                        .GET()
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();

             case HttpVerb.POST:
                request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .uri(URI.create(uri))
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .build();
                return client.send(request,HttpResponse.BodyHandlers.ofString()).body();

            case HttpVerb.PUT:
                request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .uri(URI.create(uri))
                        .PUT(HttpRequest.BodyPublishers.ofString(payload))
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();

             case HttpVerb.DELETE:
                request = HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .uri(URI.create(uri))
                        .DELETE()
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
             default:
                 return null;
        }

    }

}
