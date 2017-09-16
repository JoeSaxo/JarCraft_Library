package de.joesaxo.library.json;

import de.joesaxo.library.array.EntryList;
import org.jarcraft.library.iotools.IOTool;

import java.io.File;
import java.util.Map.Entry;

import static de.joesaxo.library.json.JSONValue.getValueEntry;

/**
 * Created by Jens on 22.08.2017.
 */
public class JSONHandler {

    public static String cleanUpJSONString(String stringJSON) {
        StringBuilder b = new StringBuilder();
        boolean afz = false;
        char[] cArray = stringJSON.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            char c = cArray[i];
            switch(c) {
                case '\b':
                case '\t':
                case '\n':
                case '\f':
                case '\r':
                    break;
                case '"':
                    if (i > 0 && cArray[i-1] == '\\') {
                        b.append('"');
                    } else {
                        afz = !afz;
                        b.append('"');
                    }
                    break;
                case ' ':
                    if (afz) {
                        b.append(' ');
                    }
                    break;
                default:
                    b.append(c);
                    break;
            }
        }
        return b.toString();
    }

    public static boolean isJSON(String stringJSON) {
        try {
            return stringJSON != null && getValueEntry(cleanUpJSONString(stringJSON)).getValue() == cleanUpJSONString(stringJSON).length();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isJSON(File file) {
        return isJSON(IOTool.read(file));
    }

    public static boolean isJSON(Object objectJSON) {
        return JSONValue.Type.getType(objectJSON) != null;
    }

    public static JSONValue createJSONValue(String stringJSONValue) {
        String cleanedUpJSONValueString = cleanUpJSONString(stringJSONValue);
        Entry<JSONValue, Integer> entry = getValueEntry(cleanedUpJSONValueString);
        if (entry.getValue() != cleanedUpJSONValueString.length()) return null;
        return entry.getKey();
    }

    public static JSONValue createJSONValue(File file, boolean subMap) {
        JSONMap hashMap = createJSONValue(new File[]{file});
        if (subMap) return hashMap;
        return hashMap.value.get(new JSONString(file.getAbsolutePath()));
    }

    public static JSONValue createJSONValue(File file) {
        return createJSONValue(file, false);
    }

    public static JSONMap createJSONValue(File[] files) {
        EntryList<JSONValue, JSONValue> hashMap = new EntryList<>();
        for (File file : files) {
            JSONValue value = createJSONValue(IOTool.read(file));
            hashMap.put(new JSONString(file.getAbsolutePath()), value);
        }
        return new JSONMap(hashMap);
    }

    public static String createString(JSONValue value) {
        return value.toString();
    }

    public static String createFormattedString(JSONValue value) {
        return value.toFormattedString(0);
    }

    public static boolean write(File file, JSONMap jsonArchiveMap) {
        return IOTool.write(file, createFormattedString(jsonArchiveMap));
    }
}
