package in.sakshi.pingpong.refereeapp.utils.events;

import java.io.InputStream;

public interface EventListener{
    public void update(String eventType, InputStream data);
}
