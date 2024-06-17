package in.sakshi.pingpong.refereeapp.server;

public interface Server {
    void init(final String uri,final int port);
    void start();
    void close();
}
