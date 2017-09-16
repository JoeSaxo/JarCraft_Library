package de.joesaxo.library.eventhandler.events;

import de.joesaxo.library.eventhandler.IEvent;

/**
 * Created by Jens on 06.05.2017.
 */
public class KeyEvent extends java.awt.event.KeyEvent implements IEvent {

    private EventType eventType;

    public KeyEvent(EventType eventType, java.awt.event.KeyEvent keyEvent) {
        super(keyEvent.getComponent(), keyEvent.getID(), keyEvent.getWhen(), keyEvent.getModifiers(),
        keyEvent.getKeyCode(), keyEvent.getKeyChar(), keyEvent.getKeyLocation());
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType.getType();
    }

    public enum EventType {

        KEY_PRESSED_EVENT("KEY_PRESSED_EVENT"),

        KEY_RELEASED_EVENT("KEY_RELEASED_EVENT"),

        KEY_TYPED_EVENT("KEY_TYPED_EVENT");

        String eventType;

        EventType(String eventType) {
            this.eventType = eventType;
        }

        public String getType() {
            return eventType;
        }
    }
}
