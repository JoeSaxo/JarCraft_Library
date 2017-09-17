package org.jarcraft.library.iotools;

/**
 * Created by Jens on 17.09.2017.
 */
public class ClassDependencyHandler {

    public static boolean dependsOn(Class<?> class1, Class<?> class2) {
        //System.out.println(class1.getName() + " | " + class2.getName());
        if (class1.equals(class2)) return true;
        if (class2 == null || class2.equals(Object.class)) return false;

        if (dependsOn(class1, class2.getSuperclass())) return true;

        for (Class<?> iFace : class2.getInterfaces()) {
            if (dependsOn(class1, iFace)) return true;
        }
        return false;
    }

}
