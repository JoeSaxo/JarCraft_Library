package org.jarcraft.library.iotools;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Jens on 16.08.2017.
 */
public class IOTool {

    public static boolean copy(InputStream from, File to) {
        try {
            writeBytes(readBytes(from), to);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copy(File from, File to) {
        try {
            writeBytes(readBytes(from), to);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static byte[] readBytes(InputStream inputStream) {
        try {
            byte[] testBytes = new byte[inputStream.available()];
            inputStream.read(testBytes);
            inputStream.close();
            return testBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public static byte[] readBytes(File file) throws IOException {
        return Files.readAllBytes(Paths.get(file.getPath()));
    }

    public static void writeBytes(byte[] bytes, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes);
        fos.close();
    }

    public static boolean download(URL url, File path) {
        try {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            FileOutputStream fos = new FileOutputStream(path);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean download(String url, File path) {
        try {
            return download(new URL(url), path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean download(String url, String path) {
        return download(url, new File(path));
    }

    public static boolean online() {
        return false;
    }


    public static String read(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder b = new StringBuilder();
            while (reader.ready()) {
                b.append((char) reader.read());
            }
            reader.close();
            return b.toString();
        } catch (IOException e) {
            return null;
        }
    }


    public static boolean write(File file, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            writer.write(text);
            writer.newLine();
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getSHA1(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[8192];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    digest.update(buffer,0, n);
                }
            }
            return new HexBinaryAdapter().marshal(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean matchSHA1(File file, String sha1) {
        return sha1.equals(getSHA1(file));
    }

    public static boolean fileMatches(File file, String sha1, int size) {
        return file.isFile() && matchSHA1(file, sha1) && file.length() == size;
    }

    public static String  getHTMLSiteContent(URL url) {
        try {
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            //con.setReadTimeout(15000);
            //con.setConnectTimeout(15000);
            con.setDoInput(true);
            con.setDoOutput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                output.append(line);
                output.append("\r\n");
            }
            in.close();
            con.disconnect();
            return output.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static String getHTMLSiteContent(String url) {
        try {
            return getHTMLSiteContent(new URL(url));
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
