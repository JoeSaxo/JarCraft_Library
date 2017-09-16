package org.jarcraft.library.iotools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Jens on 09.08.2017.
 */
public class ByteHandler {

    public static byte[] read(File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getPath()));
    }

    public static void write(byte[] bytes, File file) throws IOException {
        new FileOutputStream(file).write(bytes);
    }

    public static String stringFrom(byte[] bytes) {
        StringBuilder b = new StringBuilder();
        for (byte character : bytes) {
            b.append((char)character);
        }
        return b.toString();
    }

    public static char charOf(byte b) {
        return (char) b;
    }

    public static byte byteOf(char c) {
        return (byte) c;
    }

    public static byte[] bytesFrom(String string) {
        return string.getBytes();
    }

    public static int compare(byte[] a, byte[] b) {
        int wrongStates = 0;
        if (a.length != b.length) return -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) wrongStates++;
        }
        return wrongStates;
    }

}
