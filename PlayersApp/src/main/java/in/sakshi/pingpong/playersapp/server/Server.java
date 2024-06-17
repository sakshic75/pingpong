package in.sakshi.pingpong.playersapp.server;

public interface Server {
   void init(String uri, int port) ;
   void connect();
   void close() ;
}
