import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ShellUtil {
    @Test
    public void start() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("adb","shell","su");
        Process p = builder.start();
        int runningStatus = 0;
        String s = null;

;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));

        writer.write("cat /system/bonree.prop");

        writer.flush();
        try {
            runningStatus = p.waitFor();
        } catch (InterruptedException e) {
        }
    }
}
