package de.joesaxo.library.eventhandler;

import de.joesaxo.library.annotation.AnnotationManager;
import de.joesaxo.library.annotation.Parameter;
import de.joesaxo.library.annotation.filter.AnnotationFilter;
import de.joesaxo.library.array.Array;

/**
 * Created by Jens on 05.05.2017.
 */
public class EventHandler {

    public static final EventHandler systemHandler = new EventHandler();

    private AnnotationManager annotationManager;

    public EventHandler() {
        annotationManager = new AnnotationManager();
    }

    public void add(Object cls) {
        annotationManager.setClasses(Array.addToArray(annotationManager.getClasses(), new Object[]{cls}));
    }

    public void add(Object[] cls) {
        annotationManager.setClasses(Array.addToArray(annotationManager.getClasses(), cls));
    }

    public Object[] invoke(IEvent event) {

        Object[] firstReturnStatements = annotationManager.invokeMethods(new AnnotationFilter(AEvent.class, new Parameter("value", event.getEventType())), event);
        Object[] secondReturnStatements = annotationManager.invokeMethods(new AnnotationFilter(AEvent.class, new Parameter("value", event.getEventType())));

        return Array.addToArray(firstReturnStatements, secondReturnStatements);
    }
}
