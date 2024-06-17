package in.sakshi.pingpong.playersapp.utils.events;

public interface EventListener<E> {
    public void update(String eventType, E e);
}
