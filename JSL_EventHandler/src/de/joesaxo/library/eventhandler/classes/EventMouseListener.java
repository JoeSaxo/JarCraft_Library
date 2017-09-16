package de.joesaxo.library.eventhandler.classes;

import de.joesaxo.library.eventhandler.EventHandler;
import de.joesaxo.library.eventhandler.events.MouseEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static de.joesaxo.library.eventhandler.events.MouseEvent.EventType.*;

/**
 * Created by Jens on 07.05.2017.
 */
public class EventMouseListener implements MouseListener, MouseMotionListener{

    EventHandler eventHandler;

    public EventMouseListener(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public EventMouseListener() {
        this(EventHandler.systemHandler);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_CLICK_EVENT, e));
    }

    boolean pressed;

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_PRESS_EVENT, e));
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_RELEASE_EVENT, e));
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_ENTER_EVENT, e));
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_EXIT_EVENT, e));
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        mouseChangedPosition(e);
        eventHandler.invoke(new MouseEvent(MOUSE_DRAGGED_EVENT, e));
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        mouseChangedPosition(e);
        eventHandler.invoke(new MouseEvent(MOUSE_MOVED_EVENT, e));
    }

    private void mouseChangedPosition(java.awt.event.MouseEvent e) {
        eventHandler.invoke(new MouseEvent(MOUSE_CHANGED_POSITION_EVENT, e));
    }

}
