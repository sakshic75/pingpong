package in.sakshi.pingpong.refereeapp.service;

import java.io.IOException;

import in.sakshi.pingpong.refereeapp.models.HttpVerb;

public interface RefereeService<T,R>{
    R serve(String uri, final T payload, HttpVerb method) throws IOException, InterruptedException;
}
