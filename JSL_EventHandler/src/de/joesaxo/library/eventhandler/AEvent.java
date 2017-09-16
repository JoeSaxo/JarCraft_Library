package de.joesaxo.library.eventhandler;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 05.05.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AEvent {

    String value() default "";

}
