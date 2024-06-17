package in.sakshi.pingpong.refereeapp.service;

import in.sakshi.pingpong.refereeapp.models.HttpVerb;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface RefereeService<T,R>{
    R serve(String uri, final T payload, HttpVerb method) throws IOException, InterruptedException;
}
