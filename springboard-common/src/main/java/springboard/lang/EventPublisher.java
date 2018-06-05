package springboard.lang;

public interface EventPublisher {

    void publish(Object event);

    void publish(String destination, Object event);

}
