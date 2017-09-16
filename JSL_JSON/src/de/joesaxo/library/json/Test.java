package de.joesaxo.library.json;

import org.jarcraft.library.time.StopWatch;

import java.util.HashMap;

/**
 * Created by Jens on 24.08.2017.
 */
public class Test {


    public static void main(String[] args) {

        //System.out.println(JSONValue.Type.getType(new Has));
        //System.exit(0);
        HashMap<JSONValue, JSONValue> map1 = new HashMap<>();
        HashMap<Object, Object> map2 = new HashMap<>();
        HashMap<Object, Object> map3 = new HashMap<>();

        map1.put(new JSONString("K1"), new JSONString("V1"));
        map2.put("K1", "V1");
        map3.put("K1", new StopWatch());

        System.out.println(JSONHandler.isJSON(map1));
        System.out.println(JSONHandler.isJSON(map2));
        System.out.println(JSONHandler.isJSON(map3));



    }
}
