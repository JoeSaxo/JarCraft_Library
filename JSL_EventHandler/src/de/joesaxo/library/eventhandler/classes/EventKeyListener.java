package de.joesaxo.library.eventhandler.classes;

import de.joesaxo.library.eventhandler.EventHandler;
import de.joesaxo.library.eventhandler.IEvent;
import de.joesaxo.library.eventhandler.events.KeyEvent;

import java.awt.event.KeyListener;

import static de.joesaxo.library.eventhandler.events.KeyEvent.EventType.KEY_PRESSED_EVENT;
import static de.joesaxo.library.eventhandler.events.KeyEvent.EventType.KEY_RELEASED_EVENT;
import static de.joesaxo.library.eventhandler.events.KeyEvent.EventType.KEY_TYPED_EVENT;

/**
 * Created by Jens on 06.05.2017.
 */
public class EventKeyListener implements KeyListener {

    EventHandler eventHandler;

    public EventKeyListener(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public EventKeyListener() {
        this(EventHandler.systemHandler);
    }

    @Override
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_TYPED_EVENT, keyEvent);
        eventHandler.invoke(e);
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_PRESSED_EVENT, keyEvent);
        eventHandler.invoke(e);
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {
        IEvent e = new KeyEvent(KEY_RELEASED_EVENT, keyEvent);
        eventHandler.invoke(e);
    }
}
