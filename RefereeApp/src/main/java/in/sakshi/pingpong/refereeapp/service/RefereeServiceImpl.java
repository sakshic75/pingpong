package in.sakshi.pingpong.refereeapp.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import in.sakshi.pingpong.refereeapp.models.HttpVerb;

public class RefereeServiceImpl implements RefereeService<String, String>{
    private HttpClient client;
    public RefereeServiceImpl(){
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofMinutes(5))
                .build();
    }

    @Override
    public String serve(String uri, String payload, HttpVerb httpVerb) throws  InterruptedException, ExecutionException, TimeoutException {
        HttpRequest request;
        switch (httpVerb){
            case HttpVerb.GET:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .version(HttpClient.Version.HTTP_2)
                        .timeout(Duration.ofSeconds(30))
                        .GET()
                        .build();
                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get(5, TimeUnit.MINUTES);

            case HttpVerb.POST:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .version(HttpClient.Version.HTTP_2)
                        .POST(HttpRequest.BodyPublishers.ofString(payload))
                        .timeout(Duration.ofSeconds(30))
                        .build();
                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get(5, TimeUnit.MINUTES);
            case HttpVerb.PUT:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .version(HttpClient.Version.HTTP_2)
                        .PUT(HttpRequest.BodyPublishers.ofString(payload))
                        .timeout(Duration.ofSeconds(30))
                        .build();
                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get(5, TimeUnit.MINUTES);

            case HttpVerb.DELETE:
                request = HttpRequest.newBuilder()
                        .uri(URI.create(uri))
                        .version(HttpClient.Version.HTTP_2)
                        .timeout(Duration.ofSeconds(30))
                        .DELETE()
                        .build();
                return client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).get(5, TimeUnit.MINUTES);
            default:
                return null;
        }

    }
}
