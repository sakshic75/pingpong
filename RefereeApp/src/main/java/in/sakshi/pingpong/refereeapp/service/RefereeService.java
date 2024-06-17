package in.sakshi.pingpong.refereeapp.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import in.sakshi.pingpong.refereeapp.models.HttpVerb;

public interface RefereeService<T,R>{
    R serve(String uri, final T payload, HttpVerb method) throws IOException, InterruptedException, ExecutionException, TimeoutException;
}
