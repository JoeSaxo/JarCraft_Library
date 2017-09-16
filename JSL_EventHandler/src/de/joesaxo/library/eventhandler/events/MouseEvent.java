package de.joesaxo.library.eventhandler.events;

import de.joesaxo.library.eventhandler.IEvent;

/**
 * Created by Jens on 06.05.2017.
 */
public class MouseEvent extends java.awt.event.MouseEvent implements IEvent {

    private EventType eventType;

    public MouseEvent(EventType eventType, java.awt.event.MouseEvent mouseEvent) {
        super(mouseEvent.getComponent(), mouseEvent.getID(), mouseEvent.getWhen(), mouseEvent.getModifiers(),
        mouseEvent.getX(), mouseEvent.getY(), mouseEvent.getClickCount(), mouseEvent.isPopupTrigger(), mouseEvent.getButton());
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType.getType();
    }

    public enum EventType {

        MOUSE_CLICK_EVENT("MOUSE_CLICK_EVENT"),

        MOUSE_PRESS_EVENT("MOUSE_PRESS_EVENT"),

        MOUSE_RELEASE_EVENT("MOUSE_RELEASE_EVENT"),

        MOUSE_ENTER_EVENT("MOUSE_ENTER_EVENT"),

        MOUSE_EXIT_EVENT("MOUSE_EXIT_EVENT"),

        MOUSE_DRAGGED_EVENT("MOUSE_DRAGGED_EVENT"),

        MOUSE_MOVED_EVENT("MOUSE_MOVED_EVENT"),

        MOUSE_CHANGED_POSITION_EVENT("MOUSE_CHANGED_POSITION_EVENT");

        String eventType;

        EventType(String eventType) {
            this.eventType = eventType;
        }

        public String getType() {
            return eventType;
        }
    }
}
