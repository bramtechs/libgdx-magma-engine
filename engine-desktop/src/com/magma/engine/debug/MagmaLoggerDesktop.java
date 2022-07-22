package com.magma.engine.debug;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.magma.engine.saving.OsUtil;
import com.magma.engine.utils.Time;

public class MagmaLoggerDesktop extends MagmaLogger {

    public MagmaLoggerDesktop(String name){
        super(name);
        printHeader();
    }

    /**
     * Saves the log file to the users' appdata folder.
     * 
     * @return Absolute path of the created log file.
     */
    public static String flush() {
        return flush(false);
    }

    /**
     * Saves the log file to the users' appdata folder.
     * 
     * @param crashed Did the game malfunction?
     * @return Absolute path of the created log file.
     */
    public static String flush(boolean crashed) {
        String file = OsUtil.getUserDataDirectory("SpaceGame");

        // append end text
        MagmaLoggerDesktop i = get();
        i.html += "<hr/>";
        log("", "State: " + (crashed ? "FAILED" : "SUCCEEDED"));
        i.html += "</p>";

        // print to a file
        FileHandle handle = new FileHandle(file + "/logs/");
        handle.mkdirs();

        String name;
        if (crashed) {
            name = "crash_" + Time.getStartTime() + ".html";
        } else {
            name = "latest.html";
        }

        handle = handle.child(name);
        String path = handle.file().getAbsolutePath();
        log("MagmaLogger", "Log file saved to: " + path);
        handle.writeString(i.html, false);

        Gdx.app.log("MagmaLogger", "Log saved to " + path);
        return path;
    }

    public void printHeader() {
        html = "";
        if (name == null) {
            name = "MagmaEngineGame";
            Gdx.app.log("MagmaLogger", "No game name set: defaulting to " + name);
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        html += "<h1>" + name + "</h1>";
        html += "<h2>Log from: " + dtf.format(now) + "</h2>";
        html += "<hr/>";
        html += "<p>";
    }

    @Override
    protected void _logException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        String message = errors.toString();
        log("EXCEPTION", message, Level.ERROR);
    }

    private static MagmaLoggerDesktop get(){
        if (instance == null)
        {
            throw new NullPointerException("No logger initialized!");
        }
        return (MagmaLoggerDesktop) instance;
    }
}
