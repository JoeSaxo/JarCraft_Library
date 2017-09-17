package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jens on 24.08.2017.
 */
public class Test {


    public static void main(String[] args) {

        JSONList uselessData = new JSONList();
        uselessData.addObjective(9745);
        uselessData.addObjective(54.79);
        uselessData.addObjective(false);
        uselessData.addObjective("useless");



        EntryList<String, Object> data = new EntryList<>();
        data.put("name", "test");
        data.put("age", 18);
        data.put("booleanValue", true);
        data.put("money", 1287.76);
        data.put("uselessData", uselessData);

        JSONValue value = JSONValue.Type.getObject(data);

        System.out.println("Type: " + value.getType());
        System.out.println(value.toFormattedString(0));

    }
}
