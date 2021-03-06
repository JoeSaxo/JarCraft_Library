package de.joesaxo.library.plugin.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Jens on 15.04.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface APlugin {

    boolean load() default true;

    String value();


}
