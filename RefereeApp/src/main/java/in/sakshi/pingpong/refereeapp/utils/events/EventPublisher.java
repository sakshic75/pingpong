package in.sakshi.pingpong.refereeapp.utils.events;

import java.io.InputStream;

public interface EventPublisher {
    public void notifyAll(InputStream data);
    public void addEventListener(EventListener eventListener);
    public boolean removeEventListener(EventListener eventListener);
}
