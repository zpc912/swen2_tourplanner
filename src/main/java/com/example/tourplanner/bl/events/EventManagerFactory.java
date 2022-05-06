package com.example.tourplanner.bl.events;

public class EventManagerFactory {

    private static IEventManager eventManager;


    public static IEventManager getEventManager() {
        if(eventManager == null) {
            eventManager = new EventManager();
        }

        return eventManager;
    }
}
