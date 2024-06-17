package in.sakshi.pingpong.refereeapp.service;

import in.sakshi.pingpong.refereeapp.models.HttpVerb;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RefereeServiceImpl implements RefereeService<String, String>{
    private HttpClient client;
    public RefereeServiceImpl(){
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMillis(10))
                .build();
    }

    @Override
    public String serve(String uri, String payload, HttpVerb httpVerb) throws IOException, InterruptedException {
        HttpRequest request;
        switch (httpVerb){
            case HttpVerb.GET:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .GET()
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            case HttpVerb.POST:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .version(HttpClient.Version.HTTP_1_1)
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            case HttpVerb.PUT:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .PUT(HttpRequest.BodyPublishers.ofString(payload))
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();

            case HttpVerb.DELETE:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .DELETE()
                        .build();
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            default:
                return null;
        }

    }
}
