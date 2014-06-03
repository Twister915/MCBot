package org.projectbot.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * <p>
 * Latest Change:
 * <p>
 *
 * @author Jake
 * @since 6/3/2014
 */
public class FileUtil {
    public static String getFileLines(String file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        }
    }
}
