package in.sakshi.pingpong.playersapp.service;

import in.sakshi.pingpong.playersapp.model.HttpVerb;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface PlayerService<T,R> {
   R serve(String uri, final T payload, HttpVerb method) throws IOException, InterruptedException;
}
