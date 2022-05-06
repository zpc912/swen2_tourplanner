package com.example.tourplanner.bl.events;

public interface IEventManager {

    void subscribe(String event, IEventListener listener);

    void unsubscribe(String event, IEventListener listener);

    void notify(String event, Object data);
}
