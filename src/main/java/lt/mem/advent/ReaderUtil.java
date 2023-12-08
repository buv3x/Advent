package lt.mem.advent;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class ReaderUtil {

    public static String readInput(String fileName) {
        try {
            return FileUtils.readFileToString(new File(ReaderUtil.class.getClassLoader().getResource(fileName).getFile()),
                    Charset.defaultCharset());
        } catch (IOException e) {
            return "";
        }
    }

    public static List<String> readLineInput(String fileName) {
        try {
            return FileUtils.readLines(new File(ReaderUtil.class.getClassLoader().getResource(fileName).getFile()),
                    Charset.defaultCharset());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    public static String stringBefore(String line, String subLine) {
        return line.substring(0, line.indexOf(subLine));
    }

    public static String stringAfter(String line, String subLine) {
        return line.substring(line.indexOf(subLine) + subLine.length());
    }

}
