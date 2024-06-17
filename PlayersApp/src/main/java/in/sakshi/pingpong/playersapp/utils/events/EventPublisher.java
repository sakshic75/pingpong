package in.sakshi.pingpong.playersapp.utils.events;

public interface EventPublisher<E> {
    public void addListener(EventListener<E> listener);
    public void removeListener(EventListener<E> listener);
    public void notifyAll(String eventType,E e);
}
