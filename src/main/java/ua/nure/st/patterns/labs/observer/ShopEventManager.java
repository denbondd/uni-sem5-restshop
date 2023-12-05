package ua.nure.st.patterns.labs.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopEventManager {

    private final Map<Long, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(Long shopId, EventListener listener) {
        List<EventListener> users = listeners.getOrDefault(shopId, new ArrayList<>());
        users.add(listener);

        listeners.put(shopId, users);
        System.out.println("Subscribed to " + shopId);
    }

    public void unsubscribe(Long eventType, EventListener listener) {
        List<EventListener> users = listeners.getOrDefault(eventType, new ArrayList<>());
        users.remove(listener);

        listeners.put(eventType, users);
    }

    public void notify(Long eventType, String message) {
        List<EventListener> users = listeners.getOrDefault(eventType, new ArrayList<>());
        users.forEach(listener -> listener.update(message));
    }
}
