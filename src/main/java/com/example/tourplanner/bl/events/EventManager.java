package com.example.tourplanner.bl.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager implements IEventManager {

    private final Map<String, List<IEventListener>> listeners = new HashMap<>();


    @Override
    public void subscribe(String event, IEventListener eventListener) {
        List<IEventListener> events = listeners.getOrDefault(event, new ArrayList<>());
        events.add(eventListener);
        listeners.put(event, events);
    }


    @Override
    public void unsubscribe(String event, IEventListener eventListener) {
        List<IEventListener> events = listeners.getOrDefault(event, new ArrayList<>());
        events.remove(eventListener);
        listeners.put(event, events);
    }


    @Override
    public void notify(String event, Object data) {
        for(IEventListener listener : listeners.getOrDefault(event, new ArrayList<>())) {
            listener.update(event, data);
        }
    }
}
